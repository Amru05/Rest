package com.example.MySBSCRUD.controllers;

import com.example.MySBSCRUD.entities.User;
import com.example.MySBSCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/Rest/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("{/id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }
    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "Adminhome";
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody User user,
                          BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!user.getPassword().equals(user.getPassword())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        userService.saveUser(user);
        return "redirect:/user";


    }
    @DeleteMapping("{/id}")
    public String  deleteUser(@PathVariable int id,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(id);
        }
        return "redirect:/admin";
    }
}
