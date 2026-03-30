package com.rbm.artif.service;

import com.rbm.artif.Exception.UserExistException;
import com.rbm.artif.dto.UsersDTO;

public interface UserService {
    public void loginUser(UsersDTO user);

    public UsersDTO signupUser(UsersDTO user) throws UserExistException;
}
