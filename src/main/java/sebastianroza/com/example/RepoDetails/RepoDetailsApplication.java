package sebastianroza.com.example.RepoDetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"sebastianroza.com.example.RepoDetails"})
public class RepoDetailsApplication {
    public static void main(String[] args) {

        SpringApplication.run(RepoDetailsApplication.class, args);
    }
}
