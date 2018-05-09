DROP DATABASE IF EXISTS distributedSystemAndsecurity;
CREATE DATABASE IF NOT EXISTS distributedSystemAndsecurity;
USE distributedSystemAndsecurity;

DROP TABLE IF EXISTS usersTable;
CREATE TABLE usersTable(
	clientId VARCHAR(100) NOT NULL,
    clientSK VARCHAR(100) DEFAULT NULL,
	PRIMARY KEY(clientId)
 );

DROP TABLE IF EXISTS product;
CREATE TABLE product (
    clientID varchar(45) NOT NULL,
	id int(11) NOT NULL,
	name varchar(45) DEFAULT NULL,
	manufacturer varchar(45) DEFAULT NULL,
	capacity varchar(45) DEFAULT NULL,
    status varchar(45) DEFAULT NULL,
    date datetime(6) DEFAULT NULL
);
 
INSERT INTO usersTable (clientId, clientSK) VALUES ('Bogi','9fM0ZG9XJlzB2eJHBsKH5nrIKbvQB0YPjqj2gdhomaM=');
INSERT INTO usersTable (clientId, clientSK) VALUES ('Marta','beOOh+lQWjAgWJFO2pms7//EBYLyfKrnCDu5XkcUcWw=');
INSERT INTO usersTable (clientId, clientSK) VALUES ('Vinee','XRJLbvxIbiDFV+J3cGl+OiM73nm6QOH0U7Lt8Z790u0=');

INSERT INTO product (clientID, id, name, manufacturer, capacity, status, date) 
VALUES ('Marta','1', 'CERAMIDES SMOOTHING BODY LOTION', 'ZIAJA', '400 ml', 'new', null);
INSERT INTO product (clientID, id, name, manufacturer, capacity, status, date) 
VALUES ('Marta','2', 'CUCUMBER & MINT MICELLAR GEL', 'ZIAJA', '200 ml', 'new', null);

SELECT * FROM usersTable;
SELECT * FROM product;