package com.spring_js.task.service.impl;

import com.spring_js.task.model.User;
import com.spring_js.task.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User loadUserByUsername(String username) {
        User user = userRepo.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
