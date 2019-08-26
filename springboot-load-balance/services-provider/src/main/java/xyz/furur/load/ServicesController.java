package xyz.furur.load;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fururur
 * @create 2019-08-13-14:00
 */
@RestController
public class ServicesController {

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }
}
