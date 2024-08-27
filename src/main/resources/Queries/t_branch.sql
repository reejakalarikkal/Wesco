CREATE TABLE IF NOT EXISTS t_branch (
  id serial PRIMARY KEY,
  branch_id varchar(50)  NOT NULL,
  branch_name varchar(50) NOT NULL,
  branch_loc varchar(50),
  zip varchar(50),
  product_id varchar(50),
  qty_on_hand bigint ,
  CONSTRAINT unique_branch_id_product_id UNIQUE (branch_id,product_id),
  FOREIGN KEY (product_id) REFERENCES t_product(product_id)
)
