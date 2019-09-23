package com.simpledevicedatabase.simpleddb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.simpledevicedatabase.simpleddb.domain.*;
/*
import com.simpledevicedatabase.simpleddb.domain.Device;
import com.simpledevicedatabase.simpleddb.domain.DeviceType;
import com.simpledevicedatabase.simpleddb.domain.DeviceModel;
import com.simpledevicedatabase.simpleddb.domain.DeviceModelRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceTypeRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceModelRepository;
*/
@SpringBootApplication
public class SimpleDeviceDatabase {

	public static void main(String[] args) {
		SpringApplication.run(SimpleDeviceDatabase.class, args);
	}

	@Bean
	public CommandLineRunner deviceDemo(DeviceRepository drepository, DeviceTypeRepository dtrepository, DeviceModelRepository dmrepository) {
		return (args) -> {
			
			dtrepository.save(new DeviceType("Desktop"));
			dtrepository.save(new DeviceType("Laptop"));
			dmrepository.save(new DeviceModel("OptiPlex 3060"));
			dmrepository.save(new DeviceModel("EliteBook 840 G6"));
			drepository.save(new Device("VJ288", dtrepository.findByName("Desktop"), dmrepository.findByName("OptiPlex 3060"), "192.168.1.15", "AB:C4:09:00:B2:C5", "ABCDEF1234", "18.05.2018"));
			drepository.save(new Device("VE192", dtrepository.findByName("Laptop"), dmrepository.findByName("EliteBook 840 G6"), "192.168.1.102", "EE:4B:30:02:2C:FF", "ZYFK898A", "03.08.2019"));
			for (DeviceType type : dtrepository.findAll()) {
				System.out.println(type.toString());
			}
			/*
			for (Device device : repository.findAll()) {
				System.out.println(device.toString());
			}
			*/
		};
	}

}