package com.simpledevicedatabase.simpleddb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.simpledevicedatabase.simpleddb.domain.Device;
import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;

@SpringBootApplication
public class SimpleDeviceDatabase {

	public static void main(String[] args) {
		SpringApplication.run(SimpleDeviceDatabase.class, args);
	}

	@Bean
	public CommandLineRunner deviceDemo(DeviceRepository repository) {
		return (args) -> {
			repository.save(new Device("VJ288", "Desktop", "Dell OptiPlex 3060", "192.168.1.15", "AB:C4:09:00:B2:C5", "ABCDEF1234", "18.05.2018"));
			repository.save(new Device("VE192", "Desktop", "Dell OptiPlex 3060", "192.168.1.102", "EE:4B:30:02:2C:FF", "ZYFK898A", "03.08.2019"));
			/*
			for (Device device : repository.findAll()) {
				System.out.println(device.toString());
			}
			*/
		};
	}

}