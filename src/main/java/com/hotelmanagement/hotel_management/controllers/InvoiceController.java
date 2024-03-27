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

/**
 * Controller class to handle operations related to invoices.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;
    private PdfGenerationService pdfGenerationService;
    private GuestService guestService;
    private ReservationService reservationService;

    /**
     * Retrieves all invoices and displays them.
     *
     * @param model The model to add attributes to for rendering the view.
     * @return The name of the view template to render.
     */
    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("invoices", invoiceService.getInvoices());
        return "invoices";
    }

    /**
     * Displays the invoice for a specific reservation.
     *
     * @param reservationId The ID of the reservation.
     * @param model         The model to add attributes to for rendering the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/by-reservation/{reservationId}")
    public String showInvoiceForReservation(@PathVariable("reservationId") int reservationId, Model model) {
        Invoice invoice = invoiceService.getInvoiceByReservationId(reservationId);
        model.addAttribute("invoices", invoice);
        return "guest_reservation_invoice";
    }

    /**
     * Deletes an invoice by ID.
     *
     * @param id The ID of the invoice to delete.
     * @return A redirect to the invoices page.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "invoice_id") int id) {
        invoiceService.delete(id);
        return "redirect:/invoices";
    }

    /**
     * Edits an invoice.
     *
     * @param invoiceId The ID of the invoice to edit.
     * @param amount    The new amount for the invoice.
     * @param issueDate The new issue date for the invoice.
     * @return A redirect to the invoices page.
     */
    @PostMapping("/edit")
    public String edit(@RequestParam int invoiceId, @RequestParam BigDecimal amount,
                       @RequestParam LocalDate issueDate) {
        invoiceService.edit(invoiceId, amount, issueDate);
        return "redirect:/invoices";
    }

    /**
     * Displays the form for adding a new invoice.
     *
     * @param model The model to add attributes to for rendering the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("guests", guestService.getGuests());
        model.addAttribute("reservations", reservationService.getReservations());
        return "add_invoice_form";
    }

    /**
     * Adds a new invoice.
     *
     * @param reservationId The ID of the reservation for which to add the invoice.
     * @return A redirect to the invoices page.
     */
    @PostMapping("/add")
    public String addInvoice(@RequestParam int reservationId) {
        BigDecimal amount = calculateInvoiceAmount(reservationId);
        LocalDate issueDate = LocalDate.now();
        invoiceService.add(reservationId, amount, issueDate);
        return "redirect:/invoices";
    }

    /**
     * Calculates the amount for an invoice based on the reservation details.
     *
     * @param reservationId The ID of the reservation.
     * @return The calculated invoice amount.
     */
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

    /**
     * Generates a PDF for the given invoice.
     *
     * @param invoiceId The ID of the invoice for which to generate the PDF.
     * @return A ResponseEntity containing the PDF content.
     * @throws IOException If an I/O error occurs.
     */
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
