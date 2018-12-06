CREATE SEQUENCE public.order_id_seq;
CREATE TABLE public."order"
(
    id integer DEFAULT nextval('order_id_seq'::regclass) PRIMARY KEY NOT NULL,
    order_date timestamp,
    payment_id integer,
    status_id integer,
    store_id integer,
    total double precision,
    customer_id integer NOT NULL,
    CONSTRAINT fk_payment_id FOREIGN KEY (payment_id) REFERENCES public."payment" (id)
);
INSERT INTO public."order" (id, order_date, payment_id, status_id, store_id, total, customer_id) VALUES (1, '2018-11-26 14:33:58.648000', 1, 4, 1, 26, 1);