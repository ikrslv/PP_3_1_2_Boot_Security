package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collections;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Страница с ролью доступа ROLE_USER:
    @GetMapping("/user")
    public String showUserInfo(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    //Страница с ролью доступа ROLE_ADMIN:
    @GetMapping("/admin/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all-users";
    }

    //Ссылка на форму для создания User с ролью доступа ROLE_ADMIN
    @GetMapping("/admin/user-create")
    public String createUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-create";
    }

    //Сервис для создания User
    @PostMapping("/admin/user-create")
    public String addNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        user.setRoles(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(User user){
        userService.saveUser(user);
        return "redirect:admin/users";
    }

}
