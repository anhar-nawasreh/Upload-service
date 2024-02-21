package com.stream.upload.video.webapp.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDTO {

    private String email;
    private String password;

}
