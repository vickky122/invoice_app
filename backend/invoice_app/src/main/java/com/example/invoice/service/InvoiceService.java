package com.example.invoice.service;

import com.example.invoice.dto.InvoiceRequest;
import com.example.invoice.model.Dealer;
import com.example.invoice.model.Invoice;
import com.example.invoice.model.Vehicle;
import com.example.invoice.repo.DealerRepository;
import com.example.invoice.repo.VehicleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@Service
public class InvoiceService {

    private final DealerRepository dealerRepository;
    private final VehicleRepository vehicleRepository;
    private final QrService qrService;
    private final PdfService pdfService;

    public static class PdfPayload {
        public final byte[] pdfBytes;
        public final String invoiceNumber;
        public PdfPayload(byte[] pdfBytes, String invoiceNumber) {
            this.pdfBytes = pdfBytes;
            this.invoiceNumber = invoiceNumber;
        }
    }

    public InvoiceService(DealerRepository dealerRepository,
                          VehicleRepository vehicleRepository,
                          QrService qrService,
                          PdfService pdfService) {
        this.dealerRepository = dealerRepository;
        this.vehicleRepository = vehicleRepository;
        this.qrService = qrService;
        this.pdfService = pdfService;
    }

    public PdfPayload createInvoicePdf(InvoiceRequest request) {
        Dealer dealer = dealerRepository.findById(request.getDealerId())
                .orElseThrow(() -> new NotFoundException("Dealer not found: " + request.getDealerId()));
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found: " + request.getVehicleId()));

        BigDecimal subtotal = vehicle.getBasePrice().setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);

        Invoice invoice = new Invoice();
        String invoiceNumber = generateInvoiceNumber();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        invoice.setDealer(dealer);
        invoice.setVehicle(vehicle);
        invoice.setCustomerName(request.getCustomerName());
        invoice.setSubtotal(subtotal);
        invoice.setTax(tax);
        invoice.setTotal(total);
        String transactionId = UUID.randomUUID().toString();
        invoice.setTransactionId(transactionId);

        String qrContent = String.format(Locale.ROOT,
                "{\"transactionId\":\"%s\",\"invoice\":\"%s\",\"total\":\"%s\"}",
                transactionId, invoiceNumber, total.toPlainString());

        byte[] qrPng = qrService.generateQrPng(qrContent, 256);
        byte[] pdf = pdfService.generateInvoicePdf(invoice, qrPng);

        return new PdfPayload(pdf, invoiceNumber);
    }

    private String generateInvoiceNumber() {
        String date = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String suffix = randomAlphaNum(6).toUpperCase(Locale.ROOT);
        return "INV-" + date + "-" + suffix;
    }

    private String randomAlphaNum(int len) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String msg) { super(msg); }
    }
}
