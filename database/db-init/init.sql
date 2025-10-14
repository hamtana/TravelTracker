-- Create user
CREATE USER travel_user WITH PASSWORD 'secretpassword';

-- Create database
CREATE DATABASE "TravelManagement" OWNER travel_user;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE "TravelManagement" TO travel_user;

-- Switch into the new database
\connect "TravelManagement"

-- Change role to travel_user for table ownership
SET ROLE travel_user;

-- Patient table
CREATE TABLE Patient (
    nhi VARCHAR(7) PRIMARY KEY,
	nta_number VARCHAR(7),
    first_name VARCHAR(100) NOT NULL,
	surname VARCHAR(100) NOT NULL
);


-- Support Persons
CREATE TABLE Support_Person ( 
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	covered_by_nta BOOLEAN NOT NULL,
	patient_nhi VARCHAR(7) NOT NULL REFERENCES Patient(nhi) ON DELETE CASCADE
);

-- Service Providers
CREATE TABLE Service_Provider(
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	address VARCHAR(100) NOT NULL
);

-- Accommodations
CREATE TABLE Accommodation(
	address VARCHAR(100) PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

-- Bookings
CREATE TABLE Booking(
	id Serial PRIMARY KEY,
	patient_nhi VARCHAR(7) NOT NULL REFERENCES Patient(nhi) ON DELETE CASCADE,
	date_of_departure DATE NOT NULL,
	date_of_return DATE,
	destination VARCHAR(30),
	booking_status VARCHAR(15) NOT NULL,
	estimated_cost NUMERIC(10,2) NOT NULL,
	estimated_cost_for_patient NUMERIC(10,2),
	booking_created_at TIMESTAMP NOT NULL,
	service_provider_id INT NOT NULL REFERENCES Service_Provider(id) ON DELETE CASCADE,
	accommodation_address VARCHAR(100) REFERENCES Accommodation(address) ON DELETE CASCADE
);

-- Booked Support Persons
CREATE TABLE Booked_Support_Person(
	booking_id INT NOT NULL REFERENCES Booking(id) ON DELETE CASCADE,
	support_person_id INT NOT NULL REFERENCES Support_Person(id),
	PRIMARY KEY (booking_id, support_person_id)
);

-- Notes Table
CREATE TABLE Note (
	id SERIAL PRIMARY KEY,
	message VARCHAR(500),
	patient_nhi VARCHAR(7) REFERENCES Patient(nhi) ON DELETE CASCADE,
	booking_id INT REFERENCES Booking(id) ON DELETE CASCADE
);

CREATE TABLE Patient_Service_Provider(
    id SERIAL PRIMARY KEY,
    frequency VARCHAR(10),
    service_provider_id INT REFERENCES Service_Provider(id) ON DELETE CASCADE,
    patient_nhi VARCHAR(7) REFERENCES Patient(nhi) ON DELETE CASCADE
);



