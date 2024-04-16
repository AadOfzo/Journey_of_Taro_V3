-- User Authorities
insert into users(user_id, username, password, enabled, apikey, email)
VALUES (1001, 'Aadofzo', 'Ofzoiets1986', true, '713468367', 'aadofzo@gmail.com');
--        (1, 'TestAdmin', 'Test_Password', true, 713468368, 'Admin@testmail.com');

-- insert into users(id, username, password, apikey, firstname, lastname, email)
-- VALUES (1001, 'Example User', 'ExamplePassword1', 713468367, 'TestFirstName', 'TestLastName', 'example_user@email.com');
-- values (1001, 'Example User', 'example_user@email.com', 713468367),
--        (1002, 'Example Admin', 'example_admin@email.com', 713468368);

insert into authorities(user_id, authority)
VALUES (1001, 'ADMIN'),
       (1001, 'USER');

-- File URL's Template nu in application.properties:
-- INSERT INTO properties (key, value) VALUES ('image.url', 'https://example.com/images');
-- INSERT INTO properties (key, value) VALUES ('song.url', 'https://example.com/songs');

-- insert into images(id, imagename, imagealtname, imageurl);
-- values (101, 'TestTitle', 'TestAltTitle', 'localhost:8080/images/101');