package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.services.InvoiceService;
import com.hotelmanagement.hotel_management.services.PdfGenerationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;
    private PdfGenerationService pdfGenerationService;

    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("invoices", invoiceService.getInvoices());
        return "invoices";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "invoice_id") int id) {
        invoiceService.delete(id);
        return "redirect:/invoices";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam int invoiceId, @RequestParam BigDecimal amount,
                       @RequestParam LocalDate issueDate) {
        invoiceService.edit(invoiceId, amount, issueDate);
        return "redirect:/invoices";
    }

    @GetMapping("/{invoiceId}/generate-pdf")
    @ResponseBody
    public ResponseEntity<byte[]> generatePdf(@PathVariable("invoiceId") Integer invoiceId) throws IOException {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);

        if (invoice == null) {
            // Handle case where invoice is not found
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = pdfGenerationService.generateInvoicePdf(invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "invoice.pdf");
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
