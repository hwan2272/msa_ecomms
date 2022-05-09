package com.hwan2272.msaecomms;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper mMapper() {
		ModelMapper mMapper = new ModelMapper();
		mMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mMapper;
	}
}
