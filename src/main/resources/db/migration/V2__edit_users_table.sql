DROP TABLE USERS;

CREATE TABLE USERS
(
    id_user VARCHAR(128) PRIMARY KEY NOT NULL,
    role VARCHAR(128) NOT NULL,    
    login  VARCHAR(50) NOT NULL,
    pass  VARCHAR(50)  NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    adress VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    version NUMERIC NOT NULL
);

insert into users values('9ac1bf56-e499-11e5-9730-9a79f06e9478','ADMIN','admin','password','Mateusz','Szczesniak','Skiby 113, 26-060 Checiny','mszczesniak16@gmail.com',true,1);