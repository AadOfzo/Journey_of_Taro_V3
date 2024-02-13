package Journey_of_Taro_V3.Journey_of_Taro_V3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("Journey_of_Taro_V3.Journey_of_Taro_V3.services")
@ComponentScan("Journey_of_Taro_V3.Journey_of_Taro_V3.repositories")
public class JourneyOfTaroV3Application {

	public static void main(String[] args) {
		SpringApplication.run(JourneyOfTaroV3Application.class, args);
	}

}
