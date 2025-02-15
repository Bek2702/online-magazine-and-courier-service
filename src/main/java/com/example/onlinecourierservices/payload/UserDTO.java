package com.example.onlinecourierservices.payload;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userName;

    private String email;

    private String password;

    private String phoneNumber;
}
