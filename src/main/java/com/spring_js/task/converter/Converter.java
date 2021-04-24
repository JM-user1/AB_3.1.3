package com.spring_js.task.converter;

import com.spring_js.task.model.User;
import com.spring_js.task.model.UserRole;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Converter {

    public String roleSetToString(User user) {
        return user.getRoleSet()
                .stream()
                .map(UserRole::getName)
                .collect(Collectors.joining(", "));
    }

}
