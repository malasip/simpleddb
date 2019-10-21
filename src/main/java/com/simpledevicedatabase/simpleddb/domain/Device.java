package com.simpledevicedatabase.simpleddb.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.ResourceSupport;

@Entity
@Table(name="device")
public class Device extends ResourceSupport {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_id", nullable = false, updatable = false)
	private Long deviceId;
	
	@NotEmpty(message = "Name is required")
	@Length(max = 25)
	@Column(name = "name", unique = true)
	private String name;
	
	@Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$", message = "Invalid format")
	@Length(max = 12)
	@Column(name = "ip_address")
	private String ipAddress;

	@Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$", message = "Invalid format")
	@Length(max = 17)
	@Column(name = "mac_address")
	private String macAddress;
	
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;
	
	@Length(max = 20)
	@Column(name = "serial")
	private String serial;

	@Length(max = 500)
	@Column(name = "comment")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private DeviceType type;

	@ManyToOne
	@JoinColumn(name = "model_id")
	private DeviceModel model;

	public Device() {}
	
	public Device(Device device) {};

	public Device(String name, DeviceType type, DeviceModel model, String ipAddress, String macAddress, String serial, LocalDate purchaseDate, String comment) {
		super();
		this.name = name;
		this.type = type;
		this.model = model;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.serial = serial;
		this.purchaseDate = purchaseDate;
		this.comment = comment;
	}
	//Getters
	public Long getDeviceId() { return deviceId; }
	public String getName() { return name; }
	public DeviceType getType() { return type; }
	public DeviceModel getModel() { return model; }
	public String getIpAddress() { return ipAddress; }
	public String getMacAddress() { return macAddress; }
	public String getSerial() { return serial; }
	public LocalDate getPurchaseDate() { return purchaseDate; }
	public String getComment() { return comment; }
	//Setters
	public void setName(String name) { this.name = name; }
	public void setType(DeviceType type) { this.type = type; }
	public void setModel(DeviceModel model) { this.model = model; }
	public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
	public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
	public void setSerial(String serial) { this.serial = serial; }
	public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate;	}
	public void setComment(String comment) { this.comment = comment; }
	
	@Override
	public String toString() { return super.toString(); }
}
