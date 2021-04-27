package com.spring_js.task.service.impl;

import com.spring_js.task.model.UserRole;
import com.spring_js.task.repository.RoleRepo;
import com.spring_js.task.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service

public class RoleServiceImp implements RoleService {

    private final RoleRepo roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepo roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<UserRole> getRoleSet(Set<String> roles) {
        return null;
    }

    @Override
    public UserRole getDefaultRole() {
        return roleRepository.getRoleByName("USER");
    }
}
