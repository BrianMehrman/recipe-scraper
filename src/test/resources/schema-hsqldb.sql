
DROP TABLE recipes IF EXISTS;
CREATE TABLE recipes (
    id varchar(36) NOT NULL,
    name varchar(255) NOT NULL,
    duration int NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE ingredients IF EXISTS;
CREATE TABLE ingredients (
    id varchar(36) NOT NULL,
    name varchar(255) NOT NULL,
    measurement varchar(255) NOT NULL,
    amount decimal(24,8) NOT NULL,
    recipe_id varchar(36) NOT NULL,
    ingredient_type varchar(36) NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);