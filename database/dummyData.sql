-- =====================
-- Dummy Data Insertion
-- =====================

-- Patients
INSERT INTO Patient (nhi, nta_number, first_name, surname) VALUES
('ABC1234', 'NTA0001', 'Emma', 'Johnson'),
('BCD2345', 'NTA0002', 'Liam', 'Williams'),
('CDE3456', 'NTA0003', 'Olivia', 'Brown'),
('DEF4567', 'NTA0004', 'Noah', 'Jones'),
('EFG5678', 'NTA0005', 'Ava', 'Smith'),
('FGH6789', 'NTA0006', 'Ethan', 'Taylor'),
('GHI7890', 'NTA0007', 'Isabella', 'Davis'),
('HIJ8901', 'NTA0008', 'Lucas', 'Wilson'),
('IJK9012', 'NTA0009', 'Mia', 'Anderson'),
('JKL0123', 'NTA0010', 'Mason', 'Thomas');

-- Support Persons
INSERT INTO Support_Person (first_name, surname, covered_by_nta, patient_nhi) VALUES
('Sarah', 'Johnson', TRUE, 'ABC1234'),
('James', 'Williams', FALSE, 'BCD2345'),
('Ella', 'Brown', TRUE, 'CDE3456'),
('Michael', 'Jones', TRUE, 'DEF4567'),
('Grace', 'Smith', FALSE, 'EFG5678'),
('Oliver', 'Davis', TRUE, 'GHI7890');

-- Service Providers
INSERT INTO Service_Provider (name, address) VALUES
('Wellington Hospital', '123 Capital Road, Wellington'),
('Auckland Health Center', '89 Queen Street, Auckland'),
('Christchurch Medical', '45 Cathedral Square, Christchurch'),
('Dunedin Support Clinic', '78 George Street, Dunedin');

-- Accommodations
INSERT INTO Accommodation (address, name) VALUES
('12 Harbor View, Wellington', 'Harbor View Lodge'),
('34 City Stay, Auckland', 'City Stay Inn'),
('56 Garden Rd, Christchurch', 'Garden Motel'),
('90 Ocean Dr, Dunedin', 'Oceanview Apartments');

-- Bookings
INSERT INTO Booking (patient_nhi, date_of_departure, date_of_return, destination, booking_status, estimated_cost, estimated_cost_for_patient, booking_created_at, service_provider_id, accommodation_address)
VALUES
('ABC1234', '2025-09-10', '2025-09-15', 'Wellington', 'Confirmed', 1200.50, 300.00, NOW() - INTERVAL '60 days', 1, '12 Harbor View, Wellington'),
('BCD2345', '2025-09-12', '2025-09-20', 'Auckland', 'Pending', 950.00, 200.00, NOW() - INTERVAL '55 days', 2, '34 City Stay, Auckland'),
('CDE3456', '2025-09-18', '2025-09-25', 'Christchurch', 'Completed', 1100.75, 250.00, NOW() - INTERVAL '45 days', 3, '56 Garden Rd, Christchurch'),
('DEF4567', '2025-09-25', NULL, 'Dunedin', 'Ongoing', 870.40, NULL, NOW() - INTERVAL '30 days', 4, '90 Ocean Dr, Dunedin'),
('EFG5678', '2025-10-01', '2025-10-05', 'Auckland', 'Confirmed', 650.00, 100.00, NOW() - INTERVAL '20 days', 2, '34 City Stay, Auckland'),
('GHI7890', '2025-10-10', '2025-10-14', 'Christchurch', 'Pending', 720.00, 180.00, NOW() - INTERVAL '10 days', 3, '56 Garden Rd, Christchurch');

-- Booked Support Persons
INSERT INTO Booked_Support_Person (booking_id, support_person_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6);

-- Notes
INSERT INTO Note (message, patient_nhi, booking_id) VALUES
('Patient requested early check-in.', 'ABC1234', 1),
('Need to confirm transport to Auckland.', 'BCD2345', 2),
('All costs covered under NTA.', 'CDE3456', 3),
('Requires wheelchair-accessible room.', 'DEF4567', 4),
('Patient prefers ground floor room.', 'EFG5678', 5),
('Waiting for confirmation from provider.', 'GHI7890', 6);

-- Patient–Support–Provider Relationships
INSERT INTO Patient_Service_Provider (frequency, service_provider_id, patient_nhi) VALUES
('22x2', 1, 'ABC1234'),
('6x6', 2, 'BCD2345'),
('LDT', 3, 'CDE3456'),
('LDT', 4, 'DEF4567'),
('LDT', 2, 'EFG5678'),
('LDT', 3, 'GHI7890');
