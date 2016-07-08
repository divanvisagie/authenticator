CREATE TABLE users
(
  id bigserial NOT NULL,
  username character varying,
  email character varying,
  password_hash character varying,
  CONSTRAINT pk PRIMARY KEY (id),
  CONSTRAINT un UNIQUE(username)
);

insert into users(username,password_hash) values ('bob','$2a$10$uIXsjBLBvguLkg6SXGi/JOyHR92hkwUR43vwIcQdRO4ac.FwKbwgi'), ('mary','marys-hash');
