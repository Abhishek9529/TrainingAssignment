package com.mednet.controller;

import com.mednet.entity.Prefix;
import com.mednet.service.ExcelService;
import com.mednet.service.PrefixService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private PrefixService prefixService;

    @Autowired
    private ExcelService excelService;


    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPrefixExcel() {
        try {
            // Fetch all Prefix records from database
            var prefixes = prefixService.getAllPrefixes();
            
            // Generate Excel workbook from Prefix list
            byte[] excelFile = excelService.generatePrefixExcel(prefixes);
            
            // Create HTTP headers for file download
            HttpHeaders headers = new HttpHeaders();
            
            // Set Content-Type to XLSX MIME type
            headers.setContentType(org.springframework.http.MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            
            // Create filename with timestamp for uniqueness
            String filename = "Prefixes_" + System.currentTimeMillis() + ".xlsx";
            
            // Set Content-Disposition header to trigger download
            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename(filename)
                            .build()
            );
            
            // Return file as response with 200 OK status
            return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            // Log error for debugging
            e.printStackTrace();
            
            // Return 500 Internal Server Error if Excel generation fails
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/template")
    public ResponseEntity<byte[]> downloadPrefixTemplate() {
       try {
           // Generate empty template workbook with headers only
           byte[] templateFile = excelService.generatePrefixTemplate();
            
           // Create HTTP headers for file download
           HttpHeaders headers = new HttpHeaders();
            
           // Set Content-Type to XLSX MIME type
           headers.setContentType(org.springframework.http.MediaType.parseMediaType(
                   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            
           // Use fixed filename as per requirements
           String filename = "prefix_template.xlsx";
            
           // Set Content-Disposition header to trigger download
           headers.setContentDisposition(
                   ContentDisposition.attachment()
                           .filename(filename)
                           .build()
           );
            
           // Return template file as response with 200 OK status
           return new ResponseEntity<>(templateFile, headers, HttpStatus.OK);
            
       } catch (IOException e) {
           // Log error for debugging
           e.printStackTrace();
            
           // Return 500 Internal Server Error if template generation fails
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }


    @PostMapping("/upload")
    public String uploadPrefixExcel(@RequestParam("excelFile") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        
       // ====== STEP 1: FILE VALIDATION ======
        
       // Validate that file is present (user selected a file)
       if (file.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", 
                   "Please select an Excel file to upload.");
           return "redirect:/home";
       }
        
       // Get the original filename from the uploaded file
       String filename = file.getOriginalFilename();
        
       // Defensive check for null or empty filename
       if (filename == null || filename.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", 
                   "Invalid file. Please select a valid Excel file.");
           return "redirect:/home";
       }
        
       // Validate file extension is .xlsx
       // Extract extension: "prefixes.xlsx" → "xlsx"
       String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        
       // Only accept .xlsx format (Excel 2007+ format required by Apache POI XSSFWorkbook)
       if (!fileExtension.equals("xlsx")) {
           redirectAttributes.addFlashAttribute("errorMessage", 
                   "Invalid file format. Please upload an Excel file (.xlsx format).");
           return "redirect:/home";
       }
        
       // ====== STEP 2: OPEN EXCEL FILE USING APACHE POI ======
        
       try {
           // Create XSSFWorkbook from the uploaded file's InputStream
           // XSSFWorkbook = workbook for Excel 2007+ (.xlsx) format
           XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            
           // Get the first sheet (index 0)
           // Excel files can have multiple sheets, but requirement is to read only the first one
           Sheet sheet = workbook.getSheetAt(0);
            
           // ====== STEP 3: READ AND PROCESS DATA ROWS ======
            
           // Counter for tracking inserted records
           int recordsInserted = 0;
            
           // Print header to console for readability
           System.out.println("\n========== IMPORTING EXCEL DATA ==========");
           System.out.println("Reading data from sheet: " + sheet.getSheetName());
           System.out.println("Total rows in sheet: " + sheet.getPhysicalNumberOfRows());
           System.out.println("----------------------------------------");
            
           // Iterate through all rows in the sheet
           // Start from row 1 to skip the header row (row 0)
           for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                
               // Get the current row
               Row row = sheet.getRow(rowIndex);
                
               // Check if row exists (rows might be null if they are empty in the middle)
               // SKIP: null rows (empty in the middle of data)
               if (row == null) {
                   continue;
               }
                
               // ====== EXTRACT COLUMN VALUES ======
                
               // Column 0: Prefix (REQUIRED)
               Cell prefixCell = row.getCell(0);
               String prefix = extractCellValueAsString(prefixCell);
                
               // Column 1: Gender (optional)
               Cell genderCell = row.getCell(1);
               String gender = extractCellValueAsString(genderCell);
                
               // Column 2: Prefix Of (optional)
               Cell prefixOfCell = row.getCell(2);
               String prefixOf = extractCellValueAsString(prefixOfCell);
                
               // ====== VALIDATION: CHECK IF ROW IS COMPLETELY EMPTY ======
                
               // If all three columns are empty, skip this row
               if (prefix.trim().isEmpty() && gender.trim().isEmpty() && prefixOf.trim().isEmpty()) {
                   System.out.println("Row " + (rowIndex + 1) + ": SKIPPED (completely empty)");
                   continue;
               }
                
               // ====== VALIDATION: PREFIX FIELD IS REQUIRED ======
                
               // If Prefix column is empty, skip this row (Prefix is required)
               // Prefix is the main identifier, cannot be empty
               if (prefix.trim().isEmpty()) {
                   System.out.println("Row " + (rowIndex + 1) + ": SKIPPED (Prefix is required)");
                   continue;
               }
                
               // ====== CREATE PREFIX OBJECT ======
                
               // Create a new Prefix entity object
               // Note: ID is not set (auto-generated by @GeneratedValue)
               Prefix prefixObj = new Prefix();
                
               // Set the three properties from Excel data
               prefixObj.setPrefix(prefix.trim());           // Trim whitespace
               prefixObj.setGender(gender.trim());           // Trim whitespace
               prefixObj.setPrefixOf(prefixOf.trim());        // Trim whitespace
                
               // ====== SAVE TO DATABASE ======
                
               // Call PrefixService to save the object
               // Service delegates to PrefixDao which uses Hibernate for persistence
               // Hibernate Session manages transaction and database connection
               try {
                   prefixService.savePrefix(prefixObj);
                   recordsInserted++;
                    
                   // Print successful save to console
                   System.out.println("Row " + (rowIndex + 1) + ": INSERTED");
                   System.out.println("  Prefix = " + prefix);
                   System.out.println("  Gender = " + gender);
                   System.out.println("  Prefix Of = " + prefixOf);
                    
               } catch (Exception e) {
                   // If save fails for this row, log error and continue to next row
                   System.err.println("Row " + (rowIndex + 1) + ": ERROR - " + e.getMessage());
                   e.printStackTrace();
               }
                
               System.out.println("----------------------------------------");
           }
            
           // ====== CLOSE WORKBOOK AND PREPARE RESPONSE ======
            
           // Close the workbook to release file resources
           workbook.close();
            
           // Print summary to console
           System.out.println("========== IMPORT COMPLETE ==========");
           System.out.println("Total records inserted: " + recordsInserted);
           System.out.println("========== END OF IMPORT ==========\n");
            
           // ====== REDIRECT WITH SUCCESS MESSAGE ======
            
           // Add success message to flash attributes
           redirectAttributes.addFlashAttribute("successMessage", 
                   "Excel imported successfully.");
            
           // Redirect back to home page
           // Prefix table will automatically refresh and show new records
           return "redirect:/";
            
       } catch (IOException e) {
           // Handle file read errors or invalid file format
           System.err.println("Error reading Excel file: " + e.getMessage());
           e.printStackTrace();
            
           // Add error message for user
           redirectAttributes.addFlashAttribute("errorMessage", 
                   "Error reading Excel file. Please ensure it is a valid .xlsx file.");
            
           // Redirect back to home page to display error
           return "redirect:/home";
       }
    }
    
    /**
    * Extracts cell value as a String, handling different cell types.
    * 
    * This helper method:
    * 1. Checks if cell is null (cell doesn't exist in row)
    * 2. Handles different cell types:
    *    - STRING: Returns the string value directly
    *    - NUMERIC: Converts number to string
    *    - BLANK: Returns empty string
    *    - Other types: Uses default toString() format
    * 3. Returns clean value without extra whitespace
    * 
    * Why this is needed:
    * - Excel cells can have different data types (String, Number, Date, etc.)
    * - Apache POI requires type checking before extracting values
    * - Direct casting without type checking causes ClassCastException
    * 
    * @param cell The Excel cell to extract value from
    * @return String representation of cell value, or empty string if null/blank
    */
    private String extractCellValueAsString(Cell cell) {
       // If cell is null, return empty string
       // This happens when a column doesn't have data in that row
       if (cell == null) {
           return "";
       }
        
       // Get the cell type to determine how to extract the value
       CellType cellType = cell.getCellType();
        
       // Handle different cell types
       switch (cellType) {
           case STRING:
               // Cell contains string data - get it directly
               return cell.getStringCellValue();
                
           case NUMERIC:
               // Cell contains numeric data (number, date, etc.)
               // Convert to string and remove decimal point if it's a whole number
               double numValue = cell.getNumericCellValue();
               // Check if number is a whole number
               if (numValue == Math.floor(numValue)) {
                   // Remove decimal point for whole numbers
                   return String.valueOf((long) numValue);
               } else {
                   // Keep decimal for floating point numbers
                   return String.valueOf(numValue);
               }
                
           case BLANK:
               // Cell is intentionally blank - return empty string
               return "";
                
           case BOOLEAN:
               // Cell contains boolean value - convert to string
               return String.valueOf(cell.getBooleanCellValue());
                
           default:
               // Other cell types (FORMULA, ERROR, etc.)
               // Try to get the string value
               return cell.getStringCellValue();
       }
    }
}
