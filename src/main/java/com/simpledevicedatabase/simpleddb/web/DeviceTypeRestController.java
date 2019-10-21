package com.simpledevicedatabase.simpleddb.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.simpledevicedatabase.simpleddb.domain.Device;
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
@RequestMapping(value = "/api/deviceTypes", produces = { "application/hal+json" })
public class DeviceTypeRestController {

    @Autowired DeviceTypeRepository typeRepository;
    @Autowired DeviceRepository deviceRepository;

    @PostMapping(produces = "application/json")
    ResponseEntity<?> addType(@Valid @RequestBody DeviceType type) {
        try {
            typeRepository.save(type);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Duplicate entry");
        }
        return ResponseEntity.ok(type);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<DeviceType> saveType(@Valid @RequestBody DeviceType newType, @PathVariable Long id) {
        DeviceType type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        type.setName(newType.getName());
        typeRepository.save(type);
        return ResponseEntity.ok(type);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<?> deleteType(@PathVariable Long id) {
        return typeRepository.findById(id).map(m -> {
            typeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
    }

    @GetMapping
    Resources<?> getAllTypes() {
        List<DeviceType> allTypes = typeRepository.findAll();
        Link link = linkTo(DeviceTypeRestController.class).withSelfRel();
        if(allTypes.size() != 0) {
            for (DeviceType type : allTypes) {
                Long typeId = type.getTypeId();
                Link selfLink = linkTo(DeviceTypeRestController.class).slash(typeId).withSelfRel();
                type.add(selfLink);
                Link devicesLink = linkTo(methodOn(DeviceTypeRestController.class).getTypeDevices(typeId)).withRel("devices");
                type.add(devicesLink);
            }
            Resources<DeviceType> result = new Resources<DeviceType>(allTypes, link);
            return result;
        } else {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(DeviceType.class);
            Resources<?> result = new Resources<>(Arrays.asList(wrapper), link);
            return result;
        }
    }

    @GetMapping("/{id}")
    Resource<DeviceType> getType(@PathVariable Long id) {
        DeviceType type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        Link selfLink = linkTo(DeviceTypeRestController.class).slash(id).withSelfRel();
        type.add(selfLink);
        Link devicesLink = linkTo(methodOn(DeviceTypeRestController.class).getTypeDevices(id)).withRel("devices");
        type.add(devicesLink);
        Resource<DeviceType> result = new Resource<DeviceType>(type);
        return result;
    }

    @GetMapping("/{id}/devices")
    Resources<?> getTypeDevices(@PathVariable Long id) {
        DeviceType type = typeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        Link link = linkTo(methodOn(DeviceTypeRestController.class).getTypeDevices(id)).withSelfRel();
        List<Device> devices = type.getDevices();
        if(devices.size() != 0) {
            for (Device device : devices) {
                Link selfLink = linkTo(DeviceRestController.class).slash(device.getDeviceId()).withSelfRel();
                device.add(selfLink);
            }
            Resources<Device> result = new Resources<Device>(devices, link);
            return result;
        } else {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(Device.class);
            Resources<?> result = new Resources<>(Arrays.asList(wrapper), link);
            return result;  
        }
    }
}