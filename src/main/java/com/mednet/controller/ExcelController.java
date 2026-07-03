package com.mednet.controller;

import com.mednet.service.ExcelService;
import com.mednet.service.PrefixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * ExcelController handles all Excel download operations.
 * 
 * This controller:
 * - Provides REST endpoints for Excel file downloads
 * - Orchestrates between UI requests and service layer
 * - Sets proper HTTP headers for file download (Content-Disposition, Content-Type)
 * - Returns Excel file as byte array with appropriate MIME type
 * 
 * Why separate controller?
 * - Dedicated endpoint management for Excel operations
 * - Keeps Excel-specific HTTP handling separate from business logic (PrefixController)
 * - Can be extended for other Excel operations (upload, template, etc.)
 * - Follows REST principles for file operations
 * 
 * Note: This controller only handles DOWNLOAD. Upload and Template Download 
 * are NOT implemented as per requirements.
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private PrefixService prefixService;

    @Autowired
    private ExcelService excelService;

    /**
     * Downloads all Prefix records as an Excel file (.xlsx).
     * 
     * Process:
     * 1. Fetch all Prefix records from database via PrefixService
     * 2. Generate Excel workbook via ExcelService
     * 3. Set HTTP headers for browser download:
     *    - Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
     *      (MIME type for .xlsx files)
     *    - Content-Disposition: attachment; filename="Prefixes_[timestamp].xlsx"
     *      (Tells browser to download as file with given name)
     * 4. Return byte array with 200 OK status
     * 
     * HTTP Response:
     * - Status: 200 OK
     * - Body: Binary Excel file
     * - Filename: Prefixes_[YYYYMMDD_HHmmss].xlsx
     * 
     * Exception Handling:
     * - IOException: Returns 500 INTERNAL_SERVER_ERROR if Excel generation fails
     * 
     * @return ResponseEntity with Excel file as bytes and appropriate headers
     */
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
}
