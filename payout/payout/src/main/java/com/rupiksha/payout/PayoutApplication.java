package com.rupiksha.payout;

import com.rupiksha.payout.config.LevinConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LevinConfig.class)
public class PayoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayoutApplication.class, args);
	}

}
