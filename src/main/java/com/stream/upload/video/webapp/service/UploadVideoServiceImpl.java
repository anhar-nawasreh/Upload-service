package com.stream.upload.video.webapp.service;

import com.stream.upload.video.webapp.validator.VideoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UploadVideoServiceImpl implements UploadVideoService{

    private final VideoValidator videoValidator;

    @Override
    public void uploadVideo(MultipartFile videoFile) {
        videoValidator.validateVideo(videoFile);

    }
}
