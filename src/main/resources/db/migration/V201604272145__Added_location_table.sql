CREATE TABLE LOCATION
(
    id VARCHAR(128) PRIMARY KEY NOT NULL,
    city_id VARCHAR(128) REFERENCES CITY(id),
    name VARCHAR(128) NOT NULL,
    latitude REAL NOT NULL,
    longitude  REAL NOT NULL,
    version NUMERIC NOT NULL
);

insert into LOCATION values('f2c5a870-3f05-48d0-96ba-85753c88d139', 1, 'Murek przy szkole', 12.32, 22.111, 1);
insert into LOCATION values('f1890dac-2fda-4c09-a136-de3541e74d25', 1, 'Dziura', 14.12, 4.22, 1);
insert into LOCATION values('9d5e3c08-f99d-4e9e-9a79-6aabe85d9206', 1, 'Dziura 2', 122.12, 11.111, 1);
insert into LOCATION values('ed28557b-f16c-434c-84c7-9e6e3db28a34', 2, 'Wielka kiszka', 24.33, 62.121, 1);
insert into LOCATION values('1061974a-fa7a-49bb-beb5-749a1eb3bae5', 2, 'Płot', 72.32, 28.111, 1);