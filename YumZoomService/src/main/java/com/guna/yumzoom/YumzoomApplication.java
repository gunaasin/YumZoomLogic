package com.guna.yumzoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class YumzoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(YumzoomApplication.class, args);
	}
}
