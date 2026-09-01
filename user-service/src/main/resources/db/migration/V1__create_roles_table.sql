-- Create roles table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) UNIQUE
);