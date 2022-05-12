package com.hwan2272.masecomms;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	public ModelMapper mMapper() {
		ModelMapper mMapper = new ModelMapper();
		mMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mMapper;
	}
}
