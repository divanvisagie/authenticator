CREATE TABLE users
(
  id bigserial NOT NULL,
  username character varying,
  password_hash character varying,
  CONSTRAINT pk PRIMARY KEY (id)
);

insert into users(username,password_hash) values ('john','johns-hash'), ('mary','marys-hash');
