SELECT * FROM tutorials.coffee_purchased;

use itrust;
select * from patients;

select * from users;

select * from personnel;

select * from diagnosis;

select * from icdcode;
select * from icdcode where name like '%Obstetrics%';

################UC-94-SQL#################################

CREATE TABLE obstetrec (
	obstetrec_id		INT UNSIGNED AUTO_INCREMENT primary key,
	patientMID 			BIGINT(20) UNSIGNED NOT NULL,
	lmp_date            DATE NOT NULL,
	init_date           DATE NOT NULL,
	FOREIGN KEY (patientMID) REFERENCES patients(MID)
) ENGINE=INNODB;

CREATE TABLE obstetpriorpreg (
	obstetpriorpreg_id		INT UNSIGNED AUTO_INCREMENT primary key,
	patientMID 			BIGINT(20) UNSIGNED NOT NULL,
	conception_year     INT(4),
	pregnant_week		INT,
	init_date           INT,
	labor_hours			FLOAT,
	delivery_type		varchar(30),
	muliple				INT,
	FOREIGN KEY (patientMID) REFERENCES patients(MID)
) ENGINE=INNODB;

################UC-94-SQL#################################

CREATE TABLE obstetricOfficeVisit
(
	OBVisitID BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	OBhcpMID BIGINT(20) UNSIGNED NOT NULL,
	patientMID BIGINT(20) UNSIGNED NOT NULL,
	visitDate DATETIME NOT NULL,
	numberDaysPregnant SMALLINT UNSIGNED NOT NULL,
  blood_pressure VARCHAR(7) NOT NULL,
  fhr SMALLINT UNSIGNED NOT NULL,
  multiplet TINYINT UNSIGNED NOT NULL,
  llp BOOLEAN DEFAULT FALSE,
  ultrasound BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (OBVisitID),
	FOREIGN KEY (patientMID) REFERENCES patients(MID),
	FOREIGN KEY (OBhcpMID) REFERENCES personnel(MID)
) ENGINE=INNODB;

INSERT INTO obstetricOfficeVisit
(
  OBhcpMID,
  patientMID,
  visitDate,
  numberDaysPregnant,
  blood_pressure,
  fhr,
  multiplet,
  llp,
  ultrasound
) VALUES  (
  9000000012,
  402,
  "2018-10-14 10:00:00",
  90,
  '95/65',
  120,
  1,
  false,
  false
);
obstetricofficevisit
commit;

select * from obstetricOfficeVisit;

truncate obstetricOfficeVisit;