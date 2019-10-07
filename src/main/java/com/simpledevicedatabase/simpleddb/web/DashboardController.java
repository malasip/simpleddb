package com.simpledevicedatabase.simpleddb.web;

//import com.simpledevicedatabase.simpleddb.domain.Device;
//import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        return "index";
    }
    @RequestMapping("/dashboard/admin/models")
    public String models(Model model) {
        return "deviceModels";
    }
    @RequestMapping("/dashboard/admin/types")
    public String types(Model model) {
        return "deviceTypes";
    }
    @RequestMapping("/dashboard/admin/users")
    public String users(Model model) {
        return "users";
    }
    @RequestMapping("/dashboard/modal/devicemodal")
    public String deviceModal(Model model) {
        return "devicemodal";
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