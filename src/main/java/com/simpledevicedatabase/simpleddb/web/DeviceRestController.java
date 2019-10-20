package com.simpledevicedatabase.simpleddb.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.simpledevicedatabase.simpleddb.domain.Device;
import com.simpledevicedatabase.simpleddb.domain.DeviceModel;
import com.simpledevicedatabase.simpleddb.domain.DeviceModelRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceType;
import com.simpledevicedatabase.simpleddb.domain.DeviceTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/devices")
public class DeviceRestController {

    @Autowired DeviceRepository deviceRepository;
    @Autowired DeviceTypeRepository deviceTypeRepository;
    @Autowired DeviceModelRepository deviceModelRepository;

    @PostMapping(produces = "application/json")
    ResponseEntity<Device> addDevice(@Valid @RequestBody Device device) {
        deviceRepository.save(device);
        return ResponseEntity.ok(device);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<Device> saveDevice(@Valid @RequestBody Device newDevice, @PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        /*device.setDeviceId()
		device.setName = name;
		device.setType = type;
		device.setModel = model;
		device.setIpAddress = ipAddress;
		device.setMacAddress = macAddress;
		device.setSerial = serial;
		device.setPurchaseDate = purchaseDate;
		device.setComment = comment;
        device.setName(newType.getName());
        deviceRepository.save(device);*/
        return ResponseEntity.ok(device);
    }

    @GetMapping(produces = { "application/hal+json" })
    Resources<?> getDevices() {
        List<Device> allDevices = deviceRepository.findAll();
        Link link = linkTo(DeviceRestController.class).withSelfRel();
        if(allDevices.size() != 0) {
            for (Device device : allDevices) {
                Long deviceId = device.getDeviceId();
                Link selfLink = linkTo(DeviceRestController.class).slash(deviceId).withSelfRel();
                Link typeLink = linkTo(methodOn(DeviceRestController.class).getDeviceType(deviceId)).withRel("type");
                Link modelLink = linkTo(methodOn(DeviceRestController.class).getDeviceModel(deviceId)).withRel("model");
                device.add(selfLink);
                device.add(typeLink);
                device.add(modelLink);
            }
            Resources<Device> result = new Resources<Device>(allDevices, link);
            return result;
        } else {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(Device.class);
            Resources<?> result = new Resources<>(Arrays.asList(wrapper), link);
            return result;
        }
    }

    /*@GetMapping(value = "/{id}", produces = { "application/hal+json" })
    Resource<Device> getDevice(@PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID: " + id));
        Link selfLink = linkTo(DeviceRestController.class).slash(id).withSelfRel();
        Link typeLink = linkTo(methodOn(DeviceRestController.class).getDeviceType(id)).withRel("type");
        Link modelLink = linkTo(methodOn(DeviceRestController.class).getDeviceModel(id)).withRel("model");
        device.add(selfLink);
        device.add(typeLink);
        device.add(modelLink);
        Resource<Device> result = new Resource<Device>(device);
        return result;
    }*/

    @GetMapping(value = "/{id}/type", produces = { "application/hal+json" })
    Resource<DeviceType> getDeviceType(@PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID: " + id));
        DeviceType type = device.getType();
        Link selfLink = linkTo(methodOn(DeviceTypeRestController.class).getType(type.getTypeId())).withSelfRel();
        type.add(selfLink);
        Resource<DeviceType> result = new Resource<DeviceType>(type);
        return result;
    }

    @GetMapping(value = "/{id}/model", produces = { "application/hal+json" })
    Resource<DeviceModel> getDeviceModel(@PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID: " + id));
        DeviceModel model = device.getModel();
        Link selfLink = linkTo(methodOn(DeviceModelRestController.class).getModel(model.getModelId())).withSelfRel();
        model.add(selfLink);
        Resource<DeviceModel> result = new Resource<DeviceModel>(model);
        return result;
    }
}