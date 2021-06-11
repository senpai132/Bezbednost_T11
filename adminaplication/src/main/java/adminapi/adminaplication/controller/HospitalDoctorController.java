package adminapi.adminaplication.controller;

import adminapi.adminaplication.config.RestTemplateConfiguration;
import adminapi.adminaplication.dto.request.DoctorDTO;
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
@RequestMapping("/hospitaldoctor")
public class HospitalDoctorController {

    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @PostMapping
    public void addDoctor(@RequestHeader("Authorization") String token, @RequestBody DoctorDTO dto) {
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();

        HttpEntity<DoctorDTO> request = new HttpEntity<>(dto);
        try {
            HttpStatus httpStatus = restTemplate.exchange(
                    "https://localhost:8081/api/doctor",
                    HttpMethod.POST,
                    request,
                    String.class).getStatusCode();


        } catch (Exception exception) { //HttpClientErrorException
            //throw new InvalidAPIResponse("Invalid API response.");
            exception.printStackTrace();
        }
    }

    @GetMapping
    public List<DoctorDTO> getAllDoctors(@RequestHeader("Authorization") String token) {
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        List<DoctorDTO> result = new ArrayList<DoctorDTO>();
        //HttpEntity<DoctorDTO> request = new HttpEntity<>(dto);
        try {
            result = restTemplate.getForObject("https://localhost:8081/api/doctor", ArrayList.class);

            return result;

        } catch (Exception exception) { //HttpClientErrorException
            exception.printStackTrace();
            //return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @DeleteMapping("/{id}")
    public void deleteDoctorById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        //HttpEntity<DoctorDTO> request = new HttpEntity<>(dto);
        try {
            HttpStatus httpStatus = restTemplate.exchange(
                    "https://localhost:8081/api/doctor/" + id,
                    HttpMethod.DELETE,
                    null,
                    String.class).getStatusCode();
        } catch (Exception exception) { //HttpClientErrorException
            exception.printStackTrace();
            //return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    @PutMapping("/changetoadmin/{id}")
    public void changeToAdmin(@RequestHeader("Authorization") String token, @PathVariable int id) {
        restTemplateConfiguration.setToken(token);
        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();
        //HttpEntity<AdminDTO> request = new HttpEntity<>(dto);
        try {
            HttpStatus httpStatus = restTemplate.exchange(
                    "https://localhost:8081/api/rolechange/doctortoadmin/" + id,
                    HttpMethod.PUT,
                    null,
                    String.class).getStatusCode();
        } catch (Exception exception) { //HttpClientErrorException
            exception.printStackTrace();
            //return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

}
