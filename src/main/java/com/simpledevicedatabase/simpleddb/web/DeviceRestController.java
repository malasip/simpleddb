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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    ResponseEntity<?> addDevice(@Valid @RequestBody Device device) {
        try {
            deviceRepository.save(device);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Duplicate entry");
        }
        return ResponseEntity.ok(device);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<Device> saveDevice(@Valid @RequestBody Device newDevice, @PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        if(newDevice.getName() != "") { device.setName(newDevice.getName()); }
        if(newDevice.getType() != null) { device.setType(newDevice.getType()); }
        if(newDevice.getModel() != null) { device.setModel(newDevice.getModel()); }
        if(newDevice.getIpAddress() != "") { device.setIpAddress(newDevice.getIpAddress()); }
        if(newDevice.getMacAddress() != "") { device.setMacAddress(newDevice.getMacAddress()); }
        if(newDevice.getSerial() != "") { device.setSerial(newDevice.getSerial()); }
        if(newDevice.getPurchaseDate() != null) { device.setPurchaseDate(newDevice.getPurchaseDate()); }
        if(newDevice.getComment() != "") { device.setComment(newDevice.getComment()); }
        deviceRepository.save(device);
        return ResponseEntity.ok(device);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        return deviceRepository.findById(id).map(m -> {
            deviceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
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

    @GetMapping(value = "/{id}", produces = { "application/hal+json" })
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
    }

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