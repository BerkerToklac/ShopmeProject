package com.shopme.admin.user.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping
    public String viewHomePage(){
        return "index";
    }
    @GetMapping("/login")
    public String viewLoginPage(){

        return "login";
    }

}
