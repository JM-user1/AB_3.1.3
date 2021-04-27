package com.spring_js.task.controller.simple;

import com.spring_js.task.converter.Converter;
import com.spring_js.task.model.User;
import com.spring_js.task.service.interfaces.RoleService;
import com.spring_js.task.service.interfaces.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/admin/")
@Transactional
public class AdminController {

    final UserService userService;
    final RoleService roleService;
    final Converter converter;

    public AdminController(UserService userService, RoleService roleService, Converter converter) {
        this.userService = userService;
        this.roleService = roleService;
        this.converter = converter;
    }


    @GetMapping("users")
    public ModelAndView index(@AuthenticationPrincipal User admin) {
        return getModelAndView(admin);
    }


    @PostMapping("adduser")
    public ModelAndView createUser(@RequestParam("roles") Set<String> roles, @AuthenticationPrincipal User admin, User user) {
        user.setRoleSet(roleService.getRoleSet(roles));
        userService.addUser(user);
        return getModelAndView(admin);
    }

    @PostMapping("updateUser")
    public ModelAndView updateUser(@RequestParam("roles") Set<String> roles, @AuthenticationPrincipal User admin, User user) {

        user.setRoleSet(roleService.getRoleSet(roles));

        userService.updateUser(user);

        return getModelAndView(admin);
    }

    @PostMapping("delete")
    public ModelAndView deleteUser(@RequestParam("id") Long id, @AuthenticationPrincipal User admin) {
        userService.deleteUser(id);
        return getModelAndView(admin);
    }

    private ModelAndView getModelAndView(User admin) {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView("admin/users");
        String stringRoles = converter.roleSetToString(admin);
        modelAndView.addObject("allUsers", admin)
                .addObject("user", user)
                .addObject("userRoles", stringRoles)
                .addObject("addUser", userService.listUsers());
        return modelAndView;
    }


}