drop table if exists feeding_entity_foods
drop table if exists feeding_entity_seq
drop table if exists feeding_entity
drop table if exists food_entity_seq
drop table if exists food_entity
drop table if exists horse_entity_seq
drop table if exists horse_entity
drop table if exists owner_entity_seq
drop table if exists owner_entity
drop table if exists stable_entity_seq
drop table if exists stable_entity
create table feeding_entity_seq (next_val bigint) engine=InnoDB
insert into feeding_entity_seq values ( 100 )
create table feeding_entity (ate_all bit, done bit, end_time time(6), end_weight integer, start_time time(6), start_weight integer, horse_id bigint, id bigint not null, primary key (id)) engine=InnoDB
create table feeding_entity_foods (feeding_entity_id bigint not null, foods_id bigint not null) engine=InnoDB
create table food_entity_seq (next_val bigint) engine=InnoDB
insert into food_entity_seq values ( 100 )
create table food_entity (weight integer, id bigint not null, name varchar(255), primary key (id)) engine=InnoDB
create table horse_entity_seq (next_val bigint) engine=InnoDB
insert into horse_entity_seq values ( 100 )
create table horse_entity (feedings_per_day integer, joined date, id bigint not null, owner_id bigint, stable_id bigint, uuid binary(16), breed varchar(255), name varchar(255), nick_name varchar(255), primary key (id)) engine=InnoDB
create table owner_entity_seq (next_val bigint) engine=InnoDB
insert into owner_entity_seq values ( 100 )
create table owner_entity (id bigint not null, address varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table stable_entity_seq (next_val bigint) engine=InnoDB
insert into stable_entity_seq values ( 100 )
create table stable_entity (id bigint not null, address varchar(255), name varchar(255), primary key (id)) engine=InnoDB
alter table feeding_entity add constraint FKlfkrkrm1np6yedmm1f8ksbrap foreign key (horse_id) references horse_entity (id)
alter table feeding_entity_foods add constraint FKcxjc1w9gps50hg122knxf637f foreign key (foods_id) references food_entity (id)
alter table feeding_entity_foods add constraint FKkx909el56dli89ggh6di4ndsp foreign key (feeding_entity_id) references feeding_entity (id)
alter table horse_entity add constraint FKg8y3j1cxreihuoeb30bhi24sp foreign key (owner_id) references owner_entity (id)
alter table horse_entity add constraint FKg2xac6tttccb7eakkcnp55u2c foreign key (stable_id) references stable_entity (id)
