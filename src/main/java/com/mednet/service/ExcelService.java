package com.mednet.service;

import com.mednet.entity.Prefix;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * ExcelService is responsible for generating Excel workbooks from database entities.
 * 
 * This service uses Apache POI to:
 * - Create new Excel workbooks (.xlsx format)
 * - Write data to sheets with formatted headers
 * - Export entity data to byte arrays for download
 * 
 * Why separate service?
 * - Follows Single Responsibility Principle (SRP)
 * - Excel generation logic is isolated and reusable
 * - Can be extended for other entities without modifying PrefixService
 * - Makes testing easier
 */
@Service
public class ExcelService {

    /**
     * Generates an Excel workbook containing Prefix data.
     * 
     * Process:
     * 1. Creates a new XSSFWorkbook (OOXML format for .xlsx)
     * 2. Creates a new sheet named "Prefixes"
     * 3. Writes header row with column names
     * 4. Iterates through prefix list and writes each row
     * 5. Auto-sizes columns for better readability
     * 6. Converts workbook to byte array for HTTP download
     * 
     * @param prefixes List of Prefix entities to export
     * @return Byte array representing the Excel file
     * @throws IOException If workbook creation or conversion fails
     */
    public byte[] generatePrefixExcel(List<Prefix> prefixes) throws IOException {
        // Create a new workbook in OOXML format (.xlsx)
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        // Create a new sheet named "Prefixes"
        Sheet sheet = workbook.createSheet("Prefixes");
        
        // Create header row with formatting
        Row headerRow = sheet.createRow(0);
        
        // Create cell style for header (bold, centered background)
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        // Define column headers
        String[] headers = {"Prefix", "Gender", "Prefix Of"};
        
        // Write headers to the first row
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Write data rows
        int rowNum = 1;
        for (Prefix prefix : prefixes) {
            Row row = sheet.createRow(rowNum++);
            
            // Column 0: Prefix
            row.createCell(0).setCellValue(prefix.getPrefix() != null ? prefix.getPrefix() : "");
            
            // Column 1: Gender
            row.createCell(1).setCellValue(prefix.getGender() != null ? prefix.getGender() : "");
            
            // Column 2: Prefix Of
            row.createCell(2).setCellValue(prefix.getPrefixOf() != null ? prefix.getPrefixOf() : "");
        }
        
        // Auto-size columns for better readability
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // Convert workbook to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        return outputStream.toByteArray();
    }
    
    /**
     * Creates a header cell style with bold font and light gray background.
     * 
     * @param workbook XSSFWorkbook to create the style from
     * @return CellStyle configured for headers
     */
    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Create bold font
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        
        // Set light gray background color using RGB
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // Center align
        style.setAlignment(HorizontalAlignment.CENTER);
        
        return style;
    }
}
