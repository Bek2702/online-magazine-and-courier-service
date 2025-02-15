package com.example.onlinecourierservices.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRegister {

    @Size(min = 3 , max = 30)
    @NotBlank
    private String userName;

    private String email;

    @Pattern(regexp = "^9989[012345789][0-9]{7}$",
    message = "invalid")
    private String phoneNumber;

    private String password;

    private String prePassword;

}
