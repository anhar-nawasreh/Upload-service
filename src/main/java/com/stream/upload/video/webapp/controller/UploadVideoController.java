package com.stream.upload.video.webapp.controller;

import com.stream.upload.video.webapp.model.VideoInfoDTO;
import com.stream.upload.video.webapp.model.uploadDTO;
import com.stream.upload.video.webapp.service.UploadVideoService;
import com.stream.upload.video.webapp.validator.VideoValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")

public class UploadVideoController {

    final private  UploadVideoService uploadVideoService;
    final private WebClient.Builder webClientBuilder;
    final private VideoValidator videoValidator;

    private Mono<ResponseEntity<HttpStatus>> uploadVideoInfo(VideoInfoDTO videoInfoDTO) {
        return webClientBuilder.build()
                .post()
                .uri("http://video-storage-service:8084/api/v1/videos")
                .bodyValue(new uploadDTO(videoInfoDTO.getName(),videoInfoDTO.getPath(), videoInfoDTO.getPublisherName()))
                .exchange()
                .flatMap(response -> {
                    HttpStatus status = (HttpStatus) response.statusCode();
                    return Mono.just(new ResponseEntity<>(status));
                });


    }

    @PostMapping
    public Mono<ResponseEntity<HttpStatus>> uploadVideo(@ModelAttribute @Valid VideoInfoDTO videoInfoDTO , @RequestHeader("Authorization") String token)
    {

        videoValidator.validateVideo(videoInfoDTO.getFile());
        return webClientBuilder.build()
                .get()
                .uri("http://authentication-service:8082/api/v1/authentication")
                .header("Authorization", token)
                .retrieve()
                .toBodilessEntity()
                .flatMap(response -> {
                    HttpStatus status = (HttpStatus) response.getStatusCode();
                    if (status.equals(HttpStatus.UNAUTHORIZED)) {
                        return Mono.error(new RuntimeException("Unauthorized. Please log in first."));
                    } else if (status.is2xxSuccessful()) {
                        return uploadVideoInfo(videoInfoDTO)
                                .flatMap(videoStatus -> {
                                    if (!videoStatus.getStatusCode().is2xxSuccessful()) {
                                        return Mono.just(videoStatus);
                                    }
                                    try {
                                        return uploadVideoFile(videoInfoDTO.getPath()+"/"+videoInfoDTO.getName(), videoInfoDTO.getFile())
                                                .flatMap(Mono::just);

                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                    } else {
                        return Mono.error(new RuntimeException("Unexpected response: " + status));
                    }
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    HttpStatus status = (HttpStatus) ex.getStatusCode();
                    if (status == HttpStatus.UNAUTHORIZED) {
                        return Mono.just(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
                    } else {
                        return Mono.just(new ResponseEntity<>(status)); // Return the status code instead of throwing an error
                    }
                });

    }
    private Mono<ResponseEntity<HttpStatus>> uploadVideoFile(String videoFilePath, MultipartFile file) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://file-storage-service:8083/api/v1/files/" + videoFilePath, request, String.class);
            HttpStatus status = (HttpStatus) response.getStatusCode();
            return Mono.just(new ResponseEntity<>(status));
        } catch (HttpClientErrorException.Conflict ex) {
            HttpStatus status = (HttpStatus) ex.getStatusCode();
            return  Mono.just(new ResponseEntity<>(status));
        }

    }


}
