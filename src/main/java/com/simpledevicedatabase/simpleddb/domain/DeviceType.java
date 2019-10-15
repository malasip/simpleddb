package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Table;


@Entity
@Table(name="device_type")
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "type_id")
    private Long type_id;

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
        return type_id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.type_id = id;
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