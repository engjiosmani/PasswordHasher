CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(100) UNIQUE NOT NULL,
                       password_hash TEXT NOT NULL,
                       salt TEXT NOT NULL,
                       firstName VARCHAR(100) NOT NULL,
                       lastName VARCHAR(100) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL
);