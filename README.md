# PDF Auto-Fill Invoice (Spring Boot)

Generate professional vehicle sale invoices as downloadable PDFs. Includes dealer and vehicle details, customer name, 10% tax, total price, invoice number with timestamp, and a QR code containing the transaction ID.

---

## ✨ Features
- **API**: `POST /api/v1/invoices/pdf` returns a PDF file.
- 

- **Health**: `GET /api/v1/health` for quick status checks.
- **PDF Contents**: dealer, vehicle, customer, 10% tax, total price, invoice number, timestamp, QR with transactionId.
- **Minimal frontend**: `index.html` served by Spring Boot for one-click PDF download.
- **Live demo hosted on GitHub Codespaces** (HTTPS public URL).

---

## 🚀 Tech Stack
- Java 21, Spring Boot 3.5.x  
- Maven 3.9+  
- OpenPDF (PDF creation)  
- ZXing (QR code)

---

## 🌐 Public Demo (Codespaces)
- **Public URL**: `https://cautious-waffle-r47rrvgg5ghx6wg-8081.app.github.dev/`  
- **Health**: `https://cautious-waffle-r47rrvgg5ghx6wg-8081.app.github.dev/api/v1/health`  
- **UI**: `https://cautious-waffle-r47rrvgg5ghx6wg-8081.app.github.dev/` (Generate & Download PDF button)

> ⚠️ If an external client gets `401` via the Codespaces proxy on POST, the included `index.html` works reliably because it’s same-origin.

---

## 🛠️ Quick Start (Local)

### Prerequisites
- Java 21  
- Maven 3.9+  

### Build
```bash
mvn -DskipTests clean package
Run
bash
Copy
Edit
java -jar target/invoice_app-0.0.1-SNAPSHOT.jar


 📡 Endpoints
Health: http://localhost:8081/api/v1/health

PDF:

URL: POST http://localhost:8081/api/v1/invoices/pdf

Headers: Content-Type: application/json

Body:

json
Copy
Edit
{
  "dealerId": "D001",
  "vehicleId": "V101",
  "customerName": "John Doe"
}
💻 Frontend (Static)
Open: http://localhost:8081/

Use the form to download invoice.pdf.

📂 Project Structure
bash
Copy
Edit
backend/invoice_app
├── src/main/java/...       # Spring Boot source
├── src/main/resources/static/index.html   # UI to generate/download PDF
├── pom.xml
├── samples/                # generated sample PDFs for submission
└── screenshots/            # health/UI/Postman/Ports screenshots
🧾 Generate a Sample PDF (CLI)
mkdir -p samples
curl -X POST http://localhost:8081/api/v1/invoices/pdf \
  -H "Content-Type: application/json" \
  -d '{"dealerId":"D001","vehicleId":"V101","customerName":"John Doe"}' \
  --output samples/invoice-sample.pdf
📸 Screenshots (recommended for submission)
screenshots/health-public.png (Health endpoint at the public URL)
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/0ccc7f38-3acc-432f-82cf-923019c07d47" />


screenshots/ui-download.png (UI after a successful download)
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/57b11bc1-273c-4bc4-ae09-9ce120ece09b" />


screenshots/postman-pdf.png (Postman response headers showing PDF)
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/9e1292a1-8d86-48e2-851c-198815ecb2f1" />


screenshots/codespaces-ports.png (Codespaces Ports panel with 8081 set to Public)
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/b751fe2e-0b32-4c54-9047-b91c422bb16c" />


🐞 Troubleshooting
Static index.html not loading

Ensure the path is: src/main/resources/static/index.html (lowercase).

Rebuild: mvn -DskipTests clean package

Verify: ls target/classes/static/index.html

For hot-reload during dev: mvn spring-boot:run
