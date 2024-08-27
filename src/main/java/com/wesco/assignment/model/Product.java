package com.wesco.assignment.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Integer id;
    private String productId;
    private String pname;
    private String pdesc;
    private BigDecimal unitPrice;
}
