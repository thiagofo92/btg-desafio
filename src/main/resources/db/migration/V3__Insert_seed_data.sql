INSERT INTO btg."order" (id, id_client) VALUES (100, 32);

INSERT INTO btg."item" (id_order, product_name, price, total) VALUES 
      (100, 'TV', 2000.00, 1), 
      (100, 'PC', 5000.00, 1) on conflict do nothing;
