-- User Authorities
insert into users(username, password, enabled, apikey, email)
values ('Aadofzo', 'Ofzoiets1986', true, 713468367, 'aadofzo@gmail.com');
-- insert into "current_user"(id, username, password, enabled, apikey, email, artistname)
-- values ('1','Example User', 'ExamplePassword1', true, 713468367, 'example@email.com', 'artistname example');
insert into authorities(username, authority)
values ('Aadofzo', 'ADMIN'), ('Aadofzo', 'USER');

