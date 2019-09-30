package com.simpledevicedatabase.simpleddb.domain;

//import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DeviceModelRepository extends CrudRepository<DeviceModel, Long> {

    DeviceModel findByName(String name); 
}