package com.simpledevicedatabase.simpleddb.web;

import java.util.List;

import com.simpledevicedatabase.simpleddb.domain.Device;
import com.simpledevicedatabase.simpleddb.domain.DeviceModelRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceTypeRepository;
/*import com.simpledevicedatabase.simpleddb.domain.DeviceType;
import com.simpledevicedatabase.simpleddb.domain.DeviceModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeviceController {
    @Autowired
    private DeviceRepository drepository;
    @Autowired
    private DeviceTypeRepository dtrepository;
    @Autowired
    private DeviceModelRepository dmrepository;

    // REST IMPLEMENTATION
    @GetMapping("/api/device/list")
    public @ResponseBody List<Device> deviceListRest() {
        return (List<Device>) drepository.findAll();
    }
    @GetMapping("/api/device/get/{id}")
    public @ResponseBody Device deviceGetRest(@PathVariable Long id) {
        return drepository.findById(id).get();
    }
    @GetMapping("/api/device/delete/{id}")
    public String deviceDeleteRest(@PathVariable Long id) {
        drepository.deleteById(id);
        return "redirect:/dashboard";
    }
    @GetMapping("/api/device/model/list")
    public @ResponseBody List<DeviceModel> deviceModelListRest() {
        return (List<DeviceModel>) dmrepository.findAll();
    }
    @GetMapping("/api/device/type/list")
    public @ResponseBody List<DeviceType> deviceTypeListRest() {
        return (List<DeviceType>) dtrepository.findAll();
    }
}*/