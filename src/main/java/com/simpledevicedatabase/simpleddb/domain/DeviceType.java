package com.simpledevicedatabase.simpleddb.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.ResourceSupport;


@Entity
@Table(name="device_type")
public class DeviceType extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false, updatable = false)
    private Long type_id;

    @NotEmpty(message = "Name is required")
    @Length(max = 25)
    @Column(name = "name", unique = true)
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