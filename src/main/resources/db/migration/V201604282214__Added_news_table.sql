CREATE TABLE NEWS
(
    id VARCHAR(128) PRIMARY KEY NOT NULL,
    city_id VARCHAR(128) NOT NULL REFERENCES CITY(id),
    creation_date DATE NOT NULL,
    creation_author VARCHAR(128) NOT NULL REFERENCES USERS(id),
    modification_date DATE,
    modification_author VARCHAR(128) REFERENCES USERS(id),
    version NUMERIC NOT NULL
);

CREATE TABLE NEWS_DETAILS
(
    id VARCHAR(128) REFERENCES NEWS(id),
    language VARCHAR(20) NOT NULL,
    title VARCHAR(128) NOT NULL,
    text TEXT NOT NULL,
    PRIMARY KEY (id, language)
);

insert into NEWS values('530994c3-6ca0-4402-af96-48f134473b8f', 1, now(), '9ac1bf56-e499-11e5-9730-9a79f06e9478', NULL, NULL, 1);
insert into NEWS_DETAILS values('530994c3-6ca0-4402-af96-48f134473b8f', 'POLISH', 'Zmiana organizacji', 'Nastąpi zmiana organizacji');
insert into NEWS_DETAILS values('530994c3-6ca0-4402-af96-48f134473b8f', 'ENGLISH', 'Zmiana organizacji EN', 'Nastąpi zmiana organizacji EN');

insert into NEWS values('5dcf97ea-a940-46fe-bf4f-661141a6cf34', 1, now(), '9ac1bf56-e499-11e5-9730-9a79f06e9478', NULL, NULL, 1);
insert into NEWS_DETAILS values('5dcf97ea-a940-46fe-bf4f-661141a6cf34', 'POLISH', 'Żarło bedzie', 'Bedzie żarło że hej');
insert into NEWS_DETAILS values('5dcf97ea-a940-46fe-bf4f-661141a6cf34', 'ENGLISH', 'Żarło bedzie EN', 'Bedzie żarło że hej EN');
insert into NEWS_DETAILS values('5dcf97ea-a940-46fe-bf4f-661141a6cf34', 'GERMAN', 'Żarło bedzie DE', 'Bedzie żarło że hej DE');