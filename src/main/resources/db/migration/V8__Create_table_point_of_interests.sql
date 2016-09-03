CREATE TABLE POINTS_OF_INTEREST
(
    id_poi INT PRIMARY KEY NOT NULL,
    latitude REAL NOT NULL,
    longitude  REAL NOT NULL,
    version NUMERIC NOT NULL
);

CREATE TABLE POINTS_OF_INTEREST_DETAILS
(
    poi_id INT REFERENCES POINTS_OF_INTEREST(id_poi),
    language VARCHAR(20) NOT NULL,
    name VARCHAR(128) NOT NULL,
    description  TEXT NOT NULL,
    version NUMERIC NOT NULL,
    PRIMARY KEY (poi_id, language)
);

insert into POINTS_OF_INTEREST values(1, 50.797222, 20.460278, 1);
insert into POINTS_OF_INTEREST_DETAILS values(1, 'POLISH', 'Zamek w Chęcinach', 'Pikny zamek, że hej!', 1);
insert into POINTS_OF_INTEREST_DETAILS values(1, 'ENGLISH', 'Castle in Chęciny', 'Wonderful castle!', 1);