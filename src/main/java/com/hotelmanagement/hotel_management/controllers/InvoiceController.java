package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.data.Invoice;
import com.hotelmanagement.hotel_management.data.Reservation;
import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.InvoiceService;
import com.hotelmanagement.hotel_management.services.PdfGenerationService;
import com.hotelmanagement.hotel_management.services.ReservationService;
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
import java.time.Period;

@Controller
@AllArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;
    private PdfGenerationService pdfGenerationService;
    private GuestService guestService;
    private ReservationService reservationService;

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

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("guests", guestService.getGuests());
        model.addAttribute("reservations", reservationService.getReservations());
        return "add_invoice_form";
    }

    @PostMapping("/add")
    public String addInvoice(@RequestParam int guestId, @RequestParam int reservationId) {
        BigDecimal amount = calculateInvoiceAmount(reservationId);
        LocalDate issueDate = LocalDate.now();
        invoiceService.add(guestId, reservationId, amount, issueDate);
        return "redirect:/invoices";
    }

    public BigDecimal calculateInvoiceAmount(int reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();

        Period period = Period.between(startDate, endDate);
        int totalDays = period.getDays() + period.getMonths() * 30 + period.getYears() * 365;

        BigDecimal amountPerDayPerPerson = BigDecimal.valueOf(50); // Вартість за день проживання на одну особу

        int numberOfGuests = reservation.getRoom().getCapacity(); // Кількість гостей у номері

        return amountPerDayPerPerson.multiply(BigDecimal.valueOf(totalDays)).multiply(BigDecimal.valueOf(numberOfGuests));
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
