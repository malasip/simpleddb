package com.simpledevicedatabase.simpleddb.web;

import java.util.List;

import com.simpledevicedatabase.simpleddb.domain.Device;
import com.simpledevicedatabase.simpleddb.domain.DeviceModelRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceTypeRepository;
//import com.simpledevicedatabase.simpleddb.domain.DeviceType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DeviceController {
    @Autowired
    private DeviceRepository drepository;
    @Autowired
    private DeviceTypeRepository dtrepository;
    @Autowired
    private DeviceModelRepository dmrepository;

    @GetMapping("/device/get")
    public String getDevice(@RequestParam(name="id", required=true)Long id, Model model) {
        drepository.findById(id).ifPresent(System.out::println);
        model.addAttribute("device", drepository.findById(id).get());
        model.addAttribute("deviceTypes", dtrepository.findAll());
        model.addAttribute("deviceModels", dmrepository.findAll());
        return "showDevice";
    }
    @GetMapping("/device/add")
    public String device(Model model) {
        model.addAttribute("device", new Device());
        model.addAttribute("deviceTypes", dtrepository.findAll());
        model.addAttribute("deviceModels", dmrepository.findAll());
        return "addDevice";
    }
    @RequestMapping(value = "/device/save", method = RequestMethod.POST)
    public String save(Device device){
        drepository.save(device);
        return "redirect:/dashboard";
    }
    @GetMapping("/device/delete")
    public String delDevice(@RequestParam(name="id", required=true)Long id, Model model) {
        drepository.deleteById(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/device/list")
    public @ResponseBody List<Device> deviceListRest() {
        return (List<Device>) drepository.findAll();
    }
}