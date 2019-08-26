package xyz.furur.load;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Fururur
 * @create 2019-08-13-14:12
 */
@RestController
public class ConsumerController {
    private final String servicesUrl = "http://services-provider/";
    private final RestTemplate restTemplate;

    public ConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/")
    public String home() {
        return restTemplate.getForObject(servicesUrl,String.class);
    }

}
