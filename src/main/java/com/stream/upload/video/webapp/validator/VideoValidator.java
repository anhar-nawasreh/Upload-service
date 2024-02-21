package com.stream.upload.video.webapp.validator;

import org.springframework.web.multipart.MultipartFile;

public interface VideoValidator {

    void validateVideo(MultipartFile file);
}
