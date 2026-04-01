package com.rbm.artif.security;

import com.rbm.artif.entity.Users;
import com.rbm.artif.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> user = authRepo.findByEmail(email);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("USER_NOT_FOUND");
        }
        Users currUser= (Users) user.get();
        return new CustomUserDetails(currUser);
    }
}

