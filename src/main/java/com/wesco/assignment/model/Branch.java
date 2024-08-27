package com.wesco.assignment.model;

import lombok.Data;

@Data
public class Branch {
    private Integer id;
    private String branchId;
    private String branchName;
    private String branchLoc;
    private String zip;
    private String productId;
    private Long qtyOnHand ;
}
