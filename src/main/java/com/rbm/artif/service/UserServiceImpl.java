package com.rbm.artif.service;

import com.rbm.artif.Exception.ArtifException;
import com.rbm.artif.Exception.UserExistException;
import com.rbm.artif.dto.UsersDTO;
import com.rbm.artif.entity.Users;
import com.rbm.artif.repository.AuthRepo;
import com.rbm.artif.security.CustomUserDetailsService;
import com.rbm.artif.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    AuthRepo authRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    JwtService jwtService;

    @Override
    public UsersDTO signupUser(UsersDTO user) throws UserExistException {
        Optional<Users> checkUser= authRepo.findByEmail(user.getEmail());

        if(checkUser.isPresent()){
            throw new UserExistException("USER_EMAIL_EXIST_ALREADY");
        }

        Users newUser=new Users();
        newUser.setUserName(user.getUsername());
        newUser.setUserPremium(user.getPremium());
        newUser.setEmail(user.getEmail());

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Users savedUser = authRepo.save(newUser);

        UsersDTO userObj=modelMapper.map(savedUser,UsersDTO.class);
        userObj.setPassword(null);
        return userObj;
    }

    @Override
    public String loginUser(UsersDTO user) throws ArtifException {
        try {
            Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            if (auth.isAuthenticated()) {
                return jwtService.generateToken(user.getEmail(), user.getPremium().name());
            }
            throw new ArtifException("INVALID_CREDENTIALS");
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new ArtifException("INVALID_CREDENTIALS");
        }
    }
}
