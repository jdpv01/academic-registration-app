CREATE TABLE course (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    capacity INTEGER NOT NULL
);

CREATE TABLE subject (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    weekdays VARCHAR(255) NOT NULL,
    course_id UUID NOT NULL,
    FOREIGN KEY(course_id) REFERENCES "course"(id)
);

CREATE TABLE user_course (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    course_id UUID NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    FOREIGN KEY(user_id) REFERENCES "user"(id),
    FOREIGN KEY(course_id) REFERENCES "course"(id),
    UNIQUE (user_id, course_id)
);

INSERT INTO role (id, role) VALUES(1, 'ROLE_USER') ON CONFLICT (id) DO NOTHING;
INSERT INTO role (id, role) VALUES(2, 'ROLE_ADMIN') ON CONFLICT (id) DO NOTHING;