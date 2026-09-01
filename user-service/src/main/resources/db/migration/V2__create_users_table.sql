-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

-- Create user_roles table
CREATE TABLE user_roles (
    user_id INT,
    role_id INT,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(role_id) REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);