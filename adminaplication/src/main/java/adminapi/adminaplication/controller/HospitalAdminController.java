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
@RequestMapping("/hospitaladmin")
public class HospitalAdminController {
    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;

    @PostMapping
    public void addAdmin(@RequestBody AdminDTO dto) {
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
    public List<AdminDTO> getAllAdmins() {
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
    public void deleteDocctorById(@PathVariable int id) {
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
}
