package com.wesco.assignment.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductWithBranch {

    private String productId;
    private String pname;
    private String pdesc;
    private BigDecimal unitPrice;
    private String branchId;
    private String branchName;
    private String branchLoc;
    private String zip;
    private Integer qtyOnHand;
}
