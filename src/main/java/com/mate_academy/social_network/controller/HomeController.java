package com.mate_academy.social_network.controller;

import com.mate_academy.social_network.model.User;
import com.mate_academy.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("user", userService.getUser(new User()));
        return "home";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String home(@ModelAttribute("user") User user,
                       Model model){
        User existingUser = userService.getUser(user);
        if(existingUser != null) {
            model.addAttribute("title", "Home");
            model.addAttribute("user", existingUser);
        }
        return "home";
    }

    @RequestMapping(value = "/search")
    public String search(@RequestParam(value = "username", required = true) String username,
                         Model model){
        List<User> users = userService.getUserByName(username);
        if(users != null) {
            model.addAttribute("title", "Result search");
            model.addAttribute("users", users);
            return "search";
        }
        model.addAttribute("title", "User's not found");
        return "userNotFound";
    }
}