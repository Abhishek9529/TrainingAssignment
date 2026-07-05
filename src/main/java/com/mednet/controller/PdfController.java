package com.mednet.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    /**
     * Finds the node-pdf directory by searching up from the classpath location.
     * This works regardless of where Tomcat is running from.
     */
    private String findNodePdfDir() {
        // Strategy 1: Search up from classpath location
        String classesPath = PdfController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File current = new File(classesPath);
        while (current != null) {
            File nodePdfCheck = new File(current, "node-pdf");
            if (nodePdfCheck.exists() && nodePdfCheck.isDirectory()) {
                return nodePdfCheck.getAbsolutePath();
            }
            current = current.getParentFile();
        }
        return null;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generatePdf() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Find the node-pdf directory
            String nodePdfDir = findNodePdfDir();
            if (nodePdfDir == null) {
                response.put("success", false);
                response.put("message", "Could not find node-pdf directory. Please ensure it exists at the project root.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            // Build the command to run the Node.js script
            ProcessBuilder processBuilder = new ProcessBuilder("node", "generatePdf.js");
            processBuilder.directory(new File(nodePdfDir));
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait with 30 second timeout
            boolean finished = process.waitFor(30, java.util.concurrent.TimeUnit.SECONDS);

            if (!finished) {
                process.destroyForcibly();
                response.put("success", false);
                response.put("message", "PDF generation timed out after 30 seconds");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            int exitCode = process.exitValue();

            if (exitCode == 0) {
                // Return the PDF file directly
                File pdfFile = new File(nodePdfDir + File.separator + "output" + File.separator + "hello.pdf");
                if (pdfFile.exists()) {
                    FileSystemResource resource = new FileSystemResource(pdfFile);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"hello.pdf\"")
                            .contentType(MediaType.APPLICATION_PDF)
                            .contentLength(pdfFile.length())
                            .body(resource);
                } else {
                    response.put("success", false);
                    response.put("message", "PDF file not found after generation");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            } else {
                response.put("success", false);
                response.put("message", "PDF generation failed. Exit code: " + exitCode);
                response.put("error", output.toString());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
