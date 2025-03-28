package com.microcompany.accountsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.microcompany.accountsservice.model")
public class AccountsserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsserviceApplication.class, args);
		System.out.print("Account Service Application");
	}
	
}
