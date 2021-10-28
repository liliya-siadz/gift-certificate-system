INSERT INTO tag (id, name)
values (1, 'Best friend'),
       (2, 'From Colleagues'),
       (3, 'New year 3000'),
       (4, 'The Power of idea');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date,last_update_date)
values
(1, 'Paul Sander', 'Star wars inspiration art therapy', 345.78, 15, '1999-08-29T06:12:15.156', '2021-08-29T06:12:15.156'),
(2, 'Sandy Clare', '10 lessons of crossfit', 499.99, 60, '2020-08-29T22:12:15.156', '2021-08-29T06:12:15.156'),
(3, 'Eric Z', 'Crazy cooking with japan chef', 800.78, 100, '2021-07-01T05:12:15.576', '2021-08-29T06:12:15.156'),
(4, 'Lola Richardson', 'Swimming with dolphins', 1700, 40, '2021-03-05T13:45:07.200', '2021-08-29T06:12:15.156'),
(5, 'Bob Di La Ve', 'Free E-books at Chare De Kav', 300.30, 250, '2021-08-29T06:12:15.156', '2021-08-29T06:12:15.156');

INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
values
(1, 2), (1, 3),
(2, 1), (2, 3),
(3, 1),
(4, 1), (4, 3),
(5, 4);
