package com.rbm.artif.dto;

import lombok.*;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class ResponseObjectLoginSignup {
    UsersDTO user;
    String message;

    public ResponseObjectLoginSignup(UsersDTO userObj, String userCreated) {
        this.user = userObj;
        this.message = userCreated;
    }
}
