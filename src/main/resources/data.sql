-- Users
-- insert into users(id, username, password, apikey, firstname, lastname, email)
-- VALUES (1001, 'Example_User', 'ExamplePassword1', 713468367, 'TestFirstName', 'TestLastName', 'example_user@email.com');
insert into users(id, username, password, apikey, firstname, lastname, dob, country, email, artistname)
VALUES (1001, 'Example_Admin_2', 'ExamplePassword1', 713468367, 'Test_FirstName_1', 'Test_LastName_1', '18-03-2023', 'the Netherlands','admin@testemail.com', 'Test_Artist_Name_1'),
       (1002, 'Example_Admin_1', '$2a$10$wtKjdXOpg9dP..1xbxk2ouv6rWOW.kGaX1vhKxgAy.G6aGL./AGVW', '7lcjpziAtuwtTyjaIUeq', 'Test_FirstName_1', 'Test_LastName_1', '18-03-2023', 'the Netherlands','admin@testemail.com', 'Test_Artist_Name_1');
--        (1003, 'Example_User_1', 'ExamplePassword1', 713468369, 'Test_FirstName_1', 'Test_LastName_1', '19-04-2024', 'the Netherlands','user@testemail.com', 'Test_Artist_Name_1')
;

-- User Authorities
insert into authorities(user_id, authority)
VALUES (1001, 'ADMIN'),
       (1002, 'ADMIN');
--        (1003, 'USER');

-- File URL's Template nu in application.properties:
-- INSERT INTO properties (key, value) VALUES ('image.url', 'https://example.com/images');
-- INSERT INTO properties (key, value) VALUES ('song.url', 'https://example.com/songs');

-- insert into images(id, imagename, imagealtname, imageurl);
-- values (101, 'TestTitle', 'TestAltTitle', 'localhost:8080/images/101');