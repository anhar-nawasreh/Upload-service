package com.stream.upload.video.webapp.validator;

import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class VideoValidatorImpl implements VideoValidator {

   private static final List<String> validContentTypes = Arrays.asList("video/mp4", "video/mpeg", "video/quicktime", "video/x-ms-wmv");

    @Override
    public void validateVideo(MultipartFile file) {
        if ( ! isVideoType(file) )
            throw new ConstraintViolationException("Uploaded file is not a valid video format.Please upload a supported video file (e.g MP4, MPEG).",null);
    }
    private boolean isVideoType(MultipartFile file)
    {
        String contentType = file.getContentType();
        return contentType != null && validContentTypes.contains(contentType.toLowerCase());
    }

}
