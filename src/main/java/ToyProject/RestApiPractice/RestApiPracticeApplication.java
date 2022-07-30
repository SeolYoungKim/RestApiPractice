package ToyProject.RestApiPractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RestApiPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiPracticeApplication.class, args);
	}

}
