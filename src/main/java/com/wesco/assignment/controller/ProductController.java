package com.wesco.assignment.controller;

import com.wesco.assignment.model.Product;
import com.wesco.assignment.model.ProductWithBranch;
import com.wesco.assignment.service.ExcelReportService;
import com.wesco.assignment.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ExcelReportService excelReportService;

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id){
        Product product=productService.getProductById(id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return new ResponseEntity<>("Successfully updated the product",HttpStatus.OK);

    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Successfully deleted the product",HttpStatus.OK);
    }

    @GetMapping("/getAllProductsWithBranches")
    public ResponseEntity<List<ProductWithBranch>> findAllProductsWithBranches() {
        List<ProductWithBranch> products=productService.findAllProductsWithBranches();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/export")
    public ResponseEntity<ByteArrayResource> exportProductsToExcel() throws IOException {
        List<ProductWithBranch> productWithBranches = productService.findAllProductsWithBranches();

        ByteArrayResource resource = excelReportService.generateProductReport(productWithBranches);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products_with_branches.xlsx")
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
