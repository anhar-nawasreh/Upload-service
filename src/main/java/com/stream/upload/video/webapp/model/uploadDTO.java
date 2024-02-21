package com.stream.upload.video.webapp.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class uploadDTO {

    private String name;

    private String path;

    private String publisherName;



}
