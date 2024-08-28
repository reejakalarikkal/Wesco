package com.wesco.assignment.servicetest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesco.assignment.model.Product;
import com.wesco.assignment.model.ProductWithBranch;
import com.wesco.assignment.repository.ProductRepository;
import com.wesco.assignment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Product loadProductFromJson(String filename) throws IOException {
        return objectMapper.readValue(Files.readString(Paths.get("src/test/resources/" + filename)), Product.class);
    }

    private List<Product> loadProductsFromJson(String filename) throws IOException {
        return objectMapper.readValue(Files.readString(Paths.get("src/test/resources/" + filename)),
                new TypeReference<List<Product>>() {});
    }

    private List<ProductWithBranch> loadProductsWithBranchesFromJson(String filename) throws IOException {
        return objectMapper.readValue(Files.readString(Paths.get("src/test/resources/" + filename)),
                new TypeReference<List<ProductWithBranch>>() {});
    }

    @Test
    public void testAddProduct() throws IOException {
        Product product = loadProductFromJson("product.json");
        productService.addProduct(product);
        verify(productRepository, times(1)).addProduct(product);
    }

    @Test
    public void testGetProductById() throws IOException {
        Product expectedProduct = loadProductFromJson("product.json");
        when(productRepository.getProductById(1)).thenReturn(expectedProduct);
        Product result = productService.getProductById(1);
        assertThat(result).isEqualTo(expectedProduct);
    }

    @Test
    public void testGetAllProducts() throws IOException {
        List<Product> expectedProducts = loadProductsFromJson("products.json");
        when(productRepository.getAllProducts()).thenReturn(expectedProducts);
        List<Product> result = productService.getAllProducts();
        assertThat(result).isEqualTo(expectedProducts);
    }

    @Test
    public void testUpdateProduct() throws IOException {
        Product product = loadProductFromJson("product.json");
        productService.updateProduct(product);
        verify(productRepository, times(1)).updateProduct(product);
    }

    @Test
    public void testDeleteProduct() {
        int productId = 1;
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteProduct(productId);
    }

    @Test
    public void testFindAllProductsWithBranches() throws IOException {
        List<ProductWithBranch> expectedProductsWithBranches = loadProductsWithBranchesFromJson("productWithBranch.json");
        when(productRepository.findAllProductsWithBranches()).thenReturn(expectedProductsWithBranches);
        List<ProductWithBranch> result = productService.findAllProductsWithBranches();
        assertThat(result).isEqualTo(expectedProductsWithBranches);
    }
}
