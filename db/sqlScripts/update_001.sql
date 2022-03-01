CREATE TABLE users (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255),
        email VARCHAR(100) UNIQUE,
        password VARCHAR(100)
);

CREATE TABLE posts (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255),
	description TEXT,
	sold boolean,
	mark_id INTEGER REFERENCES cars_marks (Id),
	body_id INTEGER REFERENCES cars_bodies (Id),
	user_id INTEGER REFERENCES user_id (Id),
	created DATE default CURRENT_DATE
);


CREATE TABLE cars_marks (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) UNIQUE
);

CREATE TABLE cars_bodies (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) UNIQUE
);