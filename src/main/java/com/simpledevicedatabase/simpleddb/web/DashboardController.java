package com.simpledevicedatabase.simpleddb.web;

import com.simpledevicedatabase.simpleddb.domain.User;
import com.simpledevicedatabase.simpleddb.domain.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class DashboardController {

    @Autowired
    UserRepository urepository;

    @RequestMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) urepository.findByUsername(currentUser.getUsername());
        if(user != null) {
            model.addAttribute("uid", user.getUserId());
        }
        return "index";
    }
    @RequestMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) urepository.findByUsername(currentUser.getUsername());
        if(user != null) {
            model.addAttribute("uid", user.getUserId());
        }
        return "index";
    }
    @RequestMapping("/dashboard/admin/models")
    public String models(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) urepository.findByUsername(currentUser.getUsername());
        if(user != null) {
            model.addAttribute("uid", user.getUserId());
        }
        return "deviceModels";
    }
    @RequestMapping("/dashboard/admin/types")
    public String types(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) urepository.findByUsername(currentUser.getUsername());
        if(user != null) {
            model.addAttribute("uid", user.getUserId());
        }
        return "deviceTypes";
    }
    @RequestMapping("/dashboard/admin/users")
    public String users(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) urepository.findByUsername(currentUser.getUsername());
        if(user != null) {
            model.addAttribute("uid", user.getUserId());
        }
        return "users";
    }
    @RequestMapping("/dashboard/modal/deviceModal")
    public String deviceModal(Model model) {
        return "deviceModal";
    }
    @RequestMapping("/dashboard/modal/userModal")
    public String userModal(Model model) {
        return "userModal";
    }
    @RequestMapping("/dashboard/modal/singleValueModal")
    public String singleValueModal(Model model) {
        return "singleValueModal";
    }
}