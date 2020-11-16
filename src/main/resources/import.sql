--CREATE DATABASE test2;
--CREATE TABLE IF not exists person(
--     id int,
--     firstname varchar(36),
--     lastname varchar(36)
--);

INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'George', 'Bluth');
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Michael', 'Bluth');
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Gob', 'Bluth');
INSERT INTO arperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Buster', 'Bluth');

INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'George', 'Bluth');
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Michael', 'Bluth');
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Gob', 'Bluth');
INSERT INTO rperson (id, firstname, lastname) VALUES (nextVal('hibernate_sequence'), 'Buster', 'Bluth');