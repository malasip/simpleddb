package com.simpledevicedatabase.simpleddb.web;

import com.simpledevicedatabase.simpleddb.domain.Device;
import com.simpledevicedatabase.simpleddb.domain.DeviceModelRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceTypeRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeviceController {
    @Autowired
    private DeviceRepository drepository;
    @Autowired
    private DeviceTypeRepository dtrepository;
    @Autowired
    private DeviceModelRepository dmrepository;

    @GetMapping("/device")
    public String device(@RequestParam(name="id", required=true)Long id, Model model) {
        drepository.findById(id).ifPresent(System.out::println);
        model.addAttribute("device", drepository.findById(id).get());
        model.addAttribute("deviceTypes", dtrepository.findAll());
        model.addAttribute("deviceModels", dmrepository.findAll());
        return "device";
    }
}