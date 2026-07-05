<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:20px;">

    <h2>Sample PDF Generation using Puppeteer</h2>

    <p style="margin-bottom:20px; color:#555; font-size:14px;">
        Generate a sample Hello World PDF using Puppeteer.
    </p>

    <!-- Generate PDF Button -->
    <button id="generatePdfBtn"
            onclick="generatePdf()"
            style="padding:8px 20px;
                   background:#0b72d9;
                   color:white;
                   border:none;
                   cursor:pointer;
                   font-size:14px;
                   border-radius:3px;">
        Generate PDF
    </button>

    <!-- Status Display -->
    <div style="margin-top:15px;">
        <label style="display:block; margin-bottom:5px; font-weight:bold; font-size:13px;">Status:</label>
        <input type="text"
               id="pdfStatus"
               readonly
               value="Ready"
               style="width:400px;
                      padding:8px 10px;
                      font-size:13px;
                      border:1px solid #ccc;
                      background:#f9f9f9;
                      color:#333;
                      border-radius:3px;" />
    </div>

    <!-- JavaScript for PDF generation trigger -->
    <script>
        function generatePdf() {
            var statusField = document.getElementById('pdfStatus');
            var btn = document.getElementById('generatePdfBtn');

            // Disable button and show generating status
            btn.disabled = true;
            btn.style.background = '#999';
            statusField.value = 'Generating PDF...';

            // Simulate PDF generation process
            // In production, this would call a backend endpoint that runs the Node.js script
            setTimeout(function() {
                statusField.value = 'PDF generated successfully! Output: node-pdf/output/hello.pdf';
                btn.disabled = false;
                btn.style.background = '#0b72d9';
            }, 2000);
        }
    </script>

</div>
