package com.hotelmanagement.hotel_management.services;

import org.springframework.stereotype.Service;
import com.hotelmanagement.hotel_management.data.Invoice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PdfGenerationService {

    public byte[] generateInvoicePdf(Invoice invoice) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - (2 * margin);
                float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
                float rowHeight = 20f;
                float cellMargin = 5f;

                String[][] content = {
                        {"Invoice Details:", ""},
                        {"Reservation Name:", invoice.getReservation().getGuest().getFirstName()},
                        {"Reservation Room:", invoice.getReservation().getRoom().getRoomClass()},
                        {"Amount:", "$" + invoice.getAmount()},
                        {"Issue Date:", invoice.getIssueDate().toString()}
                };

                drawTable(contentStream, yStart, tableWidth, rowHeight, cellMargin, content);

            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }

    private void drawTable(PDPageContentStream contentStream, float y, float tableWidth, float rowHeight, float cellMargin, String[][] content) throws IOException {
        float startX = 50;

        float tableY = y;
        for (String[] row : content) {
            float tableX = startX;
            for (String cell : row) {
                drawCell(contentStream, tableX, tableY, cellMargin, rowHeight, tableWidth / content[0].length, cell);
                tableX += tableWidth / content[0].length;
            }
            tableY -= rowHeight;
        }
    }

    private void drawCell(PDPageContentStream contentStream, float x, float y, float margin, float rowHeight, float cellWidth, String text) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        contentStream.setLineWidth(0.5f);
        contentStream.addRect(x, y, cellWidth, rowHeight);

        contentStream.beginText();
        contentStream.newLineAtOffset(x + margin, y + (rowHeight / 2) - 5);
        contentStream.showText(text);
        contentStream.endText();
    }
}


