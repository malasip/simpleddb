package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Long> {

    List<Device> findByName(String name);
}