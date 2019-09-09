package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class DeviceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long modelId;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model")
    private List<Device> devices;

    public DeviceModel() {}

    public DeviceModel(String name) {
        super();
        this.name = name;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return modelId;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.modelId = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}