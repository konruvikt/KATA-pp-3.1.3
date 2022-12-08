package org.konruvikt.kata_pp_313.controllers;

import org.konruvikt.kata_pp_313.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String showUserPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",  userService.findById(id));
        return "user";
    }
}
