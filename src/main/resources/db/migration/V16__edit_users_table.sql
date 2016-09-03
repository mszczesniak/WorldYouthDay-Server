DROP TABLE USERS;

CREATE TABLE USERS
(
    id VARCHAR(128) PRIMARY KEY NOT NULL,
    id_city VARCHAR(128) REFERENCES CITY(id),
    role VARCHAR(128) NOT NULL,    
    login  VARCHAR(50) NOT NULL,
    pass  VARCHAR(250)  NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL,
    last_password_reset DATE,
    version NUMERIC NOT NULL
);

insert into users values('9ac1bf56-e499-11e5-9730-9a79f06e9478',1,'ADMIN','admin','$2a$04$1Gsqi9QdzTFVJ/0kSBfNPenid0p3zIRIWYlY8M18mGhL89emWWig6','Mateusz','Szczesniak','Skiby 113, 26-060 Checiny','mszczesniak16@gmail.com',true,now(),1);
insert into users values('3e964492-f2e3-11e5-9ce9-5e5517507c66',1,'USER','user','$2a$04$1Gsqi9QdzTFVJ/0kSBfNPenid0p3zIRIWYlY8M18mGhL89emWWig6','Mateusz','Szczesniak','Skiby 113, 26-060 Checiny','mszczesniak16@gmail.com',true,now(),1);