package org.konruvikt.kata_pp_313.controllers;

import org.konruvikt.kata_pp_313.models.Role;
import org.konruvikt.kata_pp_313.models.User;
import org.konruvikt.kata_pp_313.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String findAll(Model model){
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(@ModelAttribute("user") User user, Model model){
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("listRoles", listRoles);
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("listRoles", listRoles);
        return "user-list";  //"user-update"
    }

    @PatchMapping("/user-update")
    public String updateUser(User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
