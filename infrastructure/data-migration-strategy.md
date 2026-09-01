# Migration Script: MySQL to PostgreSQL Data Porter
# Exports data from MySQL and prepares PostgreSQL COPY / INSERT commands with proper type mapping

# 1. Type Mapping Reference:
# MySQL INT AUTO_INCREMENT -> PostgreSQL SERIAL / IDENTITY
# MySQL DOUBLE -> PostgreSQL DOUBLE PRECISION
# MySQL LONGTEXT -> PostgreSQL TEXT
# MySQL TIMESTAMP/DATETIME -> PostgreSQL TIMESTAMP WITHOUT TIME ZONE

# 2. Migration Execution Order:
# Step A: user-service (roles -> users -> user_roles)
# Step B: restaurant-service (restaurants -> restaurant_menus -> restaurant_entity_menu_types)
# Step C: payment-service (payments)
# Step D: order-service (orders)

# 3. Validation Check:
# SELECT count(*) FROM roles;
# SELECT count(*) FROM users;
# SELECT count(*) FROM user_roles;
# SELECT count(*) FROM restaurants;
# SELECT count(*) FROM restaurant_menus;
# SELECT count(*) FROM payments;
# SELECT count(*) FROM orders;
