CREATE SCHEMA IF NOT EXISTS autosell;

CREATE TABLE autosell.t_car(
    id serial PRIMARY KEY,
    c_name varchar(50) NOT NULL CHECK (LENGTH(TRIM(c_name)) >= 2),
    c_description varchar(1000),
    c_price int NOT NULL CHECK (c_price >= 0)
);