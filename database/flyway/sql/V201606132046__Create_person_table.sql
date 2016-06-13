CREATE TABLE users
(
  id bigserial NOT NULL,
  username character varying,
  CONSTRAINT pk PRIMARY KEY (id)
);

insert into users(username) values ('john'), ('mary');
