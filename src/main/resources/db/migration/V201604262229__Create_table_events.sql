CREATE TABLE EVENT
(
    id VARCHAR(128) PRIMARY KEY NOT NULL,
    location_id VARCHAR(128) REFERENCES POINTS_OF_INTEREST(id_poi),
    event_date DATE,
    version NUMERIC NOT NULL
);

CREATE TABLE EVENT_DETAILS
(
    event_id VARCHAR(128) REFERENCES EVENT(id),
    language VARCHAR(20) NOT NULL,
    title VARCHAR(128) NOT NULL,
    description  TEXT NOT NULL,
    version NUMERIC NOT NULL,
    PRIMARY KEY (event_id, language)
);