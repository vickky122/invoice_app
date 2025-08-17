package com.example.invoice.controller;

import com.example.invoice.dto.InvoiceRequest;
import com.example.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/invoices/pdf")
    public ResponseEntity<byte[]> createInvoicePdf(@Valid @RequestBody InvoiceRequest request) {
        InvoiceService.PdfPayload payload = invoiceService.createInvoicePdf(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"invoice-" + payload.invoiceNumber + ".pdf\"");
        headers.setCacheControl("no-store");

        return new ResponseEntity<>(payload.pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\":\"UP\"}");
    }
}
