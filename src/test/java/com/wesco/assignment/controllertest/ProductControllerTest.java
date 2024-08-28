package com.wesco.assignment.controllertest;

import com.wesco.assignment.controller.ProductController;
import com.wesco.assignment.model.Product;
import com.wesco.assignment.model.ProductWithBranch;
import com.wesco.assignment.service.ExcelReportService;
import org.junit.jupiter.api.Test;
import com.wesco.assignment.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExcelReportService excelReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = loadProductFromJson("product.json");

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.post("/api/products/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product added successfully"))
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo("Product added successfully");
    }

    private Product loadProductFromJson(String filename) throws IOException {
        return objectMapper.readValue(Files.readString(Paths.get("src/test/resources/" + filename)), Product.class);
    }

    private List<Product> loadProductsFromJson(String filename) throws IOException {
        return objectMapper.readValue(Files.readString(Paths.get("src/test/resources/" + filename)),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
    }

    private List<ProductWithBranch> loadProductsWithBranchesFromJson(String filename) throws IOException {
        return objectMapper.readValue(Files.readString(Paths.get("src/test/resources/" + filename)),
                objectMapper.getTypeFactory().constructCollectionType(List.class, ProductWithBranch.class));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = loadProductFromJson("product.json");
        when(productService.getProductById(1)).thenReturn(product);
        String responseBody = mockMvc.perform(get("/api/products/getProduct/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)))
                .andReturn().getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(product));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = loadProductsFromJson("products.json");
        when(productService.getAllProducts()).thenReturn(products);
        String responseBody = mockMvc.perform(get("/api/products/getAllProducts"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)))
                .andReturn().getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(products));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = loadProductFromJson("product.json");
        String responseBody = mockMvc.perform(put("/api/products/updateProduct", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully updated the product"))
                .andReturn().getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo("Successfully updated the product");
    }

    @Test
    public void testDeleteProduct() throws Exception {
        String responseBody = mockMvc.perform(delete("/api/products/deleteProduct/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted the product"))
                .andReturn().getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo("Successfully deleted the product");
    }

    @Test
    public void testFindAllProductsWithBranches() throws Exception {
        List<ProductWithBranch> productsWithBranches = loadProductsWithBranchesFromJson("productWithBranch.json");
        when(productService.findAllProductsWithBranches()).thenReturn(productsWithBranches);
        String responseBody = mockMvc.perform(get("/api/products/getAllProductsWithBranches"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productsWithBranches)))
                .andReturn().getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(productsWithBranches));
    }

}
