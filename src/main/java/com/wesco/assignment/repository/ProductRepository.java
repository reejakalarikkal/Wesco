package com.wesco.assignment.repository;

import com.wesco.assignment.model.Product;
import com.wesco.assignment.model.ProductWithBranch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addProduct(Product product) {
        try {
            String sql = "INSERT INTO t_product (product_id,pname, pdesc, unit_price) VALUES (? ,?, ?, ? )";
            jdbcTemplate.update(sql, product.getProductId(), product.getPname(), product.getPdesc(), product.getUnitPrice());
            log.info("Product added successfully: {}", product);
        } catch (Exception e) {
            log.error("Failed to add product. Error: {}", e.getMessage());
            throw e;
        }
    }

    public Product getProductById(Integer id) {
        try {
            String sql = "SELECT * FROM t_product  WHERE id = ? ";
            Product product = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Product.class));
            log.info("Product retrieved successfully: {}", product);
            return product;
        } catch (Exception e) {
            log.error("Failed to retrieve product by id {}. Error: {}", id, e.getMessage());
            throw e;
        }

    }

    public List<Product> getAllProducts() {
        try {
            String sql = "SELECT * FROM t_product";
            List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
            log.info("Retrieved {} products", products.size());
            return products;
        } catch (Exception e) {
            log.error("Failed to retrieve all products. Error: {}", e.getMessage());
            throw e;
        }
    }

    public void updateProduct(Product product) {
        try {
            String sql = "UPDATE t_product SET pname = ?, pdesc = ?, unit_price = ? WHERE id = ?";
            jdbcTemplate.update(sql, product.getPname(), product.getPdesc(), product.getUnitPrice(), product.getId());
            log.info("Product updated successfully: {}", product);
        } catch (Exception e) {
            log.error("Failed to update product with id {}. Error: {}", product.getId(), e.getMessage());
            throw e;
        }
    }

    public void deleteProduct(Integer id) {
        try {
            String sql = "DELETE FROM t_product WHERE id = ?";
            jdbcTemplate.update(sql, id);
            log.info("Product deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Failed to delete product with id {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }

    public List<ProductWithBranch> findAllProductsWithBranches() {
        try {
            String sql = "SELECT p.*, b.* FROM t_product p " +
                    "LEFT JOIN t_branch b ON p.product_id = b.product_id";
            List<ProductWithBranch> productsWithBranches = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductWithBranch.class));
            log.info("Retrieved {} products with branches", productsWithBranches.size());
            return productsWithBranches;
        } catch (Exception e) {
            log.error("Failed to retrieve products with branches. Error: {}", e.getMessage());
            throw e;
        }
    }

}

