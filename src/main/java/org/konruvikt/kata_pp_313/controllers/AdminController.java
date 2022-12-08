package org.konruvikt.kata_pp_313.controllers;

import org.konruvikt.kata_pp_313.models.User;
import org.konruvikt.kata_pp_313.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin")
    public String findAll(Model model){
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/";
    }

    //добавить просмотр страницы юзера
}
