DROP TABLE IF EXISTS appointment CASCADE;
DROP TABLE IF EXISTS staff_working_hours CASCADE;
DROP TABLE IF EXISTS staff CASCADE;
DROP TABLE IF EXISTS service CASCADE;
DROP TABLE IF EXISTS service_category CASCADE;
DROP TABLE IF EXISTS app_user CASCADE;
DROP TABLE IF EXISTS role CASCADE;
CREATE TABLE role(     --here will be stored admin or customer, differentiated by role_id
role_id  SERIAL PRIMARY KEY,
role_name VARCHAR(20) NOT NULL UNIQUE
);
CREATE TABLE app_user (
user_id SERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password_hash VARCHAR(255) NOT NULL,
full_name VARCHAR(100) NOT NULL,
phone VARCHAR(20),
email VARCHAR(100),
role_id INT NOT NULL,
CONSTRAINT fk_user_role
FOREIGN KEY (role_id) REFERENCES role(role_id)
);
CREATE TABLE service_category ( --makeup,hair,nails,cosmetics,BARBER
category_id SERIAL PRIMARY KEY,
category_name VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE service ( --daily makeup, event makeup, bridal makeup, light makeup
service_id SERIAL PRIMARY KEY,
service_name VARCHAR(100) NOT NULL,
price NUMERIC(8,2) NOT NULL CHECK (price > 0),
duration_minutes INT NOT NULL CHECK (duration_minutes > 0),
category_id INT NOT NULL,
CONSTRAINT fk_service_category
FOREIGN KEY (category_id) REFERENCES service_category(category_id)
);
CREATE TABLE staff ( --admins
staff_id SERIAL PRIMARY KEY,
staff_name VARCHAR(100) NOT NULL,
specialization VARCHAR(100),
phone VARCHAR(20)
);
CREATE TABLE staff_working_hours (
id          SERIAL PRIMARY KEY,
staff_id    INT NOT NULL,
work_day    VARCHAR(10) NOT NULL,
start_time  TIME NOT NULL,
end_time    TIME NOT NULL,
CONSTRAINT fk_staff_working_hours
FOREIGN KEY (staff_id) REFERENCES staff(staff_id),
CONSTRAINT chk_work_time
CHECK (end_time > start_time)
);
CREATE TABLE appointment (
appointment_id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
service_id INT NOT NULL,
staff_id INT NOT NULL,
appointment_date DATE NOT NULL,
appointment_time TIME NOT NULL,
status VARCHAR(20) NOT NULL DEFAULT 'Booked'
CHECK (status IN ('Booked', 'Cancelled', 'Completed')),
CONSTRAINT fk_appointment_user
FOREIGN KEY (user_id) REFERENCES app_user(user_id),
CONSTRAINT fk_appointment_service
FOREIGN KEY (service_id) REFERENCES service(service_id),
CONSTRAINT fk_appointment_staff
FOREIGN KEY (staff_id) REFERENCES staff(staff_id),
-- Prevent double booking
CONSTRAINT uq_staff_timeslot
UNIQUE (staff_id, appointment_date, appointment_time)
);
INSERT INTO role (role_name) VALUES
('ADMIN'),
('CUSTOMER');
INSERT INTO staff (staff_name, specialization, phone)
VALUES
('Gigel Popescu', 'Hair', '0742123456'),
('Ana Ionescu', 'Makeup', '0745987654'),
('Maria Georgescu', 'Cosmetic', '0730111222');
INSERT INTO staff_working_hours (staff_id, work_day, start_time, end_time)
VALUES
(1, 'Monday', '10:00', '18:00'),
(1, 'Tuesday', '10:00', '18:00'),
(1, 'Wednesday', '10:00', '18:00'),
(1, 'Thursday', '10:00', '18:00'),
(1, 'Friday', '10:00', '18:00'),
(2, 'Tuesday', '12:00', '20:00'),
(2, 'Wednesday', '12:00', '20:00'),
(2, 'Thursday', '12:00', '20:00'),
(2, 'Friday', '12:00', '20:00'),
(2, 'Saturday', '12:00', '20:00'),
(3, 'Wednesday', '09:00', '16:00'),
(3, 'Thursday', '09:00', '16:00'),
(3, 'Friday', '09:00', '16:00'),
(3, 'Saturday', '09:00', '16:00'),
(3, 'Sunday', '09:00', '16:00')
ON CONFLICT DO NOTHING;
-- admin1 / password: admin123
INSERT INTO app_user (username, password_hash, full_name, phone, email, role_id) VALUES
('admin1', 'TEMP', 'Administrator One', '0700000000', 'admin@yahoo.com', 1),
('admin2', 'TEMP', 'Administrator Two', '0722222222', 'admin2@yahoo.com', 1),
('user1',  'TEMP', 'Customer One', '0711111111', 'user1@yahoo.com', 2);
INSERT INTO service_category (category_name)
VALUES
('Makeup'),
('Hair'),
('Cosmetic'),
('Nails'),
('Barber');
--insert services
--makeup services
INSERT INTO service (service_name, price, duration_minutes, category_id)
VALUES
('Daily Makeup', 120, 45, 1),
('Event Makeup', 200, 60, 1),
('Bridal Makeup', 350, 90, 1),
('Light Makeup', 100, 30, 1),
('Smokey Eyes Makeup', 180, 60, 1),
('Glam Makeup', 220, 70, 1);
--hair services
INSERT INTO service (service_name, price, duration_minutes, category_id)
VALUES
('Balayage', 350, 180, 2),
('Haircut Short Hair', 70, 30, 2),
('Haircut Medium Hair', 90, 40, 2),
('Haircut Long Hair', 120, 50, 2),
('Hair Treatment', 150, 45, 2),
('Hair Wash & Blowdry', 60, 20, 2),
('Decoloration', 300, 120, 2),
('Keratin Treatment', 400, 120, 2),
('Hair Styling Curls', 150, 45, 2);
--cosmetic services
INSERT INTO service (service_name, price, duration_minutes, category_id)
VALUES
('Brow Lamination', 120, 45, 3),
('Lash Lamination', 150, 60, 3),
('Lash Treatment', 80, 30, 3),
('Lash Paint', 70, 20, 3),
('Henna Brow Paint', 90, 40, 3),
('Microdermabrasion', 200, 50, 3),
('Hydrating Facial', 180, 50, 3),
('Deep Cleansing Facial', 220, 60, 3);
--nail services
INSERT INTO service (service_name, price, duration_minutes, category_id)
VALUES
('Nail Construction', 200, 120, 4),
('Semi-Permanent Nails', 150, 90, 4),
('Gel Nails', 180, 100, 4),
('Short Nails Design', 100, 60, 4),
('Long Nails Design', 140, 75, 4),
('Nail Removal', 50, 20, 4);
--barber services
INSERT INTO service (service_name, price, duration_minutes, category_id)
VALUES
('Haircut Short', 50, 30, 5),
('Haircut Long', 70, 45, 5),
('Beard Trim', 40, 20, 5),
('Beard Treatment', 60, 30, 5),
('Full Barber Package', 120, 60, 5);
--test
INSERT INTO appointment (user_id, service_id, staff_id, appointment_date, appointment_time, status)
VALUES
(2, 1, 1, '2025-01-21', '14:00', 'Booked'),
(2, 5, 2, '2025-01-22', '12:00', 'Booked'),
(2, 12, 3, '2025-01-23', '10:00', 'Booked'),
(2, 8, 1, '2025-01-24', '16:00', 'Booked'),
(2, 20, 3, '2025-01-25', '11:00', 'Booked');