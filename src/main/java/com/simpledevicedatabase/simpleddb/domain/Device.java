package com.simpledevicedatabase.simpleddb.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="device")
public class Device {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_id")
	private Long id;
	
	@NotEmpty(message = "Name is required")
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "ip_address")
	private String ipAddress;
	@Column(name = "mac_address")
	private String macAddress;
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;
	
	private String serial, comment;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private DeviceType type;

	@ManyToOne
	@JoinColumn(name = "model_id")
	private DeviceModel model;

    public Device() {}

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
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	/**
	 * @return the type
	 */
	public DeviceType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(DeviceType type) {
		this.type = type;
	}
	/**
	 * @return the model
	 */
	public DeviceModel getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(DeviceModel model) {
		this.model = model;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}
	/**
	 * @param macAddress the macAddress to set
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	/**
	 * @return the serial
	 */
	public String getSerial() {
		return serial;
	}
	/**
	 * @param serial the serial to set
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	/**
	 * @return the purchaseDate
	 */
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return super.toString();
	}
}
