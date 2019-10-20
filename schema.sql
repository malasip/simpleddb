CREATE SCHEMA IF NOT EXISTS simpleddb;
USE simpleddb;

DROP TABLE IF EXISTS device_model;
CREATE TABLE IF NOT EXISTS device_model (
  model_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(45) NOT NULL)
ENGINE = InnoDB;

DROP TABLE IF EXISTS device_type;
CREATE TABLE IF NOT EXISTS device_type (
  type_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(45) NOT NULL)
ENGINE = InnoDB;

DROP TABLE IF EXISTS device;
CREATE TABLE IF NOT EXISTS device (
  device_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(45) NOT NULL UNIQUE,
  ip_address VARCHAR(12) NULL,
  mac_address VARCHAR(17) NULL,
  serial VARCHAR(20) NULL,
  purchase_date DATE NULL,
  comment VARCHAR(500) NULL,
  type_id INT NOT NULL,
  model_id INT NOT NULL,
  CONSTRAINT `fk_model_id`
    FOREIGN KEY (model_id) REFERENCES device_model (model_id)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Device_2`
    FOREIGN KEY (type_id) REFERENCES device_type (type_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS user_role (
  role_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(45) NOT NULL)
ENGINE = InnoDB;

DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user (
  user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(80) NULL,
  last_login DATETIME NULL,
  email VARCHAR(45) NULL,
  role_id INT NOT NULL,
  active TINYINT NOT NULL,
  CONSTRAINT `fk_User_1`
    FOREIGN KEY (role_id) REFERENCES user_role (role_id)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

select * from device;

insert into user_role (name) values ('ADMIN');
insert into user_role (name) values ('USER');
INSERT INTO user (`username`, `password`, `email`, `role_id`, `active`) VALUES('admin', '$2a$10$Hg1Lg3TyNHngcHbuUup/4uiXbu7BVd9bERV4W5MgHiYjzM7cJq8eC', 'admin@local', 1, true);
