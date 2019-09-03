package xyz.furur.load;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Fururur
 * @create 2019-09-02-15:12
 */
@FeignClient(value = "services-provider", fallback = RestRemoteHystrix.class)
public interface IRestRemote {
    @GetMapping("/")
    String home();
}
