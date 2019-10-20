package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DeviceTypeRepository extends CrudRepository<DeviceType, Long> { 
    List<DeviceType> findAll();
}