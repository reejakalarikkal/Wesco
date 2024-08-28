package com.wesco.assignment.repository;

import com.wesco.assignment.model.Product;
import com.wesco.assignment.model.ProductWithBranch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addProduct(Product product){
        String sql="INSERT INTO t_product (product_id,pname, pdesc, unit_price) VALUES (? ,?, ?, ? )";
        jdbcTemplate.update(sql,product.getProductId(),product.getPname(),product.getPdesc(),product.getUnitPrice());
        log.info("Product added successfully: {}", product);
    }

    public Product getProductById(Integer id){
        String sql="SELECT * FROM t_product  WHERE id = ? ";
        Product product= jdbcTemplate.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(Product.class));
        log.info("Product retrieved successfully: {}", product);
        return product;

    }

    public List<Product> getAllProducts(){
        String sql = "SELECT * FROM t_product";
        List<Product> products= jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
        log.info("Retrieved {} products", products.size());
        return products;
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE t_product SET pname = ?, pdesc = ?, unit_price = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getPname(), product.getPdesc(), product.getUnitPrice(), product.getId());
        log.info("Product updated successfully: {}", product);
    }

    public void deleteProduct(Integer id) {
        String sql = "DELETE FROM t_product WHERE id = ?";
        jdbcTemplate.update(sql, id);
        log.info("Product deleted successfully with id: {}", id);
    }

    public List<ProductWithBranch> findAllProductsWithBranches() {
        String sql = "SELECT p.*, b.* FROM t_product p " +
                "LEFT JOIN t_branch b ON p.product_id = b.product_id";
        List<ProductWithBranch> productsWithBranches= jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductWithBranch.class));
        log.info("Retrieved {} products with branches", productsWithBranches.size());
        return productsWithBranches;
    }

    /* public static class ProductWithBranchesRowMapper implements RowMapper<ProductWithBranch> {

        @Override
        public ProductWithBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductWithBranch productWithBranches = new ProductWithBranch();
            productWithBranches.setProductId(rs.getString("product_id"));
            productWithBranches.setPname(rs.getString("pname"));
            productWithBranches.setPdesc(rs.getString("pdesc"));
            productWithBranches.setUnitPrice(rs.getBigDecimal("unit_price"));
            productWithBranches.setBranchId(rs.getString("branch_id"));
            productWithBranches.setBranchName(rs.getString("branch_name"));
            productWithBranches.setBranchLoc(rs.getString("branch_loc"));
            productWithBranches.setZip(rs.getString("zip"));
            productWithBranches.setQtyOnHand(rs.getInt("qty_on_hand"));
            return productWithBranches;
        }*/
    }

