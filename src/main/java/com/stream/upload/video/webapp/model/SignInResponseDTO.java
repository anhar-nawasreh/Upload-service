package com.stream.upload.video.webapp.model;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SignInResponseDTO {

        private String firstName;

        private String lastName;

        private String email;

        private LocalDateTime createdTime;


}
