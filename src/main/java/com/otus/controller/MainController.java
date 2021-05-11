package com.otus.controller;

import com.otus.dao.UserDao;
import com.otus.dao.model.Client;
import com.otus.dao.model.ClientProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserDao userDao;

    @GetMapping("/")
    public String firstPage(Model model) {
        Object o = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (o instanceof String) {
            return "/login";
        }
        Client clientInfo = (Client) o;
        ClientProfile clientProfile = userDao.loadProfileById(clientInfo.getId());
        if (Objects.isNull(clientProfile)) {
            return "redirect:/addInfo";
        }
        model.addAttribute("firstName", clientProfile.getFirstName());
        model.addAttribute("lastName", clientProfile.getLastName());
        model.addAttribute("age", clientProfile.getAge());
        model.addAttribute("city", clientProfile.getCity());
        model.addAttribute("gender", clientProfile.getGender().getValue());
        model.addAttribute("hobby", clientProfile.getHobby());
        clientInfo.setProfileId(clientProfile.getId());

        List<ClientProfile> listFriend = userDao.findFriendUser(clientProfile.getId());
        model.addAttribute("clientProfiles", listFriend);
        return "profile";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        List<ClientProfile> clientProfiles = null;
        if (filter != null && !filter.isEmpty()) {
            clientProfiles = userDao.findByLastName(filter);
        } else {
            clientProfiles = Collections.emptyList();
        }
        model.addAttribute("clientProfiles", clientProfiles);
        model.addAttribute("filter", filter);
        return "search";
    }

    @PostMapping("/search")
    public String add(@AuthenticationPrincipal Client client, @RequestParam String id, Model model) {
        userDao.insertFriends(client.getProfileId(), id);
        model.addAttribute("messages", "");
        return "redirect:/";
    }

    @GetMapping("/addInfo")
    public String addInfo(@AuthenticationPrincipal Client client, Model model) {
        model.addAttribute("name", client.getUsername());
        return "/addInfo";
    }

    @PostMapping("/addInfo")
    public String addI(ClientProfile clientProfile, @AuthenticationPrincipal Client client, Model model) {
        userDao.insertClientProfile(clientProfile, client.getId());
        model.addAttribute("messages", "Информация успешно заполнена");
        return "redirect:/";
    }

}