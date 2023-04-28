-- User Authorities
insert into users(username, password, enabled, apikey, email)
values ('Aadofzo', 'Ofzoiets1986', true, 713468367, 'aadofzo@gmail.com');
insert into authorities(username, authority)
values ('Aadofzo', 'ADMIN'), ('Aadofzo', 'USER');

-- SongCollectionTypes
insert into song_collection_types (song_collection_type)
values ('Demo'), ('Sample Demo'), ('Single'), ('EP'), ('Album'), ('Meditations');
-- SongCollections
