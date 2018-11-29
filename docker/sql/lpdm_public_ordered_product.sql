CREATE TABLE public.ordered_product
(
    product_id integer NOT NULL,
    quantity integer,
    order_id integer NOT NULL,
    CONSTRAINT ordered_product_pkey PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk684l0g7wdvypylqnc39jfjkld FOREIGN KEY (order_id) REFERENCES public."order" (id)
);
INSERT INTO public.ordered_product (product_id, quantity, order_id) VALUES (2, 8, 1);
INSERT INTO public.ordered_product (product_id, quantity, order_id) VALUES (1, 7, 1);
INSERT INTO public.ordered_product (product_id, quantity, order_id) VALUES (3, 5, 1);