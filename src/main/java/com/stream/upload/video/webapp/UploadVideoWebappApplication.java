package com.stream.upload.video.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class UploadVideoWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadVideoWebappApplication.class, args);
	}

	@Bean
	public WebClient.Builder getWebClientBuilder()
	{
		return WebClient.builder();
	}


}
