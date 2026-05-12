package com.rupiksha.cmsart;

import com.rupiksha.cmsart.config.LevinConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LevinConfig.class)
public class CmsartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsartApplication.class, args);
	}

}
