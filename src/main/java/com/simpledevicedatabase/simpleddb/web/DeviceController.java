package com.simpledevicedatabase.simpleddb.web;

import com.simpledevicedatabase.simpleddb.domain.Device;
import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DeviceController {
    @Autowired
    private DeviceRepository repository;

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("deviceList", repository.findAll());
        return "dashboard";
    }
}