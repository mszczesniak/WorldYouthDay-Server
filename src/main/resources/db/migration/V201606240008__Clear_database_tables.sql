delete from news_details;
delete from news;

delete from event_location;
delete from event_details;
delete from event;

delete from points_of_interest_images;
delete from points_of_interest_category_details;
delete from points_of_interest_details;
delete from points_of_interest;
delete from points_of_interest_category;

delete from city_phone;
delete from phone_details;
delete from phone;

delete from prayer_details;
delete from prayer;

delete from files;

delete from users;

delete from city;

INSERT INTO city (id,name,version) VALUES('4154d988-03bd-11e6-b512-3e1d05defe78','Kielce',0);

insert into users (id,id_city,role,login,pass,first_name,last_name,address,email,active,last_password_reset,version)
values('9ac1bf56-e499-11e5-9730-9a79f06e9478','4154d988-03bd-11e6-b512-3e1d05defe78','ADMIN','admin','$2a$10$hHJKRM/UV9Ap1RfW3tDYDO6uWCXi/pKNtw1eNdx87wg1MvxQNTBCC','Mateusz','Szczesniak','Skiby 113, 26-060 Checiny','mszczesniak16@gmail.com',true,CURRENT_TIMESTAMP ,0);

