package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name="device_model")
public class DeviceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long model_id;
    
    @NotEmpty(message = "Name is required")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model")
    private List<Device> devices;

    public DeviceModel() {}

    public DeviceModel(String name) {
        super();
        this.name = name;
    }

    //Getters
    public Long getId() { return model_id; }
    public String getName() { return name; }
    //Setters
    public void setName(String name) { this.name = name; }
}