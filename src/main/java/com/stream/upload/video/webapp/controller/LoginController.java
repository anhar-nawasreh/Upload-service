package com.stream.upload.video.webapp.controller;

import com.stream.upload.video.webapp.model.SignInRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    final private WebClient.Builder webClientBuilder;

    @PostMapping
    public Mono<ResponseEntity<?>> login(@RequestBody SignInRequestDTO signInRequestDTO) {
        return webClientBuilder.build()
                .post()
                .uri("http://authentication-service:8082/api/v1/users")
                .bodyValue(signInRequestDTO)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return Mono.just(ResponseEntity.ok().headers(response.headers().asHttpHeaders()).body(response.bodyToMono(String.class)));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                    }
                });

    }
}
