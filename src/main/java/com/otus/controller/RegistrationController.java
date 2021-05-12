package com.otus.controller;

import com.otus.dao.UserDao;
import com.otus.dao.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Client client, Map<String, Object> model) {
        Client clientFromDb = userDao.loadClientByUsername(client.getUsername());
        if (clientFromDb != null) {
            model.put("message", "Такой пользователь уже существует!");
            return "registration";
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        userDao.insertClient(client);
        return "redirect:/login";
    }
}