package poc.projectmgt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
@EnableJpaAuditing
@EnableDiscoveryClient
public class UserWebApplication {
	
	@Value("${spring.application.name}")
	private String name;
	
	public static void main(String[] args) {
	SpringApplication.run(UserWebApplication.class,args);
	}
}
