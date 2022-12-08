//package org.konruvikt.kata_pp_313.controllers;
//
//import org.konruvikt.kata_pp_313.models.User;
//import org.konruvikt.kata_pp_313.services.UserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/registration")
//public class RegistrationController {
//
//    private final UserService userService;
//
//
//    public RegistrationController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//        return "registration";
//    }
//
//    @PostMapping
//    public String addUser(@ModelAttribute("userForm") User userForm, BindingResult result, Model model) {
//        User existingUser = userService.findUserByUserName(userForm.getUsername());
//        if (existingUser != null)
//            return "registration";
//        userService.saveUser(userForm);
//        return "redirect:/";
//    }
//}
