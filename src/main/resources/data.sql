insert into owner_entity (id, name, address) values (1,"owner1","owner1address")
insert into owner_entity (id, name, address) values (2,"owner2","owner2address")
insert into owner_entity (id, name, address) values (3,"owner3","owner3address")
insert into stable_entity (id, name, address) values (1, "stable1", "stable1address")
insert into stable_entity (id, name, address) values (2, "stable2", "stable2address")
insert into horse_entity (id, uuid, name, nick_name, breed, joined, feedings_per_day, owner_id, stable_id) values (1, 69, "horse1", "horse1nick", "breed1", '2024-02-02', 3, 1,1)
insert into horse_entity (id, uuid, name, nick_name, breed, joined, feedings_per_day, owner_id, stable_id) values (2, 42, "horse2", "horse2nick", "breed2", '2024-03-03', 4, 3,1)
insert into horse_entity (id, uuid, name, nick_name, breed, joined, feedings_per_day, owner_id, stable_id) values (3, UUID_TO_BIN('aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee'), "horse3", "horse3nick", "breed3", '2024-01-01', 5,1,2)
insert into feeding_entity (id,start_weight,end_weight,ate_all,done,start_time,end_time,horse_id) values (1,5,1,true,false,'10:00:00','10:30:00',1)
insert into feeding_entity (id,start_weight,end_weight,ate_all,done,start_time,end_time,horse_id) values (2,5,1,false,true,'08:00:00','08:30:00',2)
insert into feeding_entity (id,start_weight,end_weight,ate_all,done,start_time,end_time,horse_id) values (3,5,1,false,false,'12:00:00','12:30:00',1)
insert into feeding_entity (id,start_weight,end_weight,ate_all,done,start_time,end_time,horse_id) values (4,5,1,false,false,'11:00:00','11:30:00',3)
insert into food_entity (id, name, weight) values (1, "food1",3)
insert into food_entity (id, name, weight) values (2, "food2",5)
insert into food_entity (id, name, weight) values (3, "food3",2)
insert into food_entity (id, name, weight) values (4, "food4",1)



