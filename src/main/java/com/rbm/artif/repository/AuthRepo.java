package com.rbm.artif.repository;

import com.rbm.artif.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AuthRepo extends JpaRepository<Users,String> {
    public Optional findByEmail(String email);
}
