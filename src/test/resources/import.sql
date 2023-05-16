INSERT INTO role (id, role) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, role) VALUES (2, 'ROLE_ADMIN');

INSERT INTO "user" (id, firstName, lastName, email, password) VALUES ('a364e22d-24a3-4954-b37e-b5719118c9ba', 'user', 'user', 'juandpv@hotmail.com', '$2a$12$MCWnurN2eDZI5Mo3TjjUwOwPtA4F4942ds0.1mjfqXAab7rWm0wiK');
INSERT INTO "user" (id, firstName, lastName, email, password) VALUES ('f56259d3-ab63-42a5-bcbe-6d8a01205944', 'user2', 'user2', 'juandpv2@hotmail.com', '$2a$12$MCWnurN2eDZI5Mo3TjjUwOwPtA4F4942ds0.1mjfqXAab7rWm0wiK');
INSERT INTO "user" (id, firstName, lastName, email, password) VALUES ('ebfab851-419d-4707-b0e3-d3ff4d4596f5', 'user3', 'user3', 'juandpv3@hotmail.com', '$2a$12$MCWnurN2eDZI5Mo3TjjUwOwPtA4F4942ds0.1mjfqXAab7rWm0wiK');

INSERT INTO course (id, name, startDate, endDate, capacity) VALUES ('16467b1a-a4ec-4570-aeb1-8270d28386e5', 'Introduccion a las TIC', '2023-01-16', '2023-06-16', 2);
INSERT INTO subject (id, name, startTime, endTime, weekdays, course_id) VALUES ('a804b4bf-0ca5-4a34-a81c-24da34bedde2', 'Scratch', '14:00:00', '16:00:00', 'MA-JU', '16467b1a-a4ec-4570-aeb1-8270d28386e5');
INSERT INTO subject (id, name, startTime, endTime, weekdays, course_id) VALUES ('1bba5c90-080f-4c41-8ba8-484cbcaf7f4a', 'AppInventor', '14:00:00', '16:00:00', 'MA-JU', '16467b1a-a4ec-4570-aeb1-8270d28386e5');

INSERT INTO course (id, name, startDate, endDate, capacity) VALUES ('3f6d25ad-3a46-4ce5-beea-1a1b77d359b3', 'Matematicas', '2023-01-16', '2023-05-16', 1);
INSERT INTO subject (id, name, startTime, endTime, weekdays, course_id) VALUES ('f6a161d4-b74b-4a5d-b3ae-36387dd9bc55', 'Calculo de varias variables', '14:00:00', '16:00:00', 'LU-MI-VI', '3f6d25ad-3a46-4ce5-beea-1a1b77d359b3');
INSERT INTO subject (id, name, startTime, endTime, weekdays, course_id) VALUES ('e8959c3c-a80e-4283-ba3e-54156436990f', 'Algebra lineal', '14:00:00', '16:00:00', 'LU-MI-VI', '3f6d25ad-3a46-4ce5-beea-1a1b77d359b3');

INSERT INTO user_course (id, user_id, course_id, registrationDate) VALUES ('dce9ba92-8e81-471e-8cbb-69bbd44eb59f', 'a364e22d-24a3-4954-b37e-b5719118c9ba', '3f6d25ad-3a46-4ce5-beea-1a1b77d359b3', '2023-03-22T12:34:56');