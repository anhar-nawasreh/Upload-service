package com.stream.upload.video.webapp.service;


import org.springframework.web.multipart.MultipartFile;

public interface UploadVideoService {


    void uploadVideo(MultipartFile videoFile);
}
