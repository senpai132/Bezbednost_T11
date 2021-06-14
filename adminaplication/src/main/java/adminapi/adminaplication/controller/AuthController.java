package adminapi.adminaplication.controller;

import adminapi.adminaplication.config.SessionConfig;
import adminapi.adminaplication.dto.request.UserLoginDTO;
import adminapi.adminaplication.dto.response.UserTokenStateDTO;
import adminapi.adminaplication.model.User;
import adminapi.adminaplication.model.fact.BlockingFact;
import adminapi.adminaplication.security.TokenUtils;
import adminapi.adminaplication.service.UserDetailsServiceImpl;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private SessionConfig sessionConfig;


    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody UserLoginDTO authenticationRequest,
                                                                       HttpServletResponse response, HttpServletRequest request) {
        KieSession session = sessionConfig.userLoginSession();
        String ip = getClientIpAddr(request);
        try {
            BlockingFact blockingFact = new BlockingFact();
            session.insert(blockingFact);
            session.insert(ip);
            session.getAgenda().getAgendaGroup("Halt").setFocus();
            session.getAgenda().getAgendaGroup("Block").setFocus();
            session.fireUntilHalt();
            if(blockingFact.isBlocked() ){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
        } catch (Exception ex) {

            session.insert(ip);
            session.getAgenda().getAgendaGroup("Halt").setFocus();

            session.getAgenda().getAgendaGroup("FailLog").setFocus();
            session.fireUntilHalt();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /*@PostMapping("/register")
    public ResponseEntity<?> addUser(@Valid @RequestBody GuestDTO userRequest,
                                     HttpServletRequest request) {
        Guest existUser;
        try {
            existUser = guestService.insert(guestMapper.toEntity(userRequest));
            if(existUser == null)
                throw new Exception("Crash");
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(existUser,
                    request.getLocale(), appUrl));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(guestMapper.toDto(existUser), HttpStatus.CREATED);
    }*/

    /*@GetMapping("/registrationConfirm")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {

        VerificationToken verificationToken = userDetailsService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setActive(true);
        userDetailsService.saveRegisteredUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @PostMapping("/refresh")
    public ResponseEntity<UserTokenStateDTO> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        Integer id = this.tokenUtils.getUserIdFromToken(token);
        if (id != null) {
            User user = (User) this.userDetailsService.loadUserById(id);

            if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
                String refreshedToken = tokenUtils.refreshToken(token, user.getUsername());
                int expiresIn = tokenUtils.getExpiredIn();

                return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken, expiresIn));
            }
        }

        UserTokenStateDTO userTokenState = new UserTokenStateDTO();
        return ResponseEntity.badRequest().body(userTokenState);
    }

    public AuthController() {

    }
}
