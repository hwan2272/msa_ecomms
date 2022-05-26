package com.hwan2272.msaecomms;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

//@EnableJpaAuditing //- auditing
@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public ModelMapper mMapper() {
		ModelMapper mMapper = new ModelMapper();
		mMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mMapper;
	}

	//@Bean
	//public AuditorAware auditorAwareProvider() {
	//	return () -> Optional.of(UUID.randomUUID().toString());
	// custom하여 사용
	//}
}
