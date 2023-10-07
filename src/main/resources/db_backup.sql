--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4 (Debian 15.4-1.pgdg120+1)
-- Dumped by pg_dump version 15.2

-- Started on 2023-10-07 20:47:50

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

--
-- TOC entry 3375 (class 1262 OID 16384)
-- Name: test; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE test WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE test OWNER TO postgres;

\connect test

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

--
-- TOC entry 214 (class 1259 OID 16385)
-- Name: customersequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customersequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customersequence OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16386)
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
    id bigint DEFAULT nextval('public.customersequence'::regclass) NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16392)
-- Name: productsequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.productsequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.productsequence OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16393)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id bigint DEFAULT nextval('public.productsequence'::regclass) NOT NULL,
    name character varying(100) NOT NULL,
    price numeric(12,2) NOT NULL
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16401)
-- Name: purchacessequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.purchacessequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.purchacessequence OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16402)
-- Name: purchases; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchases (
    id bigint DEFAULT nextval('public.purchacessequence'::regclass) NOT NULL,
    customer_id bigint NOT NULL,
    product_id bigint NOT NULL,
    purchase_date date NOT NULL
);


ALTER TABLE public.purchases OWNER TO postgres;

--
-- TOC entry 3365 (class 0 OID 16386)
-- Dependencies: 215
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (id, first_name, last_name) FROM stdin;
1	Никита	Богданов
2	Агния	Макарова
3	Дмитрий	Прохоров
4	Мирослава	Черкасова
5	Варвара	Смирнова
6	Андрей	Борисов
7	Дмитрий	Богданов
8	Елизавета	Соловьева
9	Ярослав	Капустин
10	Артём	Беляев
\.


--
-- TOC entry 3367 (class 0 OID 16393)
-- Dependencies: 217
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id, name, price) FROM stdin;
1	Отмычка (3 ед.)	33565.89
2	Деревянный брус	13452.48
3	Солнечный камень	42838.32
4	Ритуальная свеча	21872.24
5	Световая шашка (1 шт.)	26822.25
6	Щит кристального стрелка	30461.28
7	Алмаз (драгоценный камень)	1773.97
8	Сапфир (драгоценный камень)	30560.27
9	Средняя походная сумка (10 ячеек)	32695.72
10	Ящик с инструментами	39088.71
11	Хлам (4 ед.)	36862.24
12	Зелёный изумруд (драгоценный камень)	40742.54
13	Мешочек с фосфором	49785.16
14	Мешок дерзкого повара	37589.66
15	Капюшон веселого лесника	19281.03
16	Малый провиант	437.04
17	Сумка странника (12 ячеек)	4451.71
18	Кусок белого мела	48296.11
19	Слиток металла	49374.48
20	Кремень	8180.77
21	Вяленое мясо	15522.88
22	Мешочек с порохом (малый)	13420.69
23	Темная бархатная мантия с капюшоном (+10 к харизме)	27475.97
24	Точильный камень	11859.12
25	Веревка (15 метров)	7281.06
26	Боеприпасы (обычные. 15 шт.)	9764.30
27	Свиток идентификации	2129.51
28	Ржавый клинок	807.13
29	Масляная лампа (пустая)	26536.31
30	Шёлковая материя	42409.56
31	Пояс звездного некроманта	5510.41
32	Хлам (8 ед.)	10692.75
33	Испорченная пища	49119.83
34	Хлам (5 ед.)	43579.52
35	Сигнальная шашка (3 шт.)	23778.63
36	Деревянный факел	42953.63
37	Сигнальная шашка (1 шт.)	19157.28
38	Простая свеча	32133.92
39	Амулет вечного обледенения	39110.65
40	Пояс с карманами (3 малых ячеек.)	37911.61
41	Свиток портала	10557.87
42	Защитный медальон	45384.11
43	Эликсир невидимости	23412.63
44	Молот воскресшего солнца	36756.04
\.


--
-- TOC entry 3369 (class 0 OID 16402)
-- Dependencies: 219
-- Data for Name: purchases; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchases (id, customer_id, product_id, purchase_date) FROM stdin;
1	1	23	2023-03-25
2	1	1	2023-03-25
3	1	1	2023-03-25
4	1	1	2023-03-25
5	1	1	2023-03-25
6	1	28	2023-03-25
7	1	29	2023-03-25
8	1	43	2023-03-25
9	2	18	2023-03-26
10	2	1	2023-03-26
11	2	1	2023-03-26
12	2	1	2023-03-26
13	2	20	2023-03-26
14	2	36	2023-03-27
15	2	21	2023-03-27
16	2	21	2023-03-27
17	2	21	2023-03-27
18	2	21	2023-03-27
19	3	13	2023-03-25
20	3	13	2023-03-25
21	3	13	2023-03-25
22	3	13	2023-03-25
23	3	13	2023-03-25
24	3	13	2023-03-25
25	4	16	2023-04-25
26	5	16	2023-03-28
\.


--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 214
-- Name: customersequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customersequence', 10, true);


--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 216
-- Name: productsequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.productsequence', 44, true);


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 218
-- Name: purchacessequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchacessequence', 26, true);


--
-- TOC entry 3215 (class 2606 OID 16398)
-- Name: products pk_products; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT pk_products PRIMARY KEY (id);


--
-- TOC entry 3219 (class 2606 OID 16407)
-- Name: purchases pk_purchases; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT pk_purchases PRIMARY KEY (id);


--
-- TOC entry 3213 (class 2606 OID 16391)
-- Name: customers pk_tasks; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT pk_tasks PRIMARY KEY (id);


--
-- TOC entry 3217 (class 2606 OID 16400)
-- Name: products unique_name; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT unique_name UNIQUE (name);


--
-- TOC entry 3220 (class 2606 OID 16408)
-- Name: purchases fk_customer_purchase; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT fk_customer_purchase FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- TOC entry 3221 (class 2606 OID 16413)
-- Name: purchases fk_product_purchase; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT fk_product_purchase FOREIGN KEY (product_id) REFERENCES public.products(id);


-- Completed on 2023-10-07 20:47:50

--
-- PostgreSQL database dump complete
--

