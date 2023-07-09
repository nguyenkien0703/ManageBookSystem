package com.example.userservice.service;

import ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.userservice.data.User;
import com.example.userservice.data.UserRepository;
import com.example.userservice.model.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User saveUser(User user ){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user ;
    }
    public UserDTO login (String username, String password  ){
        User user = userRepository.findByUsername(username);
        UserDTO dto = new UserDTO();
        if(user != null){
            BeanUtils.copyProperties(user , dto);
            if(passwordEncoder.matches(password,dto.getPassword())){
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String access_token  = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1*60*10000)))
                        .sign(algorithm);

                String refreshtoken = JWT.create()
                        .withSubject(user.getEmployeeId())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10800*60*100000)))
                        .sign(algorithm);
                dto.setToken(access_token);
                dto.setRefreshtoken(refreshtoken);
            }
        }
        return dto ;
    }




}
