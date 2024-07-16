-- Users
-- insert into users(id, username, password, apikey, firstname, lastname, email)
-- VALUES (1001, 'Example_User', 'ExamplePassword1', 713468367, 'TestFirstName', 'TestLastName', 'example_user@email.com');
insert into users(id, username, password, apikey, firstname, lastname, dob, country, email, artistname)
VALUES (1001, 'Example_Admin_1', '$2a$10$wtKjdXOpg9dP..1xbxk2ouv6rWOW.kGaX1vhKxgAy.G6aGL./AGVW', '7lcjpziAtuwtTyjaIUeq', 'Test_FirstName_1', 'Test_LastName_1', '18-03-2023', 'the Netherlands','admin@testemail.com', 'Admin_Artist_Name_1'),
       (1002, 'Example_User_1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFeGFtcGxlX1VzZXJfMSIsImlhdCI6MTcxODg4MTMwNCwiZXhwIjoxNzE5NzQ1MzA0fQ.0mGAxJNNSp4_T4I9FBBQYMAEgn_PO8sbntN9jTzS8FU', 'sQXMUVcOjXe83PeUJAfN', 'Test_FirstName_1', 'Test_LastName_1', '21-05-2013', 'Belgium','user@testemail.com', 'User_Artist');
--        (1003, 'Example_User_1', 'ExamplePassword1', 713468369, 'Test_FirstName_1', 'Test_LastName_1', '19-04-2024', 'the Netherlands','user@testemail.com', 'Test_Artist_Name_1')

-- User Authorities
insert into authorities(user_id, authority)
VALUES (1001, 'ADMIN'),
       (1002, 'USER');
--        (1003, 'USER');

-- File URL's Template nu in application.properties:
-- insert into songs (id, songTitle) VALUES (1001, 'Example Song' );

-- INSERT INTO properties (key, value) VALUES ('image.url', 'https://example.com/images');
-- INSERT INTO properties (key, value) VALUES ('song.url', 'https://example.com/songs');

-- insert into images(id, imagename, imagealtname, imageurl);
-- values (101, 'TestTitle', 'TestAltTitle', 'localhost:8080/images/101');

-- insert into songs(id, songFile, songTitle, artistName, songUrl, fileName, fileSize, uploadTime, mimeType, songCollection, songCollectionType, songData)
-- VALUES (1001, 'Testfile.mp3', 'TestTitle', 'Test Artist 1', 'songs/Testfile.mp3', 'Test_Artist_Name_1', 1000, '10-10-2010', 'audio', 'TestCollection', 'DEMO', 1111);