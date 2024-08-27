package com.wesco.assignment.service;

import com.wesco.assignment.model.Product;
import com.wesco.assignment.model.ProductWithBranch;
import com.wesco.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void addProduct(Product product){
        productRepository.addProduct(product);
    }

    public Product getProductById(Integer id){
        return productRepository.getProductById(id);
    }

    public List<Product> getAllProducts(){
        return productRepository.getAllProducts();
    }

    public void updateProduct(Product product,Integer id){
        productRepository.updateProduct(product,id);
    }

    public void deleteProduct(Integer id){
        productRepository.deleteProduct(id);
    }
    public List<ProductWithBranch> findAllProductsWithBranches() {
        return productRepository.findAllProductsWithBranches();
    }
}
