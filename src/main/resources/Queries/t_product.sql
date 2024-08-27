CREATE TABLE IF NOT EXISTS t_product (
  id serial PRIMARY KEY,
  product_id varchar(50) UNIQUE NOT NULL,
  pname varchar(50) NOT NULL,
  pdesc varchar(50),
  unit_price NUMERIC(10,2)
)