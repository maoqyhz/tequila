package xyz.furur.load;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fururur
 * @create 2019-08-13-14:12
 */
@RestController
public class ConsumerController {
    // 使用RestTemplate请取消注释
    // private final String servicesUrl = "http://services-provider/";
    // private final RestTemplate restTemplate;

    // public ConsumerController(RestTemplate restTemplate) {
    //     this.restTemplate = restTemplate;
    // }

    private final IRestRemote remote;

    public ConsumerController(IRestRemote remote) {
        this.remote = remote;
    }

    @RequestMapping("/")
    public String home() {
        return remote.home();
        // return restTemplate.getForObject(servicesUrl,String.class);
    }

}
