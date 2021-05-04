package com.spring_js.task.controller.rest;

import com.spring_js.task.model.User;
import com.spring_js.task.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final UserService userService;
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userList")
    public ResponseEntity<List<User>> listOfUsers(){//work
        List<User> allUsers = userService.listUsers();
        return allUsers != null && !allUsers.isEmpty()
                ? new ResponseEntity<>(allUsers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);//new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){//work
        User user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/newUser")//work
    public ResponseEntity<User> newUser(@RequestBody User user){
            userService.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {//work
            userService.updateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {//work
            userService.deleteUser(id);
        return new ResponseEntity<>("User with id " + id + " was deleted", HttpStatus.OK);
    }

}
