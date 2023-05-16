CREATE TABLE "user" (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE role (
    id INTEGER PRIMARY KEY,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE user_role (
    user_id UUID NOT NULL,
    role_id INTEGER NOT NULL,
    FOREIGN KEY(user_id) REFERENCES "user"(id),
    FOREIGN KEY(role_id) REFERENCES "role"(id),
    UNIQUE (user_id, role_id)
);