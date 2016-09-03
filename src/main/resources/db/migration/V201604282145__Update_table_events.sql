DROP TABLE EVENT_DETAILS;
DROP TABLE EVENT;

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

INSERT INTO EVENT(id,location_id,event_date,version) VALUES ('1d996c1c-0d76-11e6-a148-3e1d05defe78','b9288672-14d9-42cc-a855-f4f9f4d75ce2',CURRENT_TIMESTAMP,0 );
INSERT INTO EVENT_DETAILS (event_id, language, title, description, version) VALUES ('1d996c1c-0d76-11e6-a148-3e1d05defe78','ENGLISH','Koncert EN','Koncert zespolu combo lombo EN',0);
INSERT INTO EVENT_DETAILS (event_id, language, title, description, version) VALUES ('1d996c1c-0d76-11e6-a148-3e1d05defe78','POLISH','Koncert','Koncert zespolu combo lombo',0);