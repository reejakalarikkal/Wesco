package com.wesco.assignment.service;

import com.wesco.assignment.model.ProductWithBranch;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelReportService {

    public ByteArrayResource generateProductReport(List<ProductWithBranch> productWithBranches) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Products");

            // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Product ID");
            header.createCell(1).setCellValue("Product Name");
            header.createCell(2).setCellValue("Description");
            header.createCell(3).setCellValue("Unit Price");
            header.createCell(4).setCellValue("Branch ID");
            header.createCell(5).setCellValue("Branch Name");
            header.createCell(6).setCellValue("Branch Location");
            header.createCell(7).setCellValue("ZIP");
            header.createCell(8).setCellValue("Quantity on Hand");

            // Fill data
            int rowNum = 1;
            for (ProductWithBranch productWithBranch : productWithBranches) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(productWithBranch.getProductId());
                row.createCell(1).setCellValue(productWithBranch.getPname());
                row.createCell(2).setCellValue(productWithBranch.getPdesc());
                row.createCell(3).setCellValue(productWithBranch.getUnitPrice().toString());
                row.createCell(4).setCellValue(productWithBranch.getBranchId());
                row.createCell(5).setCellValue(productWithBranch.getBranchName());
                row.createCell(6).setCellValue(productWithBranch.getBranchLoc());
                row.createCell(7).setCellValue(productWithBranch.getZip());
                row.createCell(8).setCellValue(productWithBranch.getQtyOnHand());
            }

            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel report", e);
        }

    }
}
