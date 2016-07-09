CREATE TABLE users
(
  id bigserial NOT NULL,
  username character varying,
  password_hash character varying,
  email character varying,
  CONSTRAINT pk PRIMARY KEY (id),
  CONSTRAINT un UNIQUE(username),
  CONSTRAINT em UNIQUE(email)
);

insert into users(username,email, password_hash)
values (
    'bob',
    'bob@sg.com',
    '$2a$10$uIXsjBLBvguLkg6SXGi/JOyHR92hkwUR43vwIcQdRO4ac.FwKbwgi'
);


