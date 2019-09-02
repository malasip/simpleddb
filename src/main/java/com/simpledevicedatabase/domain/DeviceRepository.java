package com.simpledevicedatabase.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Long> {

    List<Device> findByDeviceName(String name);
}