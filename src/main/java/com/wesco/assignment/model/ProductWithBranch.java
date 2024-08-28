package com.wesco.assignment.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductWithBranch extends Product{
    private String branchId;
    private String branchName;
    private String branchLoc;
    private String zip;
    private Integer qtyOnHand;
}
