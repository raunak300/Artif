package com.rbm.artif.controller;

import com.rbm.artif.Exception.ArtifException;
import com.rbm.artif.Exception.InvalidCredentialException;
import com.rbm.artif.Exception.UserExistException;
import com.rbm.artif.dto.ResponseObjectLoginSignup;
import com.rbm.artif.dto.UsersDTO;
import com.rbm.artif.service.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private Environment environment;

    @PostMapping("/signup")
    public ResponseEntity<ResponseObjectLoginSignup> signUpController(@RequestBody UsersDTO user){
        UsersDTO userObj= userService.signupUser(user);
        ResponseObjectLoginSignup obj= new ResponseObjectLoginSignup(userObj,environment.getProperty("USER_CREATED"));
        return new ResponseEntity<>(obj ,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginController(@RequestBody UsersDTO user) throws InvalidCredentialException {
        String jwt = userService.loginUser(user);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
