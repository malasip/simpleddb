package com.simpledevicedatabase.simpleddb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.simpledevicedatabase.simpleddb.domain.User;
import com.simpledevicedatabase.simpleddb.domain.UserRepository;
import com.simpledevicedatabase.simpleddb.domain.UserRole;
import com.simpledevicedatabase.simpleddb.domain.UserRoleRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceType;
import com.simpledevicedatabase.simpleddb.domain.DeviceModel;
import com.simpledevicedatabase.simpleddb.domain.DeviceModelRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceRepository;
import com.simpledevicedatabase.simpleddb.domain.DeviceTypeRepository;

@SpringBootApplication
public class SimpleDeviceDatabase {

	public static void main(String[] args) {
		SpringApplication.run(SimpleDeviceDatabase.class, args);
	}

	/*@Bean
	public CommandLineRunner deviceDemo(
		UserRepository urepository,
		UserRoleRepository urrepository,
		DeviceRepository drepository,
		DeviceTypeRepository dtrepository,
		DeviceModelRepository dmrepository) {
		return (args) -> {
			urrepository.save(new UserRole("USER"));
			urrepository.save(new UserRole("ADMIN"));
			urepository.save(new User("admin",
				"$2a$10$Hg1Lg3TyNHngcHbuUup/4uiXbu7BVd9bERV4W5MgHiYjzM7cJq8eC",
				"admin@local",
				urrepository.findByName("ADMIN"),
				true));
			dtrepository.save(new DeviceType("Desktop"));
			dtrepository.save(new DeviceType("Laptop"));
			dmrepository.save(new DeviceModel("OptiPlex 3060"));
			dmrepository.save(new DeviceModel("EliteBook 840 G6"));
		};
	}*/
}