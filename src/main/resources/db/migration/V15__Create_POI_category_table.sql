DROP TABLE POINTS_OF_INTEREST_DETAILS;
DROP TABLE POINTS_OF_INTEREST;

CREATE TABLE POINTS_OF_INTEREST_CATEGORY
(
    id_poi_category VARCHAR(128) NOT NULL,
    language VARCHAR(20) NOT NULL,
    name VARCHAR(128) NOT NULL,
    version NUMERIC NOT NULL,
    PRIMARY KEY (id_poi_category, language)
);

CREATE TABLE POINTS_OF_INTEREST
(
    id_poi VARCHAR(128) PRIMARY KEY NOT NULL,
    latitude REAL NOT NULL,
    longitude  REAL NOT NULL,
    category_id VARCHAR(128),
    version NUMERIC NOT NULL
);

CREATE TABLE POINTS_OF_INTEREST_DETAILS
(
    poi_id VARCHAR(128) REFERENCES POINTS_OF_INTEREST(id_poi),
    language VARCHAR(20) NOT NULL,
    name VARCHAR(128) NOT NULL,
    description  TEXT NOT NULL,
    version NUMERIC NOT NULL,
    PRIMARY KEY (poi_id, language)
);

insert into POINTS_OF_INTEREST_CATEGORY values('f05fed75-d541-4d11-be21-40218ea32766', 'POLISH', 'Zamki', 1);
insert into POINTS_OF_INTEREST_CATEGORY values('f05fed75-d541-4d11-be21-40218ea32766', 'ENGLISH', 'Castles', 1);

insert into POINTS_OF_INTEREST values('b9288672-14d9-42cc-a855-f4f9f4d75ce2', 50.797222, 20.460278, 'f05fed75-d541-4d11-be21-40218ea32766', 1);

insert into POINTS_OF_INTEREST_DETAILS values('b9288672-14d9-42cc-a855-f4f9f4d75ce2', 'POLISH', 'Zamek w Chęcinach', 'Pikny zamek, że hej!', 1);
insert into POINTS_OF_INTEREST_DETAILS values('b9288672-14d9-42cc-a855-f4f9f4d75ce2', 'ENGLISH', 'Castle in Chęciny', 'Wonderful castle!', 1);


