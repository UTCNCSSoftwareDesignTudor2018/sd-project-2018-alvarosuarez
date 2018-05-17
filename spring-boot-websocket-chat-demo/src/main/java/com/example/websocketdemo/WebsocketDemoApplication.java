package com.example.websocketdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.websocketdemo.entities")
@EnableJpaRepositories("com.example.websocketdemo.repositories")
@ComponentScan({"com.example.websocketdemo","com.example.websocketdemo.config","com.example.websocketdemo.controller",
	"com.example.websocketdemo.entities","com.example.websocketdemo.repositories"})
public class WebsocketDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketDemoApplication.class, args);
	}
}
