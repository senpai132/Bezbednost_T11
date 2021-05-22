package adminapi.adminaplication.controller;

import adminapi.adminaplication.dto.mapper.CertificateSignRequestMapper;
import adminapi.adminaplication.dto.mapper.UserMapper;
import adminapi.adminaplication.dto.request.UserDTO;
import adminapi.adminaplication.dto.response.CertificateSignRequestDTO;
import adminapi.adminaplication.model.CertificateSignRequest;
import adminapi.adminaplication.model.User;
import adminapi.adminaplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private UserMapper mapper = new UserMapper();


    /*@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.allUsers();

        return new ResponseEntity<>(mapper.toDtoList(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<UserDTO>> createUser(@RequestBody UserDTO dto) {
        try {
            userService.createUser(mapper.toEntity(dto));
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<List<UserDTO>> changeUserType(@RequestBody UserDTO dto) {
        try {
            userService.changeUserType(mapper.makeType(dto.getType()), dto.getEmail());
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/{email}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }*/

}
