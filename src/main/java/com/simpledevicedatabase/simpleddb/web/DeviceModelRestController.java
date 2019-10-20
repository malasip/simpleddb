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

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/deviceModels", produces = { "application/hal+json" })
public class DeviceModelRestController {

    @Autowired DeviceModelRepository modelRepository;
    @Autowired DeviceRepository deviceRepository;

    @PostMapping(produces = "application/json")
    ResponseEntity<DeviceModel> addModel(@Valid @RequestBody DeviceModel model) {
        modelRepository.save(model);
        return ResponseEntity.ok(model);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<DeviceModel> saveModel(@Valid @RequestBody DeviceModel newModel, @PathVariable Long id) {
        DeviceModel model = modelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        model.setName(newModel.getName());
        modelRepository.save(model);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<?> deleteModel(@PathVariable Long id) {
        return modelRepository.findById(id).map(m -> {
            modelRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
    }

    @GetMapping(produces = "application/json")
    Resources<?> getAllModels() {
        List<DeviceModel> allModels = modelRepository.findAll();
        Link link = linkTo(DeviceModelRestController.class).withSelfRel();
        if(allModels.size() != 0) {
            for (DeviceModel model : allModels) {
                Long modelId = model.getModelId();
                Link selfLink = linkTo(DeviceModelRestController.class).slash(modelId).withSelfRel();
                model.add(selfLink);
                Link devicesLink = linkTo(methodOn(DeviceModelRestController.class).getModelDevices(modelId)).withRel("devices");
                model.add(devicesLink);
            }
            Resources<DeviceModel> result = new Resources<DeviceModel>(allModels, link);
            return result;
        } else {
            EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
            EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(DeviceModel.class);
            Resources<?> result = new Resources<>(Arrays.asList(wrapper), link);
            return result;
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    Resource<DeviceModel> getModel(@PathVariable Long id) {
        DeviceModel model = modelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        Link selfLink = linkTo(DeviceModelRestController.class).slash(id).withSelfRel();
        model.add(selfLink);
        Link devicesLink = linkTo(methodOn(DeviceModelRestController.class).getModelDevices(id)).withRel("devices");
        model.add(devicesLink);
        //Link link = linkTo(DeviceModelRestController.class).withSelfRel();
        //Resource<DeviceModel> result = new Resource<DeviceModel>(model, link);
        Resource<DeviceModel> result = new Resource<DeviceModel>(model);
        return result;
    }

    @GetMapping(value = "/{id}/devices", produces = "application/json")
    Resources<?> getModelDevices(@PathVariable Long id) {
        DeviceModel model = modelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid ID:" + id));
        Link link = linkTo(methodOn(DeviceModelRestController.class).getModelDevices(id)).withSelfRel();
        List<Device> devices = model.getDevices();
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