package com.Todoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TodoserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoserviceApplication.class, args);
	}

}
