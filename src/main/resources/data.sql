-- User Authorities

insert into users(id, username, password, enabled, apikey, email)
values (1001, 'Aadofzo', 'Ofzoiets1986', true, 713468367, 'aadofzo@gmail.com');
--        (1, 'TestAdmin', 'Test_Password', true, 713468368, 'Admin@testmail.com');

-- insert into "current_user"(id, username, password, enabled, apikey, email, artistname)
-- values ('1','Example User', 'ExamplePassword1', true, 713468367, 'example@email.com', 'artistname example');
insert into authorities(user_id, authority)
values (1001, 'ADMIN'), (1001, 'USER');

