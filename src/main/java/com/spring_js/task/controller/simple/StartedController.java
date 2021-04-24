package com.spring_js.task.controller.simple;


import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Transactional
public class StartedController {

    @GetMapping("/")
    public String LoginPage(){
        return "login";
    }


}
