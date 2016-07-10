CREATE TABLE users
(
  id bigserial primary key,
  username text not null unique,
  password text null,
  email text not null unique
);



insert into users(username,email, password)
values (
    'bob',
    'bob@sg.com',
    '$2a$10$uIXsjBLBvguLkg6SXGi/JOyHR92hkwUR43vwIcQdRO4ac.FwKbwgi'
);

