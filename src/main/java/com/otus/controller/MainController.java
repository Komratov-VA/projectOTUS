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

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserDao userDao;

    @GetMapping("/")
//    public String greeting(@RequestParam(required = false,defaultValue = "") String filter,
    public String firstPage(Model model){
        Object o =SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if(o instanceof String) {
            return "/login";
        }
        Client clientInfo = (Client) o;
        ClientProfile clientProfile = userDao.loadProfileById(clientInfo.getId());
        if(Objects.isNull(clientProfile)) {
            model.addAttribute("name", clientInfo.getUsername());
            return "greeting";
        }
        model.addAttribute("firstName", clientProfile.getFirstName());
        model.addAttribute("lastName", clientProfile.getLastName());
        model.addAttribute("age", clientProfile.getAge());
        model.addAttribute("city", clientProfile.getCity());
        model.addAttribute("gender", clientProfile.getGender().getValue());
        model.addAttribute("hobby", clientProfile.getHobby());

        List<ClientProfile> listFriend = userDao.findFriendUser(clientProfile.getId());
        model.addAttribute("clientProfiles",listFriend);
        return "profile";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false,defaultValue = "") String filter, Model model){
        List<ClientProfile> clientProfiles = null;
        System.out.println("here " +filter);
        if(filter !=null && !filter.isEmpty()) {
            clientProfiles = userDao.findByLastName(filter);
        }
        else {
            clientProfiles = Collections.emptyList();
        }
        System.out.println(clientProfiles);
        model.addAttribute("clientProfiles",clientProfiles);
        model.addAttribute("filter", filter);
        return "search";
    }

    @PostMapping("/search")
    public String add(@AuthenticationPrincipal Client client, @RequestParam String id, Model model)
    {
        System.out.println(id);
        userDao.insertFriends(client.getId(), id);
        model.addAttribute("messages", "");
        return "redirect:/";
    }

//    @PostMapping("filter")
//    public String filter(@RequestParam String filter,
//                         Map<String,Object> model)
//    {
//        Iterable<Message> messages;
//
//        model.put("messages",messages);
//        return "main";
//    }
}