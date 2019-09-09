package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long typeId;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
    private List<Device> devices;

    public DeviceType() {}

    public DeviceType(String name) {
        super();
        this.name = name;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return typeId;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.typeId = id;
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