package com.stream.upload.video.webapp.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class VideoInfoDTO {

    @NotNull(message = "You need to provide a video file")
    private MultipartFile file;
    @NotNull(message ="You need to provide a video path")
    private String path;
    @NotNull(message = "You have to provide a video name")
    private String name;

    private String publisherName;
}
