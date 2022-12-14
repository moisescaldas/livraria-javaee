CREATE TABLE Countries(
	ID INT NOT NULL,
	PHONE_CODE INT NOT NULL,
	ISO CHAR(2) NOT NULL,
	ISO3 CHAR(3) NOT NULL UNIQUE,
	NAME VARCHAR(64) NOT NULL,
	FULL_NAME VARCHAR(128) NOT NULL,
	PRIMARY KEY(ID)
);

CREATE TABLE SystemUsers(
	ID BIGINT NOT NULL AUTO_INCREMENT,
	EMAIL VARCHAR(64) NOT NULL UNIQUE,
	FIRST_NAME VARCHAR(24) NOT NULL,
	LAST_NAME VARCHAR(42) NOT NULL,
	SOCIAL_ID CHAR(14) NOT NULL UNIQUE,
	USER_PASSWORD VARCHAR(64),
	PRIMARY KEY(ID)
);

CREATE TABLE Addresses(
	ID BIGINT NOT NULL AUTO_INCREMENT,
	ADDRESS VARCHAR(180) NOT NULL,
	CITY VARCHAR(32) NOT NULL,
	STATE VARCHAR(32) NOT NULL,
	ZIP_CODE VARCHAR(26) NOT NULL,
	PHONE VARCHAR(26) NOT NULL,
	ID_COUNTRY INT NOT NULL,
	ID_SystemUser BIGINT NOT NULL,
	PRIMARY KEY(ID)
);

ALTER TABLE Addresses
ADD CONSTRAINT FK_Country 
FOREIGN KEY(ID_COUNTRY) REFERENCES Countries(ID);

ALTER TABLE Addresses
ADD CONSTRAINT FK_SystemUser
FOREIGN KEY(ID_SystemUser) REFERENCES SystemUsers(ID);
