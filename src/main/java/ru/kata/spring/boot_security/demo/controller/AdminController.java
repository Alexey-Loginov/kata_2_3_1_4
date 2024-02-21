package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String showAllUsers(Model model, Principal principal) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("allUser", userList);
        model.addAttribute("user", userService.findByUsername(principal.getName()).get());
        model.addAttribute("roles", roleService.findAll());
        return "admin/users";
    }

    @GetMapping("/addNewUser")
    public String newUser(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()).get());
        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", roleService.findAll());
        return "admin/user-info";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.saveUsers(user);
        return "redirect:users";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleService.findAll());
        return "admin/edit";
    }
    @PatchMapping("/edit")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
