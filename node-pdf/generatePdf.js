const puppeteer = require('puppeteer');
const path = require('path');
const fs = require('fs');

async function generatePdf() {
    // Ensure output directory exists
    const outputDir = path.join(__dirname, 'output');
    if (!fs.existsSync(outputDir)) {
        fs.mkdirSync(outputDir, { recursive: true });
    }

    console.log('Launching browser...');
    const browser = await puppeteer.launch({
        headless: 'new',
        args: ['--no-sandbox', '--disable-setuid-sandbox']
    });

    try {
        const page = await browser.newPage();

        // Get the path to hello.html
        const htmlPath = path.join(__dirname, 'hello.html');
        console.log('Opening HTML file:', htmlPath);

        // Navigate to the local HTML file
        await page.goto(`file://${htmlPath}`, { waitUntil: 'networkidle0' });

        // Generate PDF
        const outputPath = path.join(outputDir, 'hello.pdf');
        console.log('Generating PDF...');

        await page.pdf({
            path: outputPath,
            format: 'A4',
            printBackground: true,
            margin: {
                top: '20px',
                right: '20px',
                bottom: '20px',
                left: '20px'
            }
        });

        console.log('✓ PDF generated successfully!');
        console.log('  Output location:', outputPath);
    } catch (error) {
        console.error('Error generating PDF:', error);
        process.exit(1);
    } finally {
        await browser.close();
        console.log('Browser closed.');
    }
}

// Run the function
generatePdf();
