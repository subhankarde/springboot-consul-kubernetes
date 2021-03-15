package com.heros.kubernetes.supermanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableConfigurationProperties(value = SupermanConfiguration.class)
public class SupermanserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupermanserviceApplication.class, args);
	}

}
