package com.example.invoice.service;

import com.example.invoice.model.Invoice;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class PdfService {

    public byte[] generateInvoicePdf(Invoice invoice, byte[] qrPng) {
        try {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            // Header
            Paragraph title = new Paragraph("INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_LEFT);
            document.add(title);

            Paragraph meta = new Paragraph(
                    "Invoice No: " + invoice.getInvoiceNumber() + "\n" +
                            "Date: " + invoice.getTimestamp().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    textFont
            );
            meta.setSpacingBefore(5);
            meta.setSpacingAfter(10);
            document.add(meta);

            // Dealer & Customer
            PdfPTable parties = new PdfPTable(2);
            parties.setWidthPercentage(100);
            parties.setSpacingAfter(10);
            parties.setWidths(new float[]{1, 1});

            parties.addCell(makeCell("Dealer", labelFont, Element.ALIGN_LEFT, true));
            parties.addCell(makeCell("Customer", labelFont, Element.ALIGN_LEFT, true));

            String dealerBlock = invoice.getDealer().getName() + "\n" +
                    invoice.getDealer().getAddress() + "\n" +
                    "Email: " + invoice.getDealer().getEmail() + "\n" +
                    "Phone: " + invoice.getDealer().getPhone() + "\n" +
                    "Tax ID: " + invoice.getDealer().getTaxId();

            parties.addCell(makeCell(dealerBlock, textFont, Element.ALIGN_LEFT, false));
            parties.addCell(makeCell(invoice.getCustomerName(), textFont, Element.ALIGN_LEFT, false));
            document.add(parties);

            // Vehicle details table
            PdfPTable vehicleTable = new PdfPTable(2);
            vehicleTable.setWidthPercentage(100);
            vehicleTable.setSpacingBefore(5);
            vehicleTable.setSpacingAfter(10);
            vehicleTable.setWidths(new float[]{1, 2});

            vehicleTable.addCell(makeCell("Vehicle", labelFont, Element.ALIGN_LEFT, true));
            String vehicleText = invoice.getVehicle().getYear() + " " +
                    invoice.getVehicle().getMake() + " " +
                    invoice.getVehicle().getModel();
            vehicleTable.addCell(makeCell(vehicleText, textFont, Element.ALIGN_LEFT, false));

            vehicleTable.addCell(makeCell("VIN", labelFont, Element.ALIGN_LEFT, true));
            vehicleTable.addCell(makeCell(invoice.getVehicle().getVin(), textFont, Element.ALIGN_LEFT, false));

            NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
            String price = currency.format(invoice.getSubtotal().setScale(2, RoundingMode.HALF_UP));
            vehicleTable.addCell(makeCell("Price", labelFont, Element.ALIGN_LEFT, true));
            vehicleTable.addCell(makeCell(price, textFont, Element.ALIGN_LEFT, false));

            document.add(vehicleTable);

            // Summary
            PdfPTable summary = new PdfPTable(2);
            summary.setWidthPercentage(50);
            summary.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summary.setSpacingBefore(10);
            summary.setWidths(new float[]{1, 1});

            summary.addCell(makeCell("Subtotal", labelFont, Element.ALIGN_LEFT, true));
            summary.addCell(makeCell(currency.format(invoice.getSubtotal()), textFont, Element.ALIGN_RIGHT, false));

            summary.addCell(makeCell("Tax (10%)", labelFont, Element.ALIGN_LEFT, true));
            summary.addCell(makeCell(currency.format(invoice.getTax()), textFont, Element.ALIGN_RIGHT, false));

            summary.addCell(makeCell("Total", labelFont, Element.ALIGN_LEFT, true));
            summary.addCell(makeCell(currency.format(invoice.getTotal()), textFont, Element.ALIGN_RIGHT, false));

            document.add(summary);

            // QR code
            if (qrPng != null && qrPng.length > 0) {
                Image qr = Image.getInstance(qrPng);
                qr.scaleAbsolute(128, 128);
                qr.setAlignment(Image.ALIGN_RIGHT);
                qr.setSpacingBefore(15);
                document.add(qr);
            }

            // Footer
            Paragraph thanks = new Paragraph("Thank you for your business!", textFont);
            thanks.setSpacingBefore(15);
            document.add(thanks);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create PDF", e);
        }
    }

    private PdfPCell makeCell(String text, Font font, int align, boolean shaded) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(align);
        cell.setPadding(6);
        if (shaded) {
            cell.setGrayFill(0.92f);
        }
        return cell;
    }
}
