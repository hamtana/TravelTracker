-- Create user
CREATE USER travel_user WITH PASSWORD 'secretpassword';

-- Create database
CREATE DATABASE "TravelManagement" OWNER travel_user;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE "TravelManagement" TO travel_user;

-- Patient table
CREATE TABLE Patient (
    nhi VARCHAR(7) PRIMARY KEY,
	ntaNumber VARCHAR(7),
    firstName VARCHAR(100) NOT NULL,
	surname VARCHAR(100) NOT NULL
);

-- Support Persons
CREATE TABLE SupportPerson ( 
	id SERIAL PRIMARY KEY,
	firstName VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	coveredByNta BOOLEAN NOT NULL,
	patientNhi VARCHAR(7) NOT NULL REFERENCES Patient(nhi) ON DELETE CASCADE
);

-- Service Providers
CREATE TABLE ServiceProvider(
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
	patientNhi VARCHAR(7) NOT NULL REFERENCES Patient(nhi) ON DELETE CASCADE,
	dateOfDeparture DATE NOT NULL,
	dateOfReturn DATE,
	destination VARCHAR(30),
	bookingStatus VARCHAR(15) NOT NULL,
	estimatedCost NUMERIC(10,2) NOT NULL,
	estimatedCostForPatient NUMERIC(10,2),
	bookingCreatedAt TIMESTAMP NOT NULL,
	serviceProviderId INT NOT NULL REFERENCES ServiceProvider(id) ON DELETE CASCADE,
	accommodationAddress VARCHAR(100) REFERENCES Accommodation(address) ON DELETE CASCADE
);

-- Booked Support Persons
CREATE TABLE BookedSupportPerson(
	bookingId INT NOT NULL REFERENCES Booking(id) ON DELETE CASCADE,
	supportPersonId INT NOT NULL REFERENCES SupportPerson(id),
	PRIMARY KEY (bookingId, supportPersonId)
);


