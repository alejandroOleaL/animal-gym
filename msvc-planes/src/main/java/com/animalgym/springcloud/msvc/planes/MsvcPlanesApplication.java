package com.animalgym.springcloud.msvc.planes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcPlanesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcPlanesApplication.class, args);
	}

}
