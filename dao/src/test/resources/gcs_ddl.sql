CREATE TABLE gift_certificate
(
    id               serial primary key,
    name             varchar(200) not null UNIQUE,
    description      varchar(2000),
    price            decimal      not null,
    duration         smallint     not null,
    create_date      timestamp    not null,
    last_update_date timestamp    not null
);

ALTER TABLE gift_certificate
    ADD CONSTRAINT gift_certificate_name_uq UNIQUE (name);

CREATE TABLE tag
(
    id   serial primary key,
    name varchar(200) not null
);

ALTER TABLE tag
    ADD CONSTRAINT tag_name_uq UNIQUE (name);

CREATE TABLE gift_certificates_tags
(
    id                  bigserial primary key,
    gift_certificate_id integer not null,
    tag_id              integer not null
);

ALTER TABLE gift_certificates_tags
    ADD CONSTRAINT gift_certificate_id_fk FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE;
ALTER TABLE gift_certificates_tags
ADD CONSTRAINT tag_id_fk FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE;

ALTER TABLE gift_certificates_tags
    ADD UNIQUE (gift_certificate_id, tag_id);