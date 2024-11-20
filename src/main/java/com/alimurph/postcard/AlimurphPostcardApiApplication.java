package com.alimurph.postcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class AlimurphPostcardApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlimurphPostcardApiApplication.class, args);
	}

}
