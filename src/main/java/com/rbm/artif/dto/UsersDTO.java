package com.rbm.artif.dto;

import com.rbm.artif.utilities.Premium;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UsersDTO {
    String email;
    String username;
    String password;
    Premium premium;
}
