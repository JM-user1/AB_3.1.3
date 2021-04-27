package com.spring_js.task.service.interfaces;


import com.spring_js.task.model.UserRole;

import java.util.Set;

public interface RoleService {
    Set<UserRole> getRoleSet(Set<String> roles);
    UserRole getDefaultRole();

}
