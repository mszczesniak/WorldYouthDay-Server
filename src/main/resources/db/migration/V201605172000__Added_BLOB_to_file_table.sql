DROP TABLE POINTS_OF_INTEREST_IMAGES;
DROP TABLE FILES;

CREATE TABLE FILES
(
    id VARCHAR(128) PRIMARY KEY NOT NULL,
    content bytea NOT NULL,
    type VARCHAR(40) NOT NULL,
    version NUMERIC NOT NULL
);

CREATE TABLE POINTS_OF_INTEREST_IMAGES
(
    id_poi VARCHAR(128) REFERENCES POINTS_OF_INTEREST(id_poi),
    id_file VARCHAR(128) REFERENCES FILES(id),
    PRIMARY KEY (id_poi, id_file)
);