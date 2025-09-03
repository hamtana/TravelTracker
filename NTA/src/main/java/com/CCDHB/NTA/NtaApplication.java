package com.CCDHB.NTA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.CCDHB.NTA", "com.CCDHB.api"})
public class NtaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NtaApplication.class, args);
	}

}
