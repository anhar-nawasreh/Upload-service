package com.stream.upload.video.webapp.controller;

import com.stream.upload.video.webapp.model.SignUpRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {
    final private WebClient.Builder webClientBuilder;
    @PostMapping
    public Mono<ResponseEntity<String>> signUp(@RequestBody SignUpRequestDTO user)
    {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8082/api/v1/users")
                .bodyValue(user)
                .exchangeToMono(response -> {
                    HttpStatus statusCode = (HttpStatus) response.statusCode();
                        return response.bodyToMono(String.class)
                                .map(body -> ResponseEntity.status(statusCode).body(body));

                });
    }


}
