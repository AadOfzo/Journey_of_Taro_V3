-- Users
-- insert into users(id, username, password, apikey, firstname, lastname, email)
-- VALUES (1001, 'Example_User', 'ExamplePassword1', 713468367, 'TestFirstName', 'TestLastName', 'example_user@email.com');
insert into users(id, username, password, apikey, firstname, lastname, dob, country, email, artistname)
VALUES (1001, 'Example_Admin_2', 'ExamplePassword1', 713468367, 'Test_FirstName_1', 'Test_LastName_1', '18-03-2023', 'the Netherlands','admin@testemail.com', 'Test_Artist_Name_1'),
       (1002, 'Example_Admin_1', '$2a$10$wtKjdXOpg9dP..1xbxk2ouv6rWOW.kGaX1vhKxgAy.G6aGL./AGVW', '7lcjpziAtuwtTyjaIUeq', 'Test_FirstName_1', 'Test_LastName_1', '18-03-2023', 'the Netherlands','admin@testemail.com', 'Admin_Artist_Name_1'),
       (1003, 'Test_User_1', '$2a$10$KWTh.PMgGLMfGTfbco9ZBenl9fYH49EULw56qWmzgBQrOu5m2qoNi', '5sWdDhH8Cieba5Or55tN', 'firstname example2', 'lastname example', '2023-05-19', 'the Netherlands','user@testemail.com', 'User_Artist');
--        (1003, 'Example_User_1', 'ExamplePassword1', 713468369, 'Test_FirstName_1', 'Test_LastName_1', '19-04-2024', 'the Netherlands','user@testemail.com', 'Test_Artist_Name_1')

-- User Authorities
insert into authorities(user_id, authority)
VALUES (1001, 'ADMIN'),
       (1002, 'ADMIN');
--        (1003, 'USER');

-- File URL's Template nu in application.properties:
-- INSERT INTO properties (key, value) VALUES ('image.url', 'https://example.com/images');
-- INSERT INTO properties (key, value) VALUES ('song.url', 'https://example.com/songs');
--
-- insert into images(id, imagename, imagealtname, imageurl);
-- values (101, 'TestTitle', 'TestAltTitle', 'localhost:8080/images/101');

-- insert into songs(id, songFile, songTitle, artistName, songUrl, fileName, fileSize, uploadTime, mimeType, songCollection, songCollectionType, songData)
-- VALUES (1001, 'Testfile.mp3', 'TestTitle', 'Test Artist 1', 'songs/Testfile.mp3', 'Test_Artist_Name_1', 1000, '10-10-2010', 'audio', 'TestCollection', 'DEMO', 1111);

-- -- Insert sample song collections
-- INSERT INTO son(song_collection_title, image_id, song_collection_url)
-- VALUES
--     ('Test_Collection_1', 1, 'https://example.com/collection1'),
--     ('Test_Collection_2', 2, 'https://example.com/collection2');