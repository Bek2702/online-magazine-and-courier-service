package com.example.onlinecourierservices.payload;

import com.example.onlinecourierservices.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthLogin {

    @NotBlank(message = MessageConstants.EMAIL_CAN_NOT_BE_EMPTY)
    private String email;

    @NotBlank(message = MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY)
    private String password;
}
