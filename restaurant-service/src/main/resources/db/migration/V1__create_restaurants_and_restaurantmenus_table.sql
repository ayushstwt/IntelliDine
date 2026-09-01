CREATE TABLE restaurants (
    restaurant_id SERIAL PRIMARY KEY,
    restaurant_name VARCHAR(255),
    rating VARCHAR(255),
    restaurant_image_url VARCHAR(255),
    location VARCHAR(255)
);

CREATE TABLE restaurant_entity_menu_types (
    restaurant_id INT,
    menu_type VARCHAR(255),
    PRIMARY KEY (restaurant_id, menu_type),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);

CREATE TABLE restaurant_menus (
    menu_id SERIAL PRIMARY KEY,
    item_name VARCHAR(255),
    description VARCHAR(1000),
    menu_image_url VARCHAR(255),
    menu_type VARCHAR(255),
    price DOUBLE PRECISION,
    restaurant_id INT,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);