package by.mitr.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class ResourceserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceserverApplication.class, args);
    }

}

@Service
class GreetingService{
    @PreAuthorize("hasAuthority('SCOPE_user,read')")
    public Map<String, String> greet(){
        var jwt =  (Jwt) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return Map.of("message", "hello " + jwt.getSubject());
    }
}

@RestController
class GreetingController {
private final GreetingService greetingService;

    GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/")
    Map<String,String> hello(){
        return greetingService.greet();
    }
}
