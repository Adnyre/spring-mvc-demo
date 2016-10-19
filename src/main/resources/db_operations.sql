DROP TABLE phone_numbers;

DROP TABLE contacts;

CREATE TABLE contacts (
  id         SERIAL PRIMARY KEY,
  first_name CHARACTER VARYING(20),
  last_name  CHARACTER VARYING(20)
);

CREATE TABLE phone_numbers (
  id         SERIAL PRIMARY KEY,
  contact_id INTEGER REFERENCES contacts(id),
  number CHARACTER VARYING(20),
  type  CHARACTER VARYING(20),
  CONSTRAINT UQ_NUMBER_TYPE UNIQUE (number, type)
);


-- INSERT INTO contacts (first_name, last_name) VALUES ('Andrii', 'Novikov'), ('Taras', 'Martsyniak');
--
-- INSERT INTO phone_numbers (contact_id, number, type) VALUES (1, '097-000-00-00', 'mobile'), (1, '024-000-00-00', 'home'),
--   (2, '097-111-11-11', 'mobile'), (2, '024-111-11-11', 'home');