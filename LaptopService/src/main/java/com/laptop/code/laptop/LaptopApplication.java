package com.laptop.code.laptop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class LaptopApplication {


	public static void main(String[] args) {
		SpringApplication.run(LaptopApplication.class, args);
	}

}
