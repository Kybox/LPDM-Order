create table coupon
(
  id          serial                not null
    constraint coupon_pkey
      primary key,
  active      boolean default false not null,
  amount      double precision      not null,
  code        varchar               not null,
  description varchar(255)
);

alter table coupon
  owner to lpdm;

INSERT INTO public.coupon (id, active, amount, code, description) VALUES (3, true, 10, '$2a$10$sitgyUaqpfeXfVa.nGv1K.Tg8TLPxlIRlYkbIx/f2fQXgIYl8LtsO', '10€ avec le code promo10');
create table delivery
(
  id     serial           not null
    constraint delivery_pkey
      primary key,
  method varchar(30)      not null,
  amount double precision not null
);

alter table delivery
  owner to lpdm;

INSERT INTO public.delivery (id, method, amount) VALUES (1, 'not define', 0);
INSERT INTO public.delivery (id, method, amount) VALUES (2, 'Livraison à domicile', 4.9);
INSERT INTO public.delivery (id, method, amount) VALUES (3, 'Livraison en magasin', 0);
create table invoice
(
  id        serial      not null
    constraint invoice_pkey
      primary key,
  order_id  integer     not null
    constraint invoice_order_fk
      references "order",
  reference varchar(50) not null
);

alter table invoice
  owner to lpdm;

INSERT INTO public.invoice (id, order_id, reference) VALUES (7, 2, '20190113165641');
INSERT INTO public.invoice (id, order_id, reference) VALUES (8, 1, '20181124113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (9, 3, '20181126043937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (10, 4, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (11, 6, '20190108015120');
INSERT INTO public.invoice (id, order_id, reference) VALUES (12, 7, '20190108015448');
INSERT INTO public.invoice (id, order_id, reference) VALUES (13, 11, '20190108084443');
INSERT INTO public.invoice (id, order_id, reference) VALUES (14, 9, '20190108020325');
INSERT INTO public.invoice (id, order_id, reference) VALUES (15, 125, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (16, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (17, 129, '20190215212718');
INSERT INTO public.invoice (id, order_id, reference) VALUES (18, 1, '20181124113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (19, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (20, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (21, 125, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (22, 120, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (23, 129, '20190215212718');
INSERT INTO public.invoice (id, order_id, reference) VALUES (24, 123, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (25, 120, '20181130113937');
create table "order"
(
  id          serial  not null
    constraint order_pkey
      primary key,
  customer_id integer not null,
  order_date  timestamp,
  status_id   integer,
  store_id    integer,
  total       double precision,
  payment_id  integer
    constraint fkal550jx92fbea8sry5q4siyn1
      references payment,
  coupon      integer
    constraint fkiv08ux3yuf76s37722or3c6j7
      references coupon,
  delivery    integer default 1
    constraint fkt5bqfi3ksom90m3utcjsndqcs
      references delivery,
  tax_amount  double precision
);

alter table "order"
  owner to lpdm;

INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (12, 14, '2019-01-09 15:01:21.589000', 2, 0, 148, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (77, 110, '2019-02-04 00:57:13.626000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (79, 116, '2019-02-04 11:57:35.216000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (85, 118, '2019-02-04 15:35:34.901000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (86, 118, '2019-02-04 15:36:21.033000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (88, 118, '2019-02-05 06:53:55.580000', 3, 0, 69.28, 2, null, null, 6.28);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (13, 14, '2019-01-09 15:05:02.664000', 2, 0, 232, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (14, 14, '2019-01-09 15:16:34.161000', 2, 0, 47, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (15, 13, '2019-01-19 16:10:29.051000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (8, 96, '2019-01-08 02:02:56.277000', 2, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (9, 96, '2019-01-08 02:03:25.873000', 2, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (10, 7, '2019-01-08 08:44:06.683000', 2, 0, 116, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (11, 7, '2019-01-08 08:44:43.678000', 2, 0, 116, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (36, 101, '2019-01-29 16:41:17.343000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (37, 101, '2019-01-29 16:42:01.247000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (38, 101, '2019-01-29 16:43:14.688000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (39, 101, '2019-01-29 16:43:46.368000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (32, 101, '2019-01-27 20:23:14.959000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (33, 101, '2019-01-29 16:36:21.773000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (34, 101, '2019-01-29 16:39:30.339000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (35, 101, '2019-01-29 16:41:01.911000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (44, 101, '2019-01-29 17:20:30.316000', 3, 0, 25, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (45, 101, '2019-01-29 17:29:20.721000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (46, 101, '2019-01-29 16:35:01.530000', 3, 0, 42, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (40, 101, '2019-01-29 16:44:43.209000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (41, 101, '2019-01-29 16:59:14.056000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (42, 101, '2019-01-29 17:09:32.787000', 3, 0, 25, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (43, 101, '2019-01-29 17:14:07.400000', 3, 0, 13, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (20, 3, '2019-01-22 09:09:54.931000', 3, 0, 6.79, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (21, 102, '2019-01-24 11:48:50.606000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (22, 101, '2019-01-27 18:58:51.700000', 3, 0, 24, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (23, 101, '2019-01-27 19:32:51.007000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (16, 13, '2019-01-19 15:48:15.783000', 3, 0, 71, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (17, 86, '2019-01-20 22:53:39.419000', 3, 0, 46, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (18, 86, '2019-01-20 22:59:11.162000', 3, 0, 34, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (19, 86, '2019-01-20 22:05:24.440000', 3, 0, 54, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (28, 101, '2019-01-27 20:06:51.723000', 3, 0, 58, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (29, 101, '2019-01-27 20:12:38.001000', 3, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (30, 101, '2019-01-27 20:14:21.054000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (31, 101, '2019-01-27 20:21:10.823000', 3, 0, 54, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (26, 101, '2019-01-27 19:50:10.095000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (27, 101, '2019-01-27 19:55:41.334000', 3, 0, 70, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (4, 1, '2018-11-30 11:39:37.330300', 4, 2, 104, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (5, 83, '2019-01-07 17:44:13.436000', null, 0, 71, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (6, 83, '2019-01-08 01:51:20.788000', 2, 0, 59, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (7, 83, '2019-01-08 01:54:48.572000', 2, 0, 66, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (1, 1, '2018-11-24 11:39:37.330000', 4, 1, 27.43, 1, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (2, 2, '2018-11-30 08:30:37.330000', 4, 2, 52, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (3, 3, '2018-11-26 04:39:37.330000', 4, 1, 104, 1, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (120, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (121, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (99, 122, '2019-02-11 14:13:18.634000', 6, 0, 25.156000000000002, null, null, 2, 1.056);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (124, 122, '2018-11-30 11:39:37.330300', 5, 2, 160, 1, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (97, 122, '2019-02-11 09:39:09.999000', 6, 0, 348, null, null, null, 48);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (95, 122, '2019-02-07 19:08:24.543000', 6, 0, 999, null, null, null, 99);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (126, 122, '2019-02-13 23:02:30.159000', 6, 0, 19.99, 2, null, 2, 0.79);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (101, 122, '2019-02-12 18:13:46.850000', 6, 0, 268.22799999999995, 2, null, 2, 13.728000000000007);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (128, 122, '2019-02-15 13:38:41.242000', 2, 0, 33.9, 2, null, 2, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (130, 124, '2019-02-19 17:15:06.164000', 3, 0, 4.43, 2, null, null, 0.23);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (103, 122, '2019-02-13 17:00:59.308000', 6, 0, 551, null, null, null, 76);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (104, 122, '2019-02-13 17:05:46.686000', 6, 0, 62.9, null, null, 2, 8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (52, 101, '2019-01-30 14:33:33.175000', 3, 0, 24, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (53, 101, '2019-01-30 13:42:05.511000', 3, 0, 29, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (54, 101, '2019-01-30 13:42:40.693000', 3, 0, 56, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (55, 101, '2019-01-30 21:00:28.726000', 3, 0, 50, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (49, 105, '2019-01-29 23:31:47.843000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (50, 105, '2019-01-29 23:54:58.051000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (51, 105, '2019-01-30 11:16:50.742000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (60, 110, '2019-02-03 15:52:16.316000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (61, 110, '2019-02-03 15:52:55.671000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (62, 110, '2019-02-03 15:52:59.308000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (63, 110, '2019-02-03 16:05:17.090000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (56, 105, '2019-01-30 22:14:18.926000', 3, 0, 80, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (57, 105, '2019-02-01 12:32:58.268000', 3, 0, 22, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (58, 110, '2019-02-03 15:46:11.436000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (59, 110, '2019-02-03 15:50:09.886000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (68, 110, '2019-02-03 16:23:14.859000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (69, 110, '2019-02-03 16:43:56.675000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (70, 110, '2019-02-03 16:47:15.798000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (71, 110, '2019-02-03 16:50:34.740000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (64, 110, '2019-02-03 16:10:10.809000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (65, 110, '2019-02-03 16:12:36.581000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (66, 110, '2019-02-03 16:14:19.127000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (67, 110, '2019-02-03 16:17:45.297000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (76, 110, '2019-02-03 17:12:26.030000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (72, 110, '2019-02-03 16:51:05.398000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (73, 110, '2019-02-03 16:52:19.905000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (74, 110, '2019-02-03 16:52:39.842000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (75, 110, '2019-02-03 16:56:59.377000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (24, 101, '2019-01-27 19:45:00.321000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (25, 101, '2019-01-27 19:48:02.501000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (78, 110, '2019-02-04 08:22:03.505000', 3, 0, 62.8, 2, null, null, 7.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (80, 118, '2019-02-04 16:14:46.533000', 3, 0, 91.8, 2, null, null, 11.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (81, 118, '2019-02-04 16:28:14.688000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (82, 118, '2019-02-04 16:30:57.957000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (83, 118, '2019-02-04 16:33:15.326000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (84, 118, '2019-02-04 16:34:10.675000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (87, 118, '2019-02-04 17:15:15.140000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (89, 118, '2019-02-06 13:13:24.972000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (100, 118, '2019-02-11 17:46:59.822000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (98, 122, '2019-02-11 10:24:14.500000', 6, 0, 12.238, null, null, null, 0.638);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (96, 122, '2019-02-07 19:14:05.682000', 6, 0, 133.2, null, null, null, 13.2);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (94, 122, '2019-02-07 19:03:14.305000', 6, 0, 199.8, null, null, null, 19.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (93, 122, '2019-02-07 18:54:18.237000', 6, 0, 865.8, null, null, null, 85.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (92, 122, '2019-02-07 18:42:31.934000', 6, 0, 174, null, null, null, 24);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (102, 122, '2019-02-13 15:44:38.631000', 6, 0, 40.512, null, null, null, 2.112);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (108, 1, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (115, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (119, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (123, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (105, 122, '2019-02-13 17:15:41.337000', 6, 0, 236.9, 2, null, 2, 32);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (125, 122, '2018-11-30 11:39:37.330300', 2, 2, 9.96, 2, null, 2, 0.26);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (127, 122, '2019-02-15 12:08:53.942000', 6, 0, 33.3, null, null, null, 3.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (129, 122, '2019-02-15 21:27:18.240000', 2, 0, 9.01, 2, null, 2, 0.21);
create table ordered_product
(
  id         integer                      not null
    constraint ordered_product_pkey
      primary key,
  price      double precision,
  product_id integer,
  quantity   integer,
  order_id   integer
    constraint fk684l0g7wdvypylqnc39jfjkld
      references "order",
  tax        double precision default 5.5 not null
);

alter table ordered_product
  owner to lpdm;

INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (1, 14, 1, 1, 1, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (2, 12, 2, 1, 1, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (3, 14, 1, 2, 2, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (4, 12, 2, 2, 2, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (5, 14, 1, 4, 3, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (6, 12, 2, 4, 3, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (7, 14, 1, 4, 4, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (8, 12, 2, 4, 4, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (9, 29, 4, 1, 5, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (10, 14, 7, 3, 5, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (11, 14, 20, 1, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (12, 21, 8, 1, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (13, 12, 9, 2, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (14, 27, 1, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (15, 14, 7, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (16, 25, 11, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (17, 14, 7, 1, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (18, 21, 8, 2, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (19, 12, 9, 1, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (20, 14, 7, 1, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (21, 21, 8, 2, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (22, 12, 9, 1, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (23, 29, 4, 2, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (24, 21, 8, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (25, 25, 11, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (26, 12, 9, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (27, 29, 4, 2, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (28, 21, 8, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (29, 25, 11, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (30, 12, 9, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (31, 20, 5, 2, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (32, 30, 6, 2, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (33, 21, 8, 1, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (34, 20, 5, 2, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (35, 30, 6, 3, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (36, 21, 8, 1, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (37, 27, 1, 2, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (38, 27, 1, 1, 14, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (39, 20, 5, 1, 14, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (40, 19, 14, 1, 15, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (41, 22, 2, 1, 15, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (42, 22, 2, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (43, 29, 3, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (44, 20, 5, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (45, 21, 8, 1, 17, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (46, 25, 13, 1, 17, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (47, 21, 8, 1, 18, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (48, 13, 12, 1, 18, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (49, 12, 9, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (50, 29, 10, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (51, 13, 12, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (52, 0.8, 2, 1, 20, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (53, 5.99, 5, 1, 20, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (54, 12, 9, 1, 21, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (55, 12, 9, 2, 22, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (56, 12, 9, 1, 23, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (57, 12, 9, 1, 24, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (58, 12, 9, 1, 25, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (59, 29, 10, 1, 25, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (60, 29, 10, 1, 26, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (61, 29, 10, 2, 27, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (62, 12, 9, 1, 27, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (63, 29, 10, 2, 28, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (64, 29, 10, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (65, 25, 13, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (66, 14, 18, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (67, 29, 10, 1, 30, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (68, 12, 9, 1, 30, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (69, 12, 9, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (70, 13, 12, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (71, 29, 19, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (72, 12, 9, 1, 32, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (73, 29, 10, 1, 32, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (74, 12, 9, 1, 33, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (75, 29, 10, 1, 33, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (76, 12, 9, 1, 34, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (77, 12, 9, 1, 35, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (78, 12, 9, 1, 36, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (79, 12, 9, 1, 37, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (80, 29, 10, 1, 38, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (81, 29, 10, 1, 39, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (82, 29, 10, 1, 40, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (83, 12, 9, 1, 41, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (84, 29, 10, 1, 41, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (85, 25, 11, 1, 42, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (86, 13, 12, 1, 43, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (87, 13, 12, 1, 44, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (88, 12, 9, 1, 44, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (89, 12, 9, 1, 45, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (90, 13, 12, 1, 46, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (91, 29, 10, 1, 46, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (92, 22, 2, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (93, 20, 5, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (94, 27, 1, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (95, 22, 2, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (96, 20, 5, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (97, 27, 1, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (98, 22, 2, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (99, 20, 5, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (100, 27, 1, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (101, 24, 22, 1, 52, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (102, 29, 3, 1, 53, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (103, 29, 3, 1, 54, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (104, 27, 1, 1, 54, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (105, 20, 24, 1, 55, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (106, 30, 6, 1, 55, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (107, 29, 3, 2, 56, 14);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (108, 22, 2, 1, 56, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (109, 22, 2, 1, 57, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (110, 25, 11, 1, 58, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (111, 30, 30, 1, 58, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (112, 25, 42, 1, 59, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (113, 25, 42, 1, 60, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (114, 25, 42, 1, 61, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (115, 25, 42, 1, 62, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (116, 25, 42, 1, 63, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (117, 25, 42, 1, 64, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (118, 25, 42, 1, 65, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (119, 25, 11, 1, 66, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (120, 30, 30, 1, 66, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (121, 25, 11, 1, 67, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (122, 30, 30, 1, 67, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (123, 13, 34, 2, 67, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (124, 25, 11, 1, 68, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (125, 30, 30, 1, 68, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (126, 13, 34, 2, 68, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (127, 25, 11, 1, 69, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (128, 30, 30, 1, 69, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (129, 13, 34, 2, 69, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (130, 25, 11, 1, 70, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (131, 30, 30, 1, 70, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (132, 13, 34, 2, 70, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (133, 25, 11, 1, 71, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (134, 30, 30, 1, 71, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (135, 13, 34, 2, 71, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (136, 25, 11, 1, 72, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (137, 30, 30, 1, 72, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (138, 13, 34, 2, 72, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (139, 25, 11, 1, 73, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (140, 30, 30, 1, 73, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (141, 13, 34, 2, 73, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (142, 25, 11, 1, 74, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (143, 30, 30, 1, 74, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (144, 13, 34, 2, 74, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (145, 25, 11, 1, 75, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (146, 30, 30, 1, 75, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (147, 13, 34, 2, 75, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (148, 25, 11, 1, 76, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (149, 30, 30, 1, 76, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (150, 25, 11, 1, 77, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (151, 30, 30, 1, 77, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (152, 25, 42, 1, 78, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (153, 30, 30, 1, 78, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (154, 25, 11, 1, 79, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (155, 30, 30, 1, 79, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (156, 25, 11, 1, 80, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (157, 30, 30, 1, 80, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (158, 25, 42, 1, 80, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (159, 25, 11, 1, 81, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (160, 30, 30, 1, 81, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (161, 25, 11, 1, 82, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (162, 30, 30, 1, 82, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (163, 25, 11, 1, 83, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (164, 30, 30, 1, 83, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (165, 25, 11, 1, 84, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (166, 30, 30, 1, 84, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (167, 25, 11, 1, 85, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (168, 25, 11, 1, 86, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (169, 25, 11, 1, 87, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (170, 30, 30, 1, 88, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (171, 13, 12, 1, 88, 6);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (172, 20, 24, 1, 88, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (173, 25, 11, 1, 89, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (174, 30, 30, 1, 89, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (179, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (180, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (181, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (182, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (183, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (184, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (185, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (186, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (187, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (188, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (189, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (190, 25, 11, 1, 100, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (511, 4.8, 27, 1, 125, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (531, 3.9, 18, 1, 129, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (324, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (325, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (326, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (327, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (328, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (329, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (330, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (331, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (332, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (333, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (334, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (335, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (336, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (337, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (338, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (339, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (340, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (341, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (342, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (343, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (344, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (345, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (346, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (347, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (348, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (349, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (350, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (351, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (352, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (353, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (354, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (355, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (356, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (357, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (358, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (359, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (360, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (361, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (362, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (363, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (364, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (365, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (366, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (367, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (368, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (369, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (370, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (371, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (372, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (373, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (374, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (375, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (376, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (377, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (378, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (379, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (380, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (381, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (382, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (383, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (384, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (385, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (386, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (387, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (388, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (389, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (390, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (391, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (392, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (393, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (394, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (395, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (396, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (397, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (398, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (399, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (400, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (401, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (402, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (403, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (404, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (405, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (406, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (407, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (408, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (409, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (410, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (411, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (412, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (413, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (414, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (415, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (416, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (417, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (418, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (419, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (420, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (421, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (422, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (518, 3.9, 18, 2, 126, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (519, 6.5, 56, 1, 126, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (521, 30, 30, 1, 127, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (451, 14, 1, 4, 119, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (452, 12, 2, 4, 119, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (453, 5.6, 1, 4, 120, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (454, 22, 2, 4, 120, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (457, 5.6, 1, 4, 121, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (458, 22, 2, 4, 121, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (467, 5.6, 1, 4, 123, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (468, 22, 2, 4, 123, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (527, 25, 11, 1, 128, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (532, 4.2, 45, 1, 130, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (479, 29, 3, 8, 124, 14);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (480, 22, 2, 4, 124, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (481, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (482, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (483, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (484, 25, 11, 2, 105, 16);
create table payment
(
  id    serial      not null
    constraint payment_pkey
      primary key,
  label varchar(20) not null
    constraint uk_ijunsgo2jk9wmfavo2hyibdud
      unique
);

alter table payment
  owner to lpdm;

INSERT INTO public.payment (id, label) VALUES (1, 'BANKCARD');
INSERT INTO public.payment (id, label) VALUES (2, 'PAYPAL');
INSERT INTO public.payment (id, label) VALUES (3, 'test swagger');
create table coupon
(
  id          serial                not null
    constraint coupon_pkey
      primary key,
  active      boolean default false not null,
  amount      double precision      not null,
  code        varchar               not null,
  description varchar(255)
);

alter table coupon
  owner to lpdm;

INSERT INTO public.coupon (id, active, amount, code, description) VALUES (3, true, 10, '$2a$10$sitgyUaqpfeXfVa.nGv1K.Tg8TLPxlIRlYkbIx/f2fQXgIYl8LtsO', '10€ avec le code promo10');
create table delivery
(
  id     serial           not null
    constraint delivery_pkey
      primary key,
  method varchar(30)      not null,
  amount double precision not null
);

alter table delivery
  owner to lpdm;

INSERT INTO public.delivery (id, method, amount) VALUES (1, 'not define', 0);
INSERT INTO public.delivery (id, method, amount) VALUES (2, 'Livraison à domicile', 4.9);
INSERT INTO public.delivery (id, method, amount) VALUES (3, 'Livraison en magasin', 0);
create table invoice
(
  id        serial      not null
    constraint invoice_pkey
      primary key,
  order_id  integer     not null
    constraint invoice_order_fk
      references "order",
  reference varchar(50) not null
);

alter table invoice
  owner to lpdm;

INSERT INTO public.invoice (id, order_id, reference) VALUES (7, 2, '20190113165641');
INSERT INTO public.invoice (id, order_id, reference) VALUES (8, 1, '20181124113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (9, 3, '20181126043937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (10, 4, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (11, 6, '20190108015120');
INSERT INTO public.invoice (id, order_id, reference) VALUES (12, 7, '20190108015448');
INSERT INTO public.invoice (id, order_id, reference) VALUES (13, 11, '20190108084443');
INSERT INTO public.invoice (id, order_id, reference) VALUES (14, 9, '20190108020325');
INSERT INTO public.invoice (id, order_id, reference) VALUES (15, 125, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (16, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (17, 129, '20190215212718');
INSERT INTO public.invoice (id, order_id, reference) VALUES (18, 1, '20181124113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (19, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (20, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (21, 125, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (22, 120, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (23, 129, '20190215212718');
INSERT INTO public.invoice (id, order_id, reference) VALUES (24, 123, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (25, 120, '20181130113937');
create table "order"
(
  id          serial  not null
    constraint order_pkey
      primary key,
  customer_id integer not null,
  order_date  timestamp,
  status_id   integer,
  store_id    integer,
  total       double precision,
  payment_id  integer
    constraint fkal550jx92fbea8sry5q4siyn1
      references payment,
  coupon      integer
    constraint fkiv08ux3yuf76s37722or3c6j7
      references coupon,
  delivery    integer default 1
    constraint fkt5bqfi3ksom90m3utcjsndqcs
      references delivery,
  tax_amount  double precision
);

alter table "order"
  owner to lpdm;

INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (12, 14, '2019-01-09 15:01:21.589000', 2, 0, 148, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (77, 110, '2019-02-04 00:57:13.626000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (79, 116, '2019-02-04 11:57:35.216000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (85, 118, '2019-02-04 15:35:34.901000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (86, 118, '2019-02-04 15:36:21.033000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (88, 118, '2019-02-05 06:53:55.580000', 3, 0, 69.28, 2, null, null, 6.28);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (13, 14, '2019-01-09 15:05:02.664000', 2, 0, 232, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (14, 14, '2019-01-09 15:16:34.161000', 2, 0, 47, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (15, 13, '2019-01-19 16:10:29.051000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (8, 96, '2019-01-08 02:02:56.277000', 2, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (9, 96, '2019-01-08 02:03:25.873000', 2, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (10, 7, '2019-01-08 08:44:06.683000', 2, 0, 116, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (11, 7, '2019-01-08 08:44:43.678000', 2, 0, 116, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (36, 101, '2019-01-29 16:41:17.343000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (37, 101, '2019-01-29 16:42:01.247000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (38, 101, '2019-01-29 16:43:14.688000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (39, 101, '2019-01-29 16:43:46.368000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (32, 101, '2019-01-27 20:23:14.959000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (33, 101, '2019-01-29 16:36:21.773000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (34, 101, '2019-01-29 16:39:30.339000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (35, 101, '2019-01-29 16:41:01.911000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (44, 101, '2019-01-29 17:20:30.316000', 3, 0, 25, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (45, 101, '2019-01-29 17:29:20.721000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (46, 101, '2019-01-29 16:35:01.530000', 3, 0, 42, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (40, 101, '2019-01-29 16:44:43.209000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (41, 101, '2019-01-29 16:59:14.056000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (42, 101, '2019-01-29 17:09:32.787000', 3, 0, 25, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (43, 101, '2019-01-29 17:14:07.400000', 3, 0, 13, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (20, 3, '2019-01-22 09:09:54.931000', 3, 0, 6.79, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (21, 102, '2019-01-24 11:48:50.606000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (22, 101, '2019-01-27 18:58:51.700000', 3, 0, 24, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (23, 101, '2019-01-27 19:32:51.007000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (16, 13, '2019-01-19 15:48:15.783000', 3, 0, 71, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (17, 86, '2019-01-20 22:53:39.419000', 3, 0, 46, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (18, 86, '2019-01-20 22:59:11.162000', 3, 0, 34, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (19, 86, '2019-01-20 22:05:24.440000', 3, 0, 54, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (28, 101, '2019-01-27 20:06:51.723000', 3, 0, 58, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (29, 101, '2019-01-27 20:12:38.001000', 3, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (30, 101, '2019-01-27 20:14:21.054000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (31, 101, '2019-01-27 20:21:10.823000', 3, 0, 54, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (26, 101, '2019-01-27 19:50:10.095000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (27, 101, '2019-01-27 19:55:41.334000', 3, 0, 70, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (4, 1, '2018-11-30 11:39:37.330300', 4, 2, 104, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (5, 83, '2019-01-07 17:44:13.436000', null, 0, 71, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (6, 83, '2019-01-08 01:51:20.788000', 2, 0, 59, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (7, 83, '2019-01-08 01:54:48.572000', 2, 0, 66, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (1, 1, '2018-11-24 11:39:37.330000', 4, 1, 27.43, 1, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (2, 2, '2018-11-30 08:30:37.330000', 4, 2, 52, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (3, 3, '2018-11-26 04:39:37.330000', 4, 1, 104, 1, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (120, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (121, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (99, 122, '2019-02-11 14:13:18.634000', 6, 0, 25.156000000000002, null, null, 2, 1.056);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (124, 122, '2018-11-30 11:39:37.330300', 5, 2, 160, 1, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (97, 122, '2019-02-11 09:39:09.999000', 6, 0, 348, null, null, null, 48);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (95, 122, '2019-02-07 19:08:24.543000', 6, 0, 999, null, null, null, 99);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (126, 122, '2019-02-13 23:02:30.159000', 6, 0, 19.99, 2, null, 2, 0.79);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (101, 122, '2019-02-12 18:13:46.850000', 6, 0, 268.22799999999995, 2, null, 2, 13.728000000000007);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (128, 122, '2019-02-15 13:38:41.242000', 2, 0, 33.9, 2, null, 2, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (130, 124, '2019-02-19 17:15:06.164000', 3, 0, 4.43, 2, null, null, 0.23);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (103, 122, '2019-02-13 17:00:59.308000', 6, 0, 551, null, null, null, 76);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (104, 122, '2019-02-13 17:05:46.686000', 6, 0, 62.9, null, null, 2, 8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (52, 101, '2019-01-30 14:33:33.175000', 3, 0, 24, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (53, 101, '2019-01-30 13:42:05.511000', 3, 0, 29, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (54, 101, '2019-01-30 13:42:40.693000', 3, 0, 56, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (55, 101, '2019-01-30 21:00:28.726000', 3, 0, 50, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (49, 105, '2019-01-29 23:31:47.843000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (50, 105, '2019-01-29 23:54:58.051000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (51, 105, '2019-01-30 11:16:50.742000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (60, 110, '2019-02-03 15:52:16.316000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (61, 110, '2019-02-03 15:52:55.671000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (62, 110, '2019-02-03 15:52:59.308000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (63, 110, '2019-02-03 16:05:17.090000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (56, 105, '2019-01-30 22:14:18.926000', 3, 0, 80, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (57, 105, '2019-02-01 12:32:58.268000', 3, 0, 22, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (58, 110, '2019-02-03 15:46:11.436000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (59, 110, '2019-02-03 15:50:09.886000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (68, 110, '2019-02-03 16:23:14.859000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (69, 110, '2019-02-03 16:43:56.675000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (70, 110, '2019-02-03 16:47:15.798000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (71, 110, '2019-02-03 16:50:34.740000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (64, 110, '2019-02-03 16:10:10.809000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (65, 110, '2019-02-03 16:12:36.581000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (66, 110, '2019-02-03 16:14:19.127000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (67, 110, '2019-02-03 16:17:45.297000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (76, 110, '2019-02-03 17:12:26.030000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (72, 110, '2019-02-03 16:51:05.398000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (73, 110, '2019-02-03 16:52:19.905000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (74, 110, '2019-02-03 16:52:39.842000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (75, 110, '2019-02-03 16:56:59.377000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (24, 101, '2019-01-27 19:45:00.321000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (25, 101, '2019-01-27 19:48:02.501000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (78, 110, '2019-02-04 08:22:03.505000', 3, 0, 62.8, 2, null, null, 7.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (80, 118, '2019-02-04 16:14:46.533000', 3, 0, 91.8, 2, null, null, 11.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (81, 118, '2019-02-04 16:28:14.688000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (82, 118, '2019-02-04 16:30:57.957000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (83, 118, '2019-02-04 16:33:15.326000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (84, 118, '2019-02-04 16:34:10.675000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (87, 118, '2019-02-04 17:15:15.140000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (89, 118, '2019-02-06 13:13:24.972000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (100, 118, '2019-02-11 17:46:59.822000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (98, 122, '2019-02-11 10:24:14.500000', 6, 0, 12.238, null, null, null, 0.638);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (96, 122, '2019-02-07 19:14:05.682000', 6, 0, 133.2, null, null, null, 13.2);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (94, 122, '2019-02-07 19:03:14.305000', 6, 0, 199.8, null, null, null, 19.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (93, 122, '2019-02-07 18:54:18.237000', 6, 0, 865.8, null, null, null, 85.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (92, 122, '2019-02-07 18:42:31.934000', 6, 0, 174, null, null, null, 24);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (102, 122, '2019-02-13 15:44:38.631000', 6, 0, 40.512, null, null, null, 2.112);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (108, 1, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (115, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (119, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (123, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (105, 122, '2019-02-13 17:15:41.337000', 6, 0, 236.9, 2, null, 2, 32);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (125, 122, '2018-11-30 11:39:37.330300', 2, 2, 9.96, 2, null, 2, 0.26);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (127, 122, '2019-02-15 12:08:53.942000', 6, 0, 33.3, null, null, null, 3.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (129, 122, '2019-02-15 21:27:18.240000', 2, 0, 9.01, 2, null, 2, 0.21);
create table ordered_product
(
  id         integer                      not null
    constraint ordered_product_pkey
      primary key,
  price      double precision,
  product_id integer,
  quantity   integer,
  order_id   integer
    constraint fk684l0g7wdvypylqnc39jfjkld
      references "order",
  tax        double precision default 5.5 not null
);

alter table ordered_product
  owner to lpdm;

INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (1, 14, 1, 1, 1, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (2, 12, 2, 1, 1, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (3, 14, 1, 2, 2, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (4, 12, 2, 2, 2, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (5, 14, 1, 4, 3, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (6, 12, 2, 4, 3, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (7, 14, 1, 4, 4, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (8, 12, 2, 4, 4, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (9, 29, 4, 1, 5, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (10, 14, 7, 3, 5, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (11, 14, 20, 1, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (12, 21, 8, 1, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (13, 12, 9, 2, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (14, 27, 1, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (15, 14, 7, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (16, 25, 11, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (17, 14, 7, 1, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (18, 21, 8, 2, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (19, 12, 9, 1, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (20, 14, 7, 1, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (21, 21, 8, 2, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (22, 12, 9, 1, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (23, 29, 4, 2, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (24, 21, 8, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (25, 25, 11, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (26, 12, 9, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (27, 29, 4, 2, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (28, 21, 8, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (29, 25, 11, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (30, 12, 9, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (31, 20, 5, 2, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (32, 30, 6, 2, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (33, 21, 8, 1, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (34, 20, 5, 2, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (35, 30, 6, 3, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (36, 21, 8, 1, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (37, 27, 1, 2, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (38, 27, 1, 1, 14, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (39, 20, 5, 1, 14, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (40, 19, 14, 1, 15, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (41, 22, 2, 1, 15, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (42, 22, 2, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (43, 29, 3, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (44, 20, 5, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (45, 21, 8, 1, 17, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (46, 25, 13, 1, 17, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (47, 21, 8, 1, 18, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (48, 13, 12, 1, 18, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (49, 12, 9, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (50, 29, 10, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (51, 13, 12, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (52, 0.8, 2, 1, 20, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (53, 5.99, 5, 1, 20, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (54, 12, 9, 1, 21, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (55, 12, 9, 2, 22, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (56, 12, 9, 1, 23, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (57, 12, 9, 1, 24, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (58, 12, 9, 1, 25, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (59, 29, 10, 1, 25, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (60, 29, 10, 1, 26, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (61, 29, 10, 2, 27, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (62, 12, 9, 1, 27, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (63, 29, 10, 2, 28, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (64, 29, 10, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (65, 25, 13, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (66, 14, 18, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (67, 29, 10, 1, 30, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (68, 12, 9, 1, 30, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (69, 12, 9, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (70, 13, 12, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (71, 29, 19, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (72, 12, 9, 1, 32, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (73, 29, 10, 1, 32, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (74, 12, 9, 1, 33, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (75, 29, 10, 1, 33, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (76, 12, 9, 1, 34, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (77, 12, 9, 1, 35, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (78, 12, 9, 1, 36, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (79, 12, 9, 1, 37, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (80, 29, 10, 1, 38, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (81, 29, 10, 1, 39, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (82, 29, 10, 1, 40, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (83, 12, 9, 1, 41, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (84, 29, 10, 1, 41, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (85, 25, 11, 1, 42, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (86, 13, 12, 1, 43, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (87, 13, 12, 1, 44, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (88, 12, 9, 1, 44, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (89, 12, 9, 1, 45, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (90, 13, 12, 1, 46, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (91, 29, 10, 1, 46, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (92, 22, 2, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (93, 20, 5, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (94, 27, 1, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (95, 22, 2, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (96, 20, 5, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (97, 27, 1, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (98, 22, 2, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (99, 20, 5, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (100, 27, 1, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (101, 24, 22, 1, 52, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (102, 29, 3, 1, 53, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (103, 29, 3, 1, 54, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (104, 27, 1, 1, 54, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (105, 20, 24, 1, 55, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (106, 30, 6, 1, 55, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (107, 29, 3, 2, 56, 14);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (108, 22, 2, 1, 56, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (109, 22, 2, 1, 57, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (110, 25, 11, 1, 58, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (111, 30, 30, 1, 58, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (112, 25, 42, 1, 59, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (113, 25, 42, 1, 60, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (114, 25, 42, 1, 61, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (115, 25, 42, 1, 62, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (116, 25, 42, 1, 63, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (117, 25, 42, 1, 64, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (118, 25, 42, 1, 65, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (119, 25, 11, 1, 66, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (120, 30, 30, 1, 66, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (121, 25, 11, 1, 67, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (122, 30, 30, 1, 67, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (123, 13, 34, 2, 67, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (124, 25, 11, 1, 68, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (125, 30, 30, 1, 68, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (126, 13, 34, 2, 68, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (127, 25, 11, 1, 69, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (128, 30, 30, 1, 69, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (129, 13, 34, 2, 69, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (130, 25, 11, 1, 70, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (131, 30, 30, 1, 70, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (132, 13, 34, 2, 70, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (133, 25, 11, 1, 71, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (134, 30, 30, 1, 71, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (135, 13, 34, 2, 71, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (136, 25, 11, 1, 72, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (137, 30, 30, 1, 72, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (138, 13, 34, 2, 72, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (139, 25, 11, 1, 73, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (140, 30, 30, 1, 73, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (141, 13, 34, 2, 73, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (142, 25, 11, 1, 74, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (143, 30, 30, 1, 74, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (144, 13, 34, 2, 74, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (145, 25, 11, 1, 75, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (146, 30, 30, 1, 75, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (147, 13, 34, 2, 75, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (148, 25, 11, 1, 76, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (149, 30, 30, 1, 76, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (150, 25, 11, 1, 77, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (151, 30, 30, 1, 77, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (152, 25, 42, 1, 78, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (153, 30, 30, 1, 78, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (154, 25, 11, 1, 79, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (155, 30, 30, 1, 79, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (156, 25, 11, 1, 80, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (157, 30, 30, 1, 80, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (158, 25, 42, 1, 80, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (159, 25, 11, 1, 81, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (160, 30, 30, 1, 81, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (161, 25, 11, 1, 82, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (162, 30, 30, 1, 82, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (163, 25, 11, 1, 83, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (164, 30, 30, 1, 83, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (165, 25, 11, 1, 84, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (166, 30, 30, 1, 84, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (167, 25, 11, 1, 85, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (168, 25, 11, 1, 86, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (169, 25, 11, 1, 87, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (170, 30, 30, 1, 88, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (171, 13, 12, 1, 88, 6);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (172, 20, 24, 1, 88, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (173, 25, 11, 1, 89, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (174, 30, 30, 1, 89, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (179, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (180, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (181, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (182, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (183, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (184, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (185, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (186, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (187, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (188, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (189, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (190, 25, 11, 1, 100, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (511, 4.8, 27, 1, 125, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (531, 3.9, 18, 1, 129, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (324, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (325, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (326, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (327, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (328, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (329, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (330, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (331, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (332, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (333, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (334, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (335, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (336, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (337, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (338, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (339, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (340, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (341, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (342, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (343, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (344, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (345, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (346, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (347, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (348, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (349, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (350, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (351, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (352, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (353, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (354, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (355, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (356, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (357, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (358, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (359, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (360, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (361, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (362, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (363, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (364, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (365, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (366, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (367, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (368, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (369, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (370, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (371, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (372, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (373, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (374, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (375, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (376, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (377, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (378, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (379, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (380, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (381, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (382, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (383, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (384, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (385, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (386, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (387, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (388, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (389, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (390, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (391, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (392, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (393, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (394, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (395, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (396, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (397, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (398, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (399, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (400, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (401, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (402, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (403, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (404, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (405, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (406, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (407, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (408, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (409, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (410, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (411, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (412, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (413, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (414, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (415, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (416, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (417, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (418, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (419, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (420, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (421, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (422, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (518, 3.9, 18, 2, 126, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (519, 6.5, 56, 1, 126, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (521, 30, 30, 1, 127, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (451, 14, 1, 4, 119, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (452, 12, 2, 4, 119, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (453, 5.6, 1, 4, 120, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (454, 22, 2, 4, 120, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (457, 5.6, 1, 4, 121, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (458, 22, 2, 4, 121, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (467, 5.6, 1, 4, 123, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (468, 22, 2, 4, 123, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (527, 25, 11, 1, 128, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (532, 4.2, 45, 1, 130, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (479, 29, 3, 8, 124, 14);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (480, 22, 2, 4, 124, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (481, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (482, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (483, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (484, 25, 11, 2, 105, 16);
create table payment
(
  id    serial      not null
    constraint payment_pkey
      primary key,
  label varchar(20) not null
    constraint uk_ijunsgo2jk9wmfavo2hyibdud
      unique
);

alter table payment
  owner to lpdm;

INSERT INTO public.payment (id, label) VALUES (1, 'BANKCARD');
INSERT INTO public.payment (id, label) VALUES (2, 'PAYPAL');
INSERT INTO public.payment (id, label) VALUES (3, 'test swagger');
create table coupon
(
  id          serial                not null
    constraint coupon_pkey
      primary key,
  active      boolean default false not null,
  amount      double precision      not null,
  code        varchar               not null,
  description varchar(255)
);

alter table coupon
  owner to lpdm;

INSERT INTO public.coupon (id, active, amount, code, description) VALUES (3, true, 10, '$2a$10$sitgyUaqpfeXfVa.nGv1K.Tg8TLPxlIRlYkbIx/f2fQXgIYl8LtsO', '10€ avec le code promo10');
create table delivery
(
  id     serial           not null
    constraint delivery_pkey
      primary key,
  method varchar(30)      not null,
  amount double precision not null
);

alter table delivery
  owner to lpdm;

INSERT INTO public.delivery (id, method, amount) VALUES (1, 'not define', 0);
INSERT INTO public.delivery (id, method, amount) VALUES (2, 'Livraison à domicile', 4.9);
INSERT INTO public.delivery (id, method, amount) VALUES (3, 'Livraison en magasin', 0);
create table invoice
(
  id        serial      not null
    constraint invoice_pkey
      primary key,
  order_id  integer     not null
    constraint invoice_order_fk
      references "order",
  reference varchar(50) not null
);

alter table invoice
  owner to lpdm;

INSERT INTO public.invoice (id, order_id, reference) VALUES (7, 2, '20190113165641');
INSERT INTO public.invoice (id, order_id, reference) VALUES (8, 1, '20181124113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (9, 3, '20181126043937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (10, 4, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (11, 6, '20190108015120');
INSERT INTO public.invoice (id, order_id, reference) VALUES (12, 7, '20190108015448');
INSERT INTO public.invoice (id, order_id, reference) VALUES (13, 11, '20190108084443');
INSERT INTO public.invoice (id, order_id, reference) VALUES (14, 9, '20190108020325');
INSERT INTO public.invoice (id, order_id, reference) VALUES (15, 125, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (16, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (17, 129, '20190215212718');
INSERT INTO public.invoice (id, order_id, reference) VALUES (18, 1, '20181124113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (19, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (20, 128, '20190215133841');
INSERT INTO public.invoice (id, order_id, reference) VALUES (21, 125, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (22, 120, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (23, 129, '20190215212718');
INSERT INTO public.invoice (id, order_id, reference) VALUES (24, 123, '20181130113937');
INSERT INTO public.invoice (id, order_id, reference) VALUES (25, 120, '20181130113937');
create table "order"
(
  id          serial  not null
    constraint order_pkey
      primary key,
  customer_id integer not null,
  order_date  timestamp,
  status_id   integer,
  store_id    integer,
  total       double precision,
  payment_id  integer
    constraint fkal550jx92fbea8sry5q4siyn1
      references payment,
  coupon      integer
    constraint fkiv08ux3yuf76s37722or3c6j7
      references coupon,
  delivery    integer default 1
    constraint fkt5bqfi3ksom90m3utcjsndqcs
      references delivery,
  tax_amount  double precision
);

alter table "order"
  owner to lpdm;

INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (12, 14, '2019-01-09 15:01:21.589000', 2, 0, 148, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (77, 110, '2019-02-04 00:57:13.626000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (79, 116, '2019-02-04 11:57:35.216000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (85, 118, '2019-02-04 15:35:34.901000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (86, 118, '2019-02-04 15:36:21.033000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (88, 118, '2019-02-05 06:53:55.580000', 3, 0, 69.28, 2, null, null, 6.28);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (13, 14, '2019-01-09 15:05:02.664000', 2, 0, 232, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (14, 14, '2019-01-09 15:16:34.161000', 2, 0, 47, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (15, 13, '2019-01-19 16:10:29.051000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (8, 96, '2019-01-08 02:02:56.277000', 2, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (9, 96, '2019-01-08 02:03:25.873000', 2, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (10, 7, '2019-01-08 08:44:06.683000', 2, 0, 116, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (11, 7, '2019-01-08 08:44:43.678000', 2, 0, 116, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (36, 101, '2019-01-29 16:41:17.343000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (37, 101, '2019-01-29 16:42:01.247000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (38, 101, '2019-01-29 16:43:14.688000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (39, 101, '2019-01-29 16:43:46.368000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (32, 101, '2019-01-27 20:23:14.959000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (33, 101, '2019-01-29 16:36:21.773000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (34, 101, '2019-01-29 16:39:30.339000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (35, 101, '2019-01-29 16:41:01.911000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (44, 101, '2019-01-29 17:20:30.316000', 3, 0, 25, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (45, 101, '2019-01-29 17:29:20.721000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (46, 101, '2019-01-29 16:35:01.530000', 3, 0, 42, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (40, 101, '2019-01-29 16:44:43.209000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (41, 101, '2019-01-29 16:59:14.056000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (42, 101, '2019-01-29 17:09:32.787000', 3, 0, 25, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (43, 101, '2019-01-29 17:14:07.400000', 3, 0, 13, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (20, 3, '2019-01-22 09:09:54.931000', 3, 0, 6.79, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (21, 102, '2019-01-24 11:48:50.606000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (22, 101, '2019-01-27 18:58:51.700000', 3, 0, 24, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (23, 101, '2019-01-27 19:32:51.007000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (16, 13, '2019-01-19 15:48:15.783000', 3, 0, 71, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (17, 86, '2019-01-20 22:53:39.419000', 3, 0, 46, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (18, 86, '2019-01-20 22:59:11.162000', 3, 0, 34, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (19, 86, '2019-01-20 22:05:24.440000', 3, 0, 54, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (28, 101, '2019-01-27 20:06:51.723000', 3, 0, 58, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (29, 101, '2019-01-27 20:12:38.001000', 3, 0, 68, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (30, 101, '2019-01-27 20:14:21.054000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (31, 101, '2019-01-27 20:21:10.823000', 3, 0, 54, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (26, 101, '2019-01-27 19:50:10.095000', 3, 0, 29, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (27, 101, '2019-01-27 19:55:41.334000', 3, 0, 70, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (4, 1, '2018-11-30 11:39:37.330300', 4, 2, 104, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (5, 83, '2019-01-07 17:44:13.436000', null, 0, 71, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (6, 83, '2019-01-08 01:51:20.788000', 2, 0, 59, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (7, 83, '2019-01-08 01:54:48.572000', 2, 0, 66, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (1, 1, '2018-11-24 11:39:37.330000', 4, 1, 27.43, 1, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (2, 2, '2018-11-30 08:30:37.330000', 4, 2, 52, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (3, 3, '2018-11-26 04:39:37.330000', 4, 1, 104, 1, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (120, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (121, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (99, 122, '2019-02-11 14:13:18.634000', 6, 0, 25.156000000000002, null, null, 2, 1.056);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (124, 122, '2018-11-30 11:39:37.330300', 5, 2, 160, 1, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (97, 122, '2019-02-11 09:39:09.999000', 6, 0, 348, null, null, null, 48);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (95, 122, '2019-02-07 19:08:24.543000', 6, 0, 999, null, null, null, 99);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (126, 122, '2019-02-13 23:02:30.159000', 6, 0, 19.99, 2, null, 2, 0.79);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (101, 122, '2019-02-12 18:13:46.850000', 6, 0, 268.22799999999995, 2, null, 2, 13.728000000000007);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (128, 122, '2019-02-15 13:38:41.242000', 2, 0, 33.9, 2, null, 2, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (130, 124, '2019-02-19 17:15:06.164000', 3, 0, 4.43, 2, null, null, 0.23);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (103, 122, '2019-02-13 17:00:59.308000', 6, 0, 551, null, null, null, 76);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (104, 122, '2019-02-13 17:05:46.686000', 6, 0, 62.9, null, null, 2, 8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (52, 101, '2019-01-30 14:33:33.175000', 3, 0, 24, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (53, 101, '2019-01-30 13:42:05.511000', 3, 0, 29, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (54, 101, '2019-01-30 13:42:40.693000', 3, 0, 56, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (55, 101, '2019-01-30 21:00:28.726000', 3, 0, 50, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (49, 105, '2019-01-29 23:31:47.843000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (50, 105, '2019-01-29 23:54:58.051000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (51, 105, '2019-01-30 11:16:50.742000', 3, 0, 69, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (60, 110, '2019-02-03 15:52:16.316000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (61, 110, '2019-02-03 15:52:55.671000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (62, 110, '2019-02-03 15:52:59.308000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (63, 110, '2019-02-03 16:05:17.090000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (56, 105, '2019-01-30 22:14:18.926000', 3, 0, 80, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (57, 105, '2019-02-01 12:32:58.268000', 3, 0, 22, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (58, 110, '2019-02-03 15:46:11.436000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (59, 110, '2019-02-03 15:50:09.886000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (68, 110, '2019-02-03 16:23:14.859000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (69, 110, '2019-02-03 16:43:56.675000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (70, 110, '2019-02-03 16:47:15.798000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (71, 110, '2019-02-03 16:50:34.740000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (64, 110, '2019-02-03 16:10:10.809000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (65, 110, '2019-02-03 16:12:36.581000', 3, 0, 25, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (66, 110, '2019-02-03 16:14:19.127000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (67, 110, '2019-02-03 16:17:45.297000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (76, 110, '2019-02-03 17:12:26.030000', 3, 0, 55, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (72, 110, '2019-02-03 16:51:05.398000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (73, 110, '2019-02-03 16:52:19.905000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (74, 110, '2019-02-03 16:52:39.842000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (75, 110, '2019-02-03 16:56:59.377000', 3, 0, 81, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (24, 101, '2019-01-27 19:45:00.321000', 3, 0, 12, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (25, 101, '2019-01-27 19:48:02.501000', 3, 0, 41, 2, null, 1, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (78, 110, '2019-02-04 08:22:03.505000', 3, 0, 62.8, 2, null, null, 7.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (80, 118, '2019-02-04 16:14:46.533000', 3, 0, 91.8, 2, null, null, 11.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (81, 118, '2019-02-04 16:28:14.688000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (82, 118, '2019-02-04 16:30:57.957000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (83, 118, '2019-02-04 16:33:15.326000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (84, 118, '2019-02-04 16:34:10.675000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (87, 118, '2019-02-04 17:15:15.140000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (89, 118, '2019-02-06 13:13:24.972000', 3, 0, 62.3, 2, null, null, 7.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (100, 118, '2019-02-11 17:46:59.822000', 3, 0, 29, 2, null, null, 4);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (98, 122, '2019-02-11 10:24:14.500000', 6, 0, 12.238, null, null, null, 0.638);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (96, 122, '2019-02-07 19:14:05.682000', 6, 0, 133.2, null, null, null, 13.2);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (94, 122, '2019-02-07 19:03:14.305000', 6, 0, 199.8, null, null, null, 19.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (93, 122, '2019-02-07 18:54:18.237000', 6, 0, 865.8, null, null, null, 85.8);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (92, 122, '2019-02-07 18:42:31.934000', 6, 0, 174, null, null, null, 24);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (102, 122, '2019-02-13 15:44:38.631000', 6, 0, 40.512, null, null, null, 2.112);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (108, 1, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (115, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (119, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (123, 122, '2018-11-30 11:39:37.330300', 5, 2, 104, 2, null, null, 0);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (105, 122, '2019-02-13 17:15:41.337000', 6, 0, 236.9, 2, null, 2, 32);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (125, 122, '2018-11-30 11:39:37.330300', 2, 2, 9.96, 2, null, 2, 0.26);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (127, 122, '2019-02-15 12:08:53.942000', 6, 0, 33.3, null, null, null, 3.3);
INSERT INTO public."order" (id, customer_id, order_date, status_id, store_id, total, payment_id, coupon, delivery, tax_amount) VALUES (129, 122, '2019-02-15 21:27:18.240000', 2, 0, 9.01, 2, null, 2, 0.21);
create table ordered_product
(
  id         integer                      not null
    constraint ordered_product_pkey
      primary key,
  price      double precision,
  product_id integer,
  quantity   integer,
  order_id   integer
    constraint fk684l0g7wdvypylqnc39jfjkld
      references "order",
  tax        double precision default 5.5 not null
);

alter table ordered_product
  owner to lpdm;

INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (1, 14, 1, 1, 1, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (2, 12, 2, 1, 1, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (3, 14, 1, 2, 2, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (4, 12, 2, 2, 2, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (5, 14, 1, 4, 3, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (6, 12, 2, 4, 3, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (7, 14, 1, 4, 4, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (8, 12, 2, 4, 4, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (9, 29, 4, 1, 5, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (10, 14, 7, 3, 5, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (11, 14, 20, 1, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (12, 21, 8, 1, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (13, 12, 9, 2, 6, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (14, 27, 1, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (15, 14, 7, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (16, 25, 11, 1, 7, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (17, 14, 7, 1, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (18, 21, 8, 2, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (19, 12, 9, 1, 8, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (20, 14, 7, 1, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (21, 21, 8, 2, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (22, 12, 9, 1, 9, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (23, 29, 4, 2, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (24, 21, 8, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (25, 25, 11, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (26, 12, 9, 1, 10, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (27, 29, 4, 2, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (28, 21, 8, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (29, 25, 11, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (30, 12, 9, 1, 11, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (31, 20, 5, 2, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (32, 30, 6, 2, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (33, 21, 8, 1, 12, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (34, 20, 5, 2, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (35, 30, 6, 3, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (36, 21, 8, 1, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (37, 27, 1, 2, 13, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (38, 27, 1, 1, 14, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (39, 20, 5, 1, 14, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (40, 19, 14, 1, 15, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (41, 22, 2, 1, 15, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (42, 22, 2, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (43, 29, 3, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (44, 20, 5, 1, 16, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (45, 21, 8, 1, 17, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (46, 25, 13, 1, 17, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (47, 21, 8, 1, 18, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (48, 13, 12, 1, 18, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (49, 12, 9, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (50, 29, 10, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (51, 13, 12, 1, 19, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (52, 0.8, 2, 1, 20, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (53, 5.99, 5, 1, 20, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (54, 12, 9, 1, 21, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (55, 12, 9, 2, 22, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (56, 12, 9, 1, 23, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (57, 12, 9, 1, 24, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (58, 12, 9, 1, 25, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (59, 29, 10, 1, 25, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (60, 29, 10, 1, 26, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (61, 29, 10, 2, 27, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (62, 12, 9, 1, 27, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (63, 29, 10, 2, 28, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (64, 29, 10, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (65, 25, 13, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (66, 14, 18, 1, 29, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (67, 29, 10, 1, 30, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (68, 12, 9, 1, 30, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (69, 12, 9, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (70, 13, 12, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (71, 29, 19, 1, 31, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (72, 12, 9, 1, 32, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (73, 29, 10, 1, 32, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (74, 12, 9, 1, 33, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (75, 29, 10, 1, 33, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (76, 12, 9, 1, 34, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (77, 12, 9, 1, 35, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (78, 12, 9, 1, 36, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (79, 12, 9, 1, 37, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (80, 29, 10, 1, 38, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (81, 29, 10, 1, 39, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (82, 29, 10, 1, 40, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (83, 12, 9, 1, 41, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (84, 29, 10, 1, 41, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (85, 25, 11, 1, 42, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (86, 13, 12, 1, 43, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (87, 13, 12, 1, 44, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (88, 12, 9, 1, 44, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (89, 12, 9, 1, 45, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (90, 13, 12, 1, 46, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (91, 29, 10, 1, 46, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (92, 22, 2, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (93, 20, 5, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (94, 27, 1, 1, 49, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (95, 22, 2, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (96, 20, 5, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (97, 27, 1, 1, 50, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (98, 22, 2, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (99, 20, 5, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (100, 27, 1, 1, 51, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (101, 24, 22, 1, 52, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (102, 29, 3, 1, 53, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (103, 29, 3, 1, 54, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (104, 27, 1, 1, 54, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (105, 20, 24, 1, 55, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (106, 30, 6, 1, 55, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (107, 29, 3, 2, 56, 14);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (108, 22, 2, 1, 56, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (109, 22, 2, 1, 57, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (110, 25, 11, 1, 58, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (111, 30, 30, 1, 58, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (112, 25, 42, 1, 59, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (113, 25, 42, 1, 60, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (114, 25, 42, 1, 61, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (115, 25, 42, 1, 62, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (116, 25, 42, 1, 63, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (117, 25, 42, 1, 64, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (118, 25, 42, 1, 65, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (119, 25, 11, 1, 66, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (120, 30, 30, 1, 66, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (121, 25, 11, 1, 67, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (122, 30, 30, 1, 67, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (123, 13, 34, 2, 67, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (124, 25, 11, 1, 68, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (125, 30, 30, 1, 68, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (126, 13, 34, 2, 68, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (127, 25, 11, 1, 69, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (128, 30, 30, 1, 69, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (129, 13, 34, 2, 69, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (130, 25, 11, 1, 70, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (131, 30, 30, 1, 70, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (132, 13, 34, 2, 70, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (133, 25, 11, 1, 71, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (134, 30, 30, 1, 71, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (135, 13, 34, 2, 71, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (136, 25, 11, 1, 72, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (137, 30, 30, 1, 72, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (138, 13, 34, 2, 72, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (139, 25, 11, 1, 73, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (140, 30, 30, 1, 73, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (141, 13, 34, 2, 73, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (142, 25, 11, 1, 74, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (143, 30, 30, 1, 74, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (144, 13, 34, 2, 74, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (145, 25, 11, 1, 75, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (146, 30, 30, 1, 75, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (147, 13, 34, 2, 75, 9);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (148, 25, 11, 1, 76, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (149, 30, 30, 1, 76, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (150, 25, 11, 1, 77, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (151, 30, 30, 1, 77, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (152, 25, 42, 1, 78, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (153, 30, 30, 1, 78, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (154, 25, 11, 1, 79, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (155, 30, 30, 1, 79, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (156, 25, 11, 1, 80, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (157, 30, 30, 1, 80, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (158, 25, 42, 1, 80, 18);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (159, 25, 11, 1, 81, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (160, 30, 30, 1, 81, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (161, 25, 11, 1, 82, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (162, 30, 30, 1, 82, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (163, 25, 11, 1, 83, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (164, 30, 30, 1, 83, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (165, 25, 11, 1, 84, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (166, 30, 30, 1, 84, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (167, 25, 11, 1, 85, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (168, 25, 11, 1, 86, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (169, 25, 11, 1, 87, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (170, 30, 30, 1, 88, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (171, 13, 12, 1, 88, 6);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (172, 20, 24, 1, 88, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (173, 25, 11, 1, 89, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (174, 30, 30, 1, 89, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (179, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (180, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (181, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (182, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (183, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (184, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (185, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (186, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (187, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (188, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (189, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (190, 25, 11, 1, 100, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (511, 4.8, 27, 1, 125, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (531, 3.9, 18, 1, 129, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (324, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (325, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (326, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (327, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (328, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (329, 4.8, 27, 1, 99, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (330, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (331, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (332, 5.8, 41, 1, 98, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (333, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (334, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (335, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (336, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (337, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (338, 25, 11, 3, 97, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (339, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (340, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (341, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (342, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (343, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (344, 30, 30, 1, 96, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (345, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (346, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (347, 30, 30, 15, 95, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (348, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (349, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (350, 30, 30, 3, 94, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (351, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (352, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (353, 30, 30, 13, 93, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (354, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (355, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (356, 25, 11, 3, 92, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (357, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (358, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (359, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (360, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (361, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (362, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (363, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (364, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (365, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (366, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (367, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (368, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (369, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (370, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (371, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (372, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (373, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (374, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (375, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (376, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (377, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (378, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (379, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (380, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (381, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (382, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (383, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (384, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (385, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (386, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (387, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (388, 5.2, 33, 3, 101, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (389, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (390, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (391, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (392, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (393, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (394, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (395, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (396, 4.8, 27, 2, 102, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (397, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (398, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (399, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (400, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (401, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (402, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (403, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (404, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (405, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (406, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (407, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (408, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (409, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (410, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (411, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (412, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (413, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (414, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (415, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (416, 25, 11, 2, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (417, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (418, 25, 11, 1, 103, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (419, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (420, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (421, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (422, 25, 11, 1, 104, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (518, 3.9, 18, 2, 126, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (519, 6.5, 56, 1, 126, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (521, 30, 30, 1, 127, 11);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (451, 14, 1, 4, 119, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (452, 12, 2, 4, 119, 0);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (453, 5.6, 1, 4, 120, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (454, 22, 2, 4, 120, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (457, 5.6, 1, 4, 121, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (458, 22, 2, 4, 121, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (467, 5.6, 1, 4, 123, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (468, 22, 2, 4, 123, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (527, 25, 11, 1, 128, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (532, 4.2, 45, 1, 130, 5.5);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (479, 29, 3, 8, 124, 14);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (480, 22, 2, 4, 124, 10);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (481, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (482, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (483, 25, 11, 2, 105, 16);
INSERT INTO public.ordered_product (id, price, product_id, quantity, order_id, tax) VALUES (484, 25, 11, 2, 105, 16);
create table payment
(
  id    serial      not null
    constraint payment_pkey
      primary key,
  label varchar(20) not null
    constraint uk_ijunsgo2jk9wmfavo2hyibdud
      unique
);

alter table payment
  owner to lpdm;

INSERT INTO public.payment (id, label) VALUES (1, 'BANKCARD');
INSERT INTO public.payment (id, label) VALUES (2, 'PAYPAL');