package com.rbm.artif.service;

import com.rbm.artif.Exception.UserExistException;
import com.rbm.artif.dto.UsersDTO;
import com.rbm.artif.entity.Users;
import com.rbm.artif.repository.AuthRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void loginUser(UsersDTO user) {

    }
}
