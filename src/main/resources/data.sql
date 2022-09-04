INSERT INTO customers (id, first_name, last_name, version)
VALUES (1, 'Isaiah', 'Benson', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (1, 1, '0491570159', 1, 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (2, 1, '0491570152', 0, 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (3, 1, '0491570153', 1, 1);


INSERT INTO customers (id, first_name, last_name, version)
VALUES (2, 'Tommy', 'Guzman', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (4, 2, '0491570159', 1, 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (5, 2, '0491570152', 0, 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (6, 2, '0491570153', 1, 1);



INSERT INTO customers (id, first_name, last_name, version)
VALUES (3, 'Kit', 'Poole', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (7, 3, '0491570159', 1, 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (8, 3, '0491570152', 0, 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (9, 3, '0491570153', 1, 1);


INSERT INTO customers (id, first_name, last_name, version)
VALUES (4, 'Obadiah', 'Reyes', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (10, 4, '0491570113', 0, 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (11, 4, '0491570114', 0, 1);


INSERT INTO customers (id, first_name, last_name, version)
VALUES (5, 'Orson', 'Holmes', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (12, 5, '0491570115', 0, 1);


INSERT INTO customers (id, first_name, last_name, version)
VALUES (6, 'Dennis', 'Bacchus', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (13, 6, '0491570109', 0, 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (14, 6, '0491570110', 0, 1);


INSERT INTO customers (id, first_name, last_name, version)
VALUES (7, 'Miles', 'Heptinstall', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (15, 7, '0491570106', 0, 1);


INSERT INTO customers (id, first_name, last_name, version)
VALUES (8, 'Samuel', 'White', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (16, 8, '0491570107', 0, 1);

INSERT INTO customers (id, first_name, last_name, version)
VALUES (9, 'Barrett', 'Tucker', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (17, 9, '0491570103', 1, 1);


INSERT INTO customers (id, first_name, last_name, version)
VALUES (10, 'Osmond', 'Norris', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (18, 10, '0491570104', 0, 1);

INSERT INTO customers (id, first_name, last_name, version)
VALUES (11, 'Trix', 'Lipsey', 1);
INSERT INTO phone_details (id, customer_id, phone_number, status, version)
VALUES (19, 11, '0491570105', 0, 1);


insert into customer_phone_detail_ids(customer_id, phone_detail_id)
select customer_id, id
from phone_details;





