INSERT INTO authority (name) VALUES
    ('ROLE_SUPER_ADMIN'),
    ('ROLE_ADMIN');

INSERT INTO admin (id, username, email_address, password) VALUES
    (1111, 'admin', 'admin@gmail.com', '$2a$04$5gn/3csNiz5C9S8E5SI.IO9gi8WF6AHofzUW0Ynk3.V2BzTu0sbGG');

INSERT INTO user_authority (user_id, authority_id) VALUES
    (1111, 1);