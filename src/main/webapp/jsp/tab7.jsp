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

            // Call backend endpoint to generate PDF
            fetch('<%= request.getContextPath() %>/api/pdf/generate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(function(response) {
                if (!response.ok) {
                    return response.json().then(function(data) {
                        throw new Error(data.message || 'Failed to generate PDF');
                    });
                }
                // PDF returned as binary - trigger download
                return response.blob().then(function(blob) {
                    var url = window.URL.createObjectURL(blob);
                    var a = document.createElement('a');
                    a.href = url;
                    a.download = 'hello.pdf';
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);
                    window.URL.revokeObjectURL(url);
                    statusField.value = 'PDF generated successfully and download started!';
                });
            })
            .catch(function(error) {
                statusField.value = 'Error: ' + error.message;
            })
            .finally(function() {
                btn.disabled = false;
                btn.style.background = '#0b72d9';
            });
        }
    </script>

</div>
