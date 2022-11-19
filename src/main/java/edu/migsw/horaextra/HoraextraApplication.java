package edu.migsw.horaextra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HoraextraApplication{

	public static void main(String[] args) {
		SpringApplication.run(HoraextraApplication.class, args);
	}

}
