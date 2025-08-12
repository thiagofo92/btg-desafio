CREATE TABLE IF NOT EXISTS btg."order" (
	id int4 NOT NULL,
	id_client int4 NULL,
	"date" timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT order_pk PRIMARY KEY (id)
);
CREATE INDEX order_id_client_idx ON btg."order" USING btree (id_client);


CREATE TABLE IF NOT EXISTS btg."item" (
	id serial NOT NULL,
	id_order int NULL,
	product_name varchar NULL,
	price numeric NULL,
	total int NULL,
	CONSTRAINT item_pk PRIMARY KEY (id),
	CONSTRAINT item_order_fk FOREIGN KEY (id_order) REFERENCES btg."order"(id)
);

