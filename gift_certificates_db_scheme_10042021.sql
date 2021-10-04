--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: gift_certificate; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gift_certificate (
    id integer NOT NULL,
    name character varying(200) NOT NULL,
    description character varying(3000) NOT NULL,
    price numeric NOT NULL,
    duration smallint NOT NULL,
    create_date timestamp with time zone NOT NULL,
    last_update_date timestamp with time zone NOT NULL
);


ALTER TABLE public.gift_certificate OWNER TO postgres;

--
-- Name: gift_certificate_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.gift_certificate_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gift_certificate_id_seq OWNER TO postgres;

--
-- Name: gift_certificate_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gift_certificate_id_seq OWNED BY public.gift_certificate.id;


--
-- Name: gift_certificates_tags; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gift_certificates_tags (
    id integer NOT NULL,
    tag_id integer NOT NULL,
    gift_certificate_id integer NOT NULL
);


ALTER TABLE public.gift_certificates_tags OWNER TO postgres;

--
-- Name: gift_certificates_tags_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.gift_certificates_tags_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gift_certificates_tags_id_seq OWNER TO postgres;

--
-- Name: gift_certificates_tags_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.gift_certificates_tags_id_seq OWNED BY public.gift_certificates_tags.id;


--
-- Name: tag; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tag (
    id integer NOT NULL,
    name character varying(40) NOT NULL
);


ALTER TABLE public.tag OWNER TO postgres;

--
-- Name: tag_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tag_id_seq OWNER TO postgres;

--
-- Name: tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tag_id_seq OWNED BY public.tag.id;


--
-- Name: gift_certificate id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gift_certificate ALTER COLUMN id SET DEFAULT nextval('public.gift_certificate_id_seq'::regclass);


--
-- Name: gift_certificates_tags id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gift_certificates_tags ALTER COLUMN id SET DEFAULT nextval('public.gift_certificates_tags_id_seq'::regclass);


--
-- Name: tag id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag ALTER COLUMN id SET DEFAULT nextval('public.tag_id_seq'::regclass);


--
-- Data for Name: gift_certificate; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gift_certificate (id, name, description, price, duration, create_date, last_update_date) FROM stdin;
\.


--
-- Data for Name: gift_certificates_tags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gift_certificates_tags (id, tag_id, gift_certificate_id) FROM stdin;
\.


--
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tag (id, name) FROM stdin;
\.


--
-- Name: gift_certificate_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gift_certificate_id_seq', 1, false);


--
-- Name: gift_certificates_tags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.gift_certificates_tags_id_seq', 1, false);


--
-- Name: tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tag_id_seq', 1, false);


--
-- Name: gift_certificate gift_certificate_id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gift_certificate
    ADD CONSTRAINT gift_certificate_id_pk PRIMARY KEY (id);


--
-- Name: gift_certificates_tags gift_certificate_tag_uq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gift_certificates_tags
    ADD CONSTRAINT gift_certificate_tag_uq UNIQUE (gift_certificate_id, tag_id);


--
-- Name: gift_certificates_tags gift_certificates_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gift_certificates_tags
    ADD CONSTRAINT gift_certificates_tags_pkey PRIMARY KEY (id);


--
-- Name: tag id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT id_pk PRIMARY KEY (id);


--
-- Name: tag name_uq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT name_uq UNIQUE (name);


--
-- Name: gift_certificates_tags gift_certificates_tags_gift_certificate_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gift_certificates_tags
    ADD CONSTRAINT gift_certificates_tags_gift_certificate_id_fkey FOREIGN KEY (gift_certificate_id) REFERENCES public.gift_certificate(id);


--
-- Name: gift_certificates_tags gift_certificates_tags_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gift_certificates_tags
    ADD CONSTRAINT gift_certificates_tags_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tag(id);


--
-- PostgreSQL database dump complete
--

