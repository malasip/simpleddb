package com.simpledevicedatabase.simpleddb.domain;

//import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DeviceTypeRepository extends CrudRepository<DeviceType, Long> {
    
    DeviceType findByName(String name);
}