package com.spring_js.task.service.impl;

import com.spring_js.task.model.User;
import com.spring_js.task.repository.UserRepo;
import com.spring_js.task.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImp implements UserService {

    UserRepo userRepo;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserServiceImp(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Transactional
    @Override
    public List<User> listUsers() {

        return userRepo.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {

        userRepo.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {

        userRepo.saveAndFlush(user);
    }

    @Transactional
    @Override
    public User getUserById(Long id) {

        return userRepo.getOne(id);
    }


}
