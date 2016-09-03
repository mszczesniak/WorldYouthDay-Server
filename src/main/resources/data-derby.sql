INSERT INTO city (id,name,version)
VALUES('4154d988-03bd-11e6-b512-3e1d05defe78','Kielce',0);
INSERT INTO city (id,name,version)
VALUES('4e87eacb-dd20-4eba-aeef-d9dc5f3e5507','Warszawa',0);

insert into users (id,id_city,role,login,pass,first_name,last_name,address,email,active,last_password_reset,version)
values('9ac1bf56-e499-11e5-9730-9a79f06e9478','4154d988-03bd-11e6-b512-3e1d05defe78','ADMIN','admin','$2a$04$1Gsqi9QdzTFVJ/0kSBfNPenid0p3zIRIWYlY8M18mGhL89emWWig6','Mateusz','Szczesniak','Skiby 113, 26-060 Checiny','mszczesniak16@gmail.com',true,CURRENT_TIMESTAMP ,0);


insert into users (id,id_city,role,login,pass,first_name,last_name,address,email,active,last_password_reset,version)
values('177b9ad8-17b9-11e6-b6ba-3e1d05defe78','4154d988-03bd-11e6-b512-3e1d05defe78','USER','user','$2a$04$1Gsqi9QdzTFVJ/0kSBfNPenid0p3zIRIWYlY8M18mGhL89emWWig6','Mateusz','Szczesniak','Skiby 113, 26-060 Checiny','mszczesniak16spam@gmail.com',true,CURRENT_TIMESTAMP ,0);


insert into POINTS_OF_INTEREST_CATEGORY (id_poi_category, version) values('f05fed75-d541-4d11-be21-40218ea32766', 0);
insert into POINTS_OF_INTEREST_CATEGORY_DETAILS (id_poi_category, language, name) values('f05fed75-d541-4d11-be21-40218ea32766', 'POLISH', 'Kasztan');
insert into POINTS_OF_INTEREST_CATEGORY_DETAILS (id_poi_category, language, name) values('f05fed75-d541-4d11-be21-40218ea32766', 'ENGLISH', 'Kasztan EN');
insert into POINTS_OF_INTEREST_CATEGORY_DETAILS (id_poi_category, language, name) values('f05fed75-d541-4d11-be21-40218ea32766', 'GERMAN', 'Kasztan DE');
insert into POINTS_OF_INTEREST_CATEGORY (id_poi_category, version) values('36a63514-1f9c-4266-8ab0-763cc31dae92', 0);
insert into POINTS_OF_INTEREST_CATEGORY_DETAILS (id_poi_category, language, name) values('36a63514-1f9c-4266-8ab0-763cc31dae92', 'POLISH', 'Zamek');
insert into POINTS_OF_INTEREST_CATEGORY_DETAILS (id_poi_category, language, name) values('36a63514-1f9c-4266-8ab0-763cc31dae92', 'ENGLISH', 'Castle');
insert into POINTS_OF_INTEREST_CATEGORY_DETAILS (id_poi_category, language, name) values('36a63514-1f9c-4266-8ab0-763cc31dae92', 'GERMAN', 'Die Zamek');

insert into POINTS_OF_INTEREST (id_poi, latitude, longitude, category_id, city_id, version) values('b9288672-14d9-42cc-a855-f4f9f4d75ce2', 50.797222, 20.460278, '36a63514-1f9c-4266-8ab0-763cc31dae92', '4154d988-03bd-11e6-b512-3e1d05defe78', 1);
insert into POINTS_OF_INTEREST_DETAILS (poi_id, language, name, description) values('b9288672-14d9-42cc-a855-f4f9f4d75ce2', 'POLISH', 'Zamek w Checinach', 'Pikny zamek, ze hej!');
insert into POINTS_OF_INTEREST_DETAILS (poi_id, language, name, description) values('b9288672-14d9-42cc-a855-f4f9f4d75ce2', 'ENGLISH', 'Castle in Checiny', 'Wonderful castle!');
insert into POINTS_OF_INTEREST_DETAILS (poi_id, language, name, description) values('b9288672-14d9-42cc-a855-f4f9f4d75ce2', 'FRENCH', 'Castle in Checiny', 'Wonderful castle!');

insert into POINTS_OF_INTEREST (id_poi, latitude, longitude, category_id, city_id, version) values('454f41d3-10e0-441f-a856-e63b8d3caa81', 34.797222, 22.4601278, 'f05fed75-d541-4d11-be21-40218ea32766', '4154d988-03bd-11e6-b512-3e1d05defe78', 1);
insert into POINTS_OF_INTEREST_DETAILS (poi_id, language, name, description) values('454f41d3-10e0-441f-a856-e63b8d3caa81', 'POLISH', 'Kamien przy drodze', 'Pikny kamien!');
insert into POINTS_OF_INTEREST_DETAILS (poi_id, language, name, description) values('454f41d3-10e0-441f-a856-e63b8d3caa81', 'ENGLISH', 'Kamien przy drodze EN', 'Pikny kamien! EN');
insert into POINTS_OF_INTEREST_DETAILS (poi_id, language, name, description) values('454f41d3-10e0-441f-a856-e63b8d3caa81', 'PORTUGUESE', 'Kamien przy drodze PT', 'Pikny kamien! PT');


INSERT INTO PRAYER (id,version) VALUES('238a44d8-0953-11e6-b512-3e1d05defe78',0);
INSERT INTO PRAYER_DETAILS (prayer_id,language,title,content,version) VALUES('238a44d8-0953-11e6-b512-3e1d05defe78','POLISH','Tytul1','Tresc1',0);
INSERT INTO PRAYER_DETAILS (prayer_id,language,title,content,version) VALUES('238a44d8-0953-11e6-b512-3e1d05defe78','ENGLISH','Title1','Content1',0);
INSERT INTO PRAYER (id,version) VALUES('9e4972d4-0953-11e6-b512-3e1d05defe78',0);
INSERT INTO PRAYER_DETAILS (prayer_id,language,title,content,version) VALUES('9e4972d4-0953-11e6-b512-3e1d05defe78','POLISH','Tytul2','Tresc2',0);
INSERT INTO PRAYER_DETAILS (prayer_id,language,title,content,version) VALUES('9e4972d4-0953-11e6-b512-3e1d05defe78','ENGLISH','Title2','Content2',0);

insert into NEWS (id, city_id, creation_date, creation_author, modification_date, modification_author, version) values('530994c3-6ca0-4402-af96-48f134473b8f', '4154d988-03bd-11e6-b512-3e1d05defe78', TIMESTAMP('2016-03-22 22:12:00'), '9ac1bf56-e499-11e5-9730-9a79f06e9478', NULL, NULL, 1);
insert into NEWS_DETAILS (id, language, title, text) values('530994c3-6ca0-4402-af96-48f134473b8f', 'POLISH', 'Zmiana organizacji', 'Nastapi zmiana organizacji');
insert into NEWS_DETAILS (id, language, title, text) values('530994c3-6ca0-4402-af96-48f134473b8f', 'ENGLISH', 'Zmiana organizacji EN', 'Nastapi zmiana organizacji EN');

insert into NEWS (id, city_id, creation_date, creation_author, modification_date, modification_author, version) values('5dcf97ea-a940-46fe-bf4f-661141a6cf34', '4154d988-03bd-11e6-b512-3e1d05defe78', TIMESTAMP('2016-04-01 12:11:00'), '9ac1bf56-e499-11e5-9730-9a79f06e9478', NULL, NULL, 1);
insert into NEWS_DETAILS (id, language, title, text) values('5dcf97ea-a940-46fe-bf4f-661141a6cf34', 'POLISH', 'Zarlo bedzie', 'Bedzie zarlo ze hej');
insert into NEWS_DETAILS (id, language, title, text) values('5dcf97ea-a940-46fe-bf4f-661141a6cf34', 'ENGLISH', 'Zarlo bedzie EN', 'Bedzie zarlo ze hej EN');
insert into NEWS_DETAILS (id, language, title, text) values('5dcf97ea-a940-46fe-bf4f-661141a6cf34', 'GERMAN', 'Zarlo bedzie DE', 'Bedzie zarlo ze hej DE');

insert into NEWS (id, city_id, creation_date, creation_author, modification_date, modification_author, version) values('d348b3af-092a-446a-899d-62070b33a489', '4e87eacb-dd20-4eba-aeef-d9dc5f3e5507', TIMESTAMP('2016-04-01 12:44:00'), '9ac1bf56-e499-11e5-9730-9a79f06e9478', NULL, NULL, 1);
insert into NEWS_DETAILS (id, language, title, text) values('d348b3af-092a-446a-899d-62070b33a489', 'POLISH', 'Odwolanie spotkania', 'Odwolanie spotkania hehe');
insert into NEWS_DETAILS (id, language, title, text) values('d348b3af-092a-446a-899d-62070b33a489', 'ENGLISH', 'Odwolanie spotkania EN', 'Odwolanie spotkania hehe EN');

INSERT INTO city (id,name,version) VALUES('ede9c925-e95a-448c-b5df-f2cca37cfce1','TestCity1',0);
INSERT INTO city (id,name,version) VALUES('c182d79c-5bc2-409b-b135-ea4af26608f4','TestCity2',0);
INSERT INTO city (id,name,version) VALUES('61dcec9f-82bc-4489-abd7-6a1a25f8b7b3','TestCity3',0);

INSERT INTO phone (pho_version, pho_id, pho_number) VALUES (0, '7d535251-f713-4b9f-9283-c6d90ea9332c', '999444222');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, '7d535251-f713-4b9f-9283-c6d90ea9332c', 'POLISH', 'Pan Kacper Bublik','Telefon do Kacpra');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, '7d535251-f713-4b9f-9283-c6d90ea9332c', 'ENGLISH', 'Mr Kacper Bublik','Phone to Kacper');

INSERT INTO phone (pho_version, pho_id, pho_number) VALUES (0, '09edbbb4-5a51-498d-9cab-b7f886581fff', '555888111');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, '09edbbb4-5a51-498d-9cab-b7f886581fff', 'POLISH', 'Lukasz Wesolowski','Telefon do Lukasza');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, '09edbbb4-5a51-498d-9cab-b7f886581fff', 'ENGLISH','Lukasz Wesolowski','Phone to Lukasz');


INSERT INTO phone (pho_version, pho_id, pho_number) VALUES (0, '599b0c2d-9e50-4c1e-8fe3-01ceb0f7c90f', '111777000');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, '599b0c2d-9e50-4c1e-8fe3-01ceb0f7c90f', 'POLISH', 'Pan Piotr Ludwinek','Telefon do Ludwinka');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, '599b0c2d-9e50-4c1e-8fe3-01ceb0f7c90f', 'ENGLISH','Mr Peter Ludwin','Phone to Ludwinek');

INSERT INTO phone (pho_version, pho_id, pho_number) VALUES (0, 'dae50ffd-21ce-44cd-8efa-0c5b8b3d164c', '333444111');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, 'dae50ffd-21ce-44cd-8efa-0c5b8b3d164c', 'POLISH', 'Pan Blaszek','Telefon do Blach');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, 'dae50ffd-21ce-44cd-8efa-0c5b8b3d164c', 'ENGLISH','Mr Blaszek','Phone to Blach');

INSERT INTO city_phone(cph_city, cph_phone) VALUES ('ede9c925-e95a-448c-b5df-f2cca37cfce1', '7d535251-f713-4b9f-9283-c6d90ea9332c');
INSERT INTO city_phone(cph_city, cph_phone) VALUES ('ede9c925-e95a-448c-b5df-f2cca37cfce1', '09edbbb4-5a51-498d-9cab-b7f886581fff');
INSERT INTO city_phone(cph_city, cph_phone) VALUES ('c182d79c-5bc2-409b-b135-ea4af26608f4', '09edbbb4-5a51-498d-9cab-b7f886581fff');
INSERT INTO city_phone(cph_city, cph_phone) VALUES ('c182d79c-5bc2-409b-b135-ea4af26608f4', '599b0c2d-9e50-4c1e-8fe3-01ceb0f7c90f');

INSERT INTO PHONE(pho_id,pho_version, pho_number) VALUES ('99b20497-9594-4ea4-8c6a-ea99045cd6a4', 0, '111111111');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, '99b20497-9594-4ea4-8c6a-ea99045cd6a4', 'POLISH', 'TestOwnerPL','TestDescriptionPL');
INSERT INTO phone_details(phd_version, phd_id, phd_language, phd_owner, phd_description) VALUES (0, '99b20497-9594-4ea4-8c6a-ea99045cd6a4', 'ENGLISH','TestOwnerEN','TestDescriptionEN');

INSERT INTO EVENT(id,start_date,end_date,city_id,version) VALUES ('1d996c1c-0d76-11e6-a148-3e1d05defe78',TIMESTAMP('2016-08-07 10:10:00'),TIMESTAMP('2016-08-07 10:40:00'),'4154d988-03bd-11e6-b512-3e1d05defe78',0);
INSERT INTO EVENT_LOCATION(ID_EVENT,ID_LOCATION) VALUES ('1d996c1c-0d76-11e6-a148-3e1d05defe78','b9288672-14d9-42cc-a855-f4f9f4d75ce2');
INSERT INTO EVENT_DETAILS (event_id, language, title, description, version) VALUES ('1d996c1c-0d76-11e6-a148-3e1d05defe78','ENGLISH','Koncert EN','Koncert zespolu combo lombo EN',0);
INSERT INTO EVENT_DETAILS (event_id, language, title, description, version) VALUES ('1d996c1c-0d76-11e6-a148-3e1d05defe78','POLISH','Koncert','Koncert zespolu combo lombo',0);


INSERT INTO EVENT (id,start_date,end_date,city_id,version) VALUES('2192092e-3953-11e6-ac61-9e71128cae77',TIMESTAMP('2016-08-07 21:12:00'),TIMESTAMP('2016-08-07 23:12:00'),'4e87eacb-dd20-4eba-aeef-d9dc5f3e5507',0);
INSERT INTO EVENT_DETAILS (event_id,language,title,description,version) VALUES('2192092e-3953-11e6-ac61-9e71128cae77','POLISH','TytulEventa1','TrescEvent1',0);
INSERT INTO EVENT_DETAILS (event_id,language,title,description,version) VALUES('2192092e-3953-11e6-ac61-9e71128cae77','ENGLISH','TitleEvent1','DetailsEvent1',0);
INSERT INTO EVENT_LOCATION(ID_EVENT,ID_LOCATION) VALUES ('2192092e-3953-11e6-ac61-9e71128cae77','454f41d3-10e0-441f-a856-e63b8d3caa81');

INSERT INTO EVENT (id,start_date,end_date,city_id,version) VALUES('7839890b-af6a-49b0-86cd-54a5c1b0c532',TIMESTAMP('2016-06-07 22:12:00'),TIMESTAMP('2016-08-05 11:12:00'),'4154d988-03bd-11e6-b512-3e1d05defe78',0);
INSERT INTO EVENT_DETAILS (event_id,language,title,description,version) VALUES('7839890b-af6a-49b0-86cd-54a5c1b0c532','POLISH','TytulEventa2','TrescEvent2',0);
INSERT INTO EVENT_DETAILS (event_id,language,title,description,version) VALUES('7839890b-af6a-49b0-86cd-54a5c1b0c532','ENGLISH','TitleEvent2','DetailsEvent2',0);
INSERT INTO EVENT_LOCATION(ID_EVENT,ID_LOCATION) VALUES ('7839890b-af6a-49b0-86cd-54a5c1b0c532','b9288672-14d9-42cc-a855-f4f9f4d75ce2');
INSERT INTO EVENT_LOCATION(ID_EVENT,ID_LOCATION) VALUES ('7839890b-af6a-49b0-86cd-54a5c1b0c532','454f41d3-10e0-441f-a856-e63b8d3caa81');

insert into NEWS (id, city_id, creation_date, creation_author, modification_date, modification_author, version) values('a3349cf6-421f-11e6-beb8-9e71128cae77', '4154d988-03bd-11e6-b512-3e1d05defe78', TIMESTAMP('2016-03-22 22:12:00'), '9ac1bf56-e499-11e5-9730-9a79f06e9478', NULL, NULL, 1);
insert into NEWS_DETAILS (id, language, title, text) values('a3349cf6-421f-11e6-beb8-9e71128cae77', 'POLISH', 'News z tagami', '<event id="7839890b-af6a-49b0-86cd-54a5c1b0c532">Wydarzenie</event><poi id="b9288672-14d9-42cc-a855-f4f9f4d75ce2">Zabytek</poi><prayer id="238a44d8-0953-11e6-b512-3e1d05defe78">Modlitwa</prayer>');
insert into NEWS_DETAILS (id, language, title, text) values('a3349cf6-421f-11e6-beb8-9e71128cae77', 'ENGLISH', 'News with tags', '<event id="7839890b-af6a-49b0-86cd-54a5c1b0c532">Event</event><poi id="b9288672-14d9-42cc-a855-f4f9f4d75ce2">POI</poi><prayer id="238a44d8-0953-11e6-b512-3e1d05defe78">Prayer</prayer>');
