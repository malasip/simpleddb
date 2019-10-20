package com.simpledevicedatabase.simpleddb.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Long> {
    List<Device> findAll();
}