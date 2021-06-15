INSERT INTO authority (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_DOCTOR');

INSERT INTO privilege (name) VALUES
    ('CREATE_RULE'),
    ('CERT_REQ'),
    ('ALL_LOGS'),
    ('PATIENT_ACCESS');

INSERT INTO admin (id, username, email_address, password) VALUES
    (1111, 'admin', 'admin@gmail.com', '$2a$04$5gn/3csNiz5C9S8E5SI.IO9gi8WF6AHofzUW0Ynk3.V2BzTu0sbGG');

INSERT INTO doctor (id, username, email_address, password) VALUES
    (1001, 'perica', 'acanikolic021@gmail.com', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.');

INSERT INTO user_authority (user_id, authority_id) VALUES
    (1111, 1),
    (1001, 2);

INSERT INTO user_privilege (user_id, privilege_id) VALUES
    (1111, 1),
    (1111, 2),
    (1111, 3),
    (1001, 1),
    (1001, 4);

INSERT INTO patient (id, first_name, last_name, email_address, date_of_birth) VALUES
    (1021, 'Igor', 'Igoric', 'acanikolic021@gmail.com', '2000-12-08'),
    (1022, 'Janko', 'Jankovic', 'janko@gmail.com', '1988-12-08'),
    (1023, 'Marija', 'Maricic', 'marija@gmail.com', '1974-12-08'),
    (1024, 'Dusan', 'Dusanic', 'dusan@gmail.com', '1965-12-08'),
    (1025, 'Jelena', 'Jelic', 'jelena@gmail.com', '2003-12-08'),
    (1026, 'Kristina', 'Krstic', 'kristina@gmail.com', '1992-12-08'),
    (1027, 'Vuk', 'Vukovic', 'vuk@gmail.com', '1976-12-08'),
    (1028, 'Tesa', 'Tesanovic', 'tesa@gmail.com', '1952-12-08'),
    (1029, 'Milica', 'Milic', 'milica@gmail.com', '1984-12-08'),
    (1030, 'Jovana', 'Jovic', 'jovana@gmail.com', '2011-12-08');

INSERT INTO rule_template(id, use_function, min_allowed_value, max_allowed_value, min_device_alarm, max_device_alarm) VALUES
    (7001, 'Pritisak', 50, 200, 2, 1);

INSERT INTO patient_message(id, message, patient_id) VALUES
    (1, 'Patient reading from device N1K usage Pritisak was 100', 1021),
    (2 , 'Patient reading from device N1K usage Pritisak was 165', 1022),
    (3, 'Patient reading from device N1K usage Pritisak was 10', 1023);