package adminapi.adminaplication.controller;

import adminapi.adminaplication.config.RestTemplateConfiguration;
import adminapi.adminaplication.dto.request.AdminDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/c")
public class HospitalAdminController {
    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @PostMapping
    public void addAdmin(@RequestHeader("Authorization") String token, @RequestBody AdminDTO dto) {
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();

        HttpEntity<AdminDTO> request = new HttpEntity<>(dto);
        try {
            HttpStatus httpStatus = restTemplate.exchange(
                    "https://localhost:8081/api/admin",
                    HttpMethod.POST,
                    request,
                    String.class).getStatusCode();


        } catch (Exception exception) { //HttpClientErrorException
            //throw new InvalidAPIResponse("Invalid API response.");
            exception.printStackTrace();
        }
    }

    @GetMapping
    public List<AdminDTO> getAllAdmins(@RequestHeader("Authorization") String token) {
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        List<AdminDTO> result = new ArrayList<AdminDTO>();
        //HttpEntity<AdminDTO> request = new HttpEntity<>(dto);
        try {
            result = restTemplate.getForObject("https://localhost:8081/api/admin", ArrayList.class);

            return result;

        } catch (Exception exception) { //HttpClientErrorException
            exception.printStackTrace();
            //return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @DeleteMapping("/{id}")
    public void deleteAdminById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        //HttpEntity<AdminDTO> request = new HttpEntity<>(dto);
        try {
            HttpStatus httpStatus = restTemplate.exchange(
                    "https://localhost:8081/api/admin/" + id,
                    HttpMethod.DELETE,
                    null,
                    String.class).getStatusCode();
        } catch (Exception exception) { //HttpClientErrorException
            exception.printStackTrace();
            //return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    @PutMapping("/changetodoctor/{id}")
    public void changeToDoctor(@RequestHeader("Authorization") String token, @PathVariable int id) {
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        //HttpEntity<AdminDTO> request = new HttpEntity<>(dto);
        try {
            HttpStatus httpStatus = restTemplate.exchange(
                    "https://localhost:8081/api/rolechange/admintodoctor/" + id,
                    HttpMethod.PUT,
                    null,
                    String.class).getStatusCode();
        } catch (Exception exception) { //HttpClientErrorException
            exception.printStackTrace();
            //return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }
}
