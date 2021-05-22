INSERT INTO authority (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_DOCTOR');

INSERT INTO admin (id, username, email_address, password) VALUES
    (1111, 'admin', 'admin@gmail.com', '$2a$04$5gn/3csNiz5C9S8E5SI.IO9gi8WF6AHofzUW0Ynk3.V2BzTu0sbGG');

INSERT INTO doctor (id, username, email_address, password) VALUES
    (1001, 'perica', 'acanikolic021@gmail.com', '$2a$04$iLVT9N/5RKaS1bMXEmueauu7pU1ZROAxtRT0x8pAGGuQtmp9E8LH.');

INSERT INTO user_authority (user_id, authority_id) VALUES
    (1111, 1),
    (1001, 2);
