INSERT INTO btg_test."order" (id, id_client) VALUES (100, 32) on conflict (id) do nothing;

INSERT INTO btg_test."item" (id_order, product_name, price, total) VALUES 
      (100, 'TV', 2000.00, 1), 
      (100, 'PC', 5000.00, 1);
