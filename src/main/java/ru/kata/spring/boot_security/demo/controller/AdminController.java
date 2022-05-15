package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-create";
    }

    //Сервис для создания User
    @PostMapping("/admin/user-create")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam("rolesList") String roles) {
        //user.setRoles(roleService.getRole(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    //Удаление пользователя из БД
    @GetMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }


    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-update";
    }

    @PostMapping("/admin/user-update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id,
                             @RequestParam(value = "rolesList", required = false) String roles) {
        user.setRoles(roleService.getRole(roles));
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

}
