package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Table;


@Entity
@Table(name="device_model")
public class DeviceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "model_id")
    private Long model_id;
    
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
        return model_id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.model_id = id;
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