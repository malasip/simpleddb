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

import org.springframework.hateoas.ResourceSupport;


@Entity
@Table(name="device_type")
public class DeviceType extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long type_id;

    @NotEmpty(message = "Name is required")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
    private List<Device> devices;

    public DeviceType() {}

    public DeviceType(String name) {
        super();
        this.name = name;
    }

    //Getters
    public Long getTypeId() { return type_id; }
    public String getName() { return name; }
    public List<Device> getDevices() { return devices; }
    //Setters
    public void setName(String name) { this.name = name; }
}