-- drop the database if exists
drop database if exists rsvp;

-- create the database
-- create database if not exists rsvp; -- already have drop database if exists
create database rsvp;

-- select the database
use rsvp;

-- create one or more tables
select "Creating RSVP table..." as msg;
create table rsvp_table (
    email varchar(256) not null, -- this is the PK
    phone varchar(256),
    confirmation_date date not null,
    comments text,

    constraint email_pk primary key(email)
    -- ,
    -- CONSTRAINT rsvp_phone_uq UNIQUE (phone),
    -- CONSTRAINT rsvp_cfm_date_chk CHECK (cfm_date > '2025-01-01')
);


-- grant fred access to the database
select "Grant privileges to fred" as msg;
grant all privileges on rsvp.* to 'fred'@'%';
flush privileges;