create table cars
(
    id    serial primary key,
    brand varchar(20)             not null,
    model varchar(40)             not null,
    price int check ( price > 0 ) not null
);

create table cars
(
    id             serial primary key,
    name           varchar(20)              not null,
    age            int check ( age >= 18 )  not null,
    driver_license boolean default true     not null,
    car_id         int references cars (id) not null
);



