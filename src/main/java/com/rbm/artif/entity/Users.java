package com.rbm.artif.entity;

import com.rbm.artif.utilities.Premium;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class Users {
    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    @Id
    String email;

    public Premium getUserPremium() {
        return userPremium;
    }

    public void setUserPremium(Premium userPremium) {
        this.userPremium = userPremium;
    }

    @Column(nullable = false)
    String userName;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    Premium userPremium;

    public void setEmail(String email) {
        this.email = email;
    }
}
