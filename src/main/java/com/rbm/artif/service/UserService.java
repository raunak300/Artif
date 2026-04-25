package com.rbm.artif.service;

import com.rbm.artif.Exception.ArtifException;
import com.rbm.artif.Exception.InvalidCredentialException;
import com.rbm.artif.Exception.UserExistException;
import com.rbm.artif.dto.UsersDTO;

public interface UserService {
    public String loginUser(UsersDTO user) throws InvalidCredentialException;

    public UsersDTO signupUser(UsersDTO user) throws UserExistException;
}
