CREATE TABLE IF NOT EXISTS btg."order" (
	id int NOT NULL,
	id_client int NULL,
	CONSTRAINT order_pk PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS btg.item (
	id serial NOT NULL,
	id_order int NULL,
	product_name varchar NULL,
	price numeric NULL,
	total int NULL,
	CONSTRAINT item_pk PRIMARY KEY (id),
	CONSTRAINT item_order_fk FOREIGN KEY (id_order) REFERENCES btg."order"(id)
);

