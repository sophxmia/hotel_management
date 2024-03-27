package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.data.Reservation;
//import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.InvoiceService;
import com.hotelmanagement.hotel_management.services.ReservationService;
import com.hotelmanagement.hotel_management.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Controller class to handle operations related to reservations.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private ReservationService reservationService;
    private GuestService guestService;
    private RoomService roomService;
    private InvoiceController invoiceController;
    private InvoiceService invoiceService;

    /**
     * Displays all reservations.
     *
     * @param model The model to add attributes to for rendering the view.
     * @return The name of the view template to render.
     */
    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("reservations", reservationService.getReservations());
        return "reservations";
    }

    // Null Object pattern

    /**
     * Displays reservations for a specific guest.
     *
     * @param guestId The ID of the guest.
     * @param model   The model to add attributes to for rendering the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/guest/{id}")
    public String reservationsForGuest(@PathVariable(name = "id") int guestId, Model model) {
        List<Reservation> reservationsForGuest = Collections.emptyList();
        if (reservationService != null) {
            reservationsForGuest = reservationService.getReservationsForGuest(guestId);
        }
        model.addAttribute("reservations", reservationsForGuest);
        return "guest_reservation";
    }

    /**
     * Deletes a reservation by ID.
     *
     * @param id The ID of the reservation to delete.
     * @return A redirect to the reservations page.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "reservation_id") int id) {
        reservationService.delete(id);
        return "redirect:/reservations";
    }

    /**
     * Edits a reservation.
     *
     * @param reservationId The ID of the reservation to edit.
     * @param startDate     The new start date for the reservation.
     * @param endDate       The new end date for the reservation.
     * @return A redirect to the reservations page.
     */
    @PostMapping("/edit")
    public String edit(@RequestParam int reservationId, @RequestParam LocalDate startDate,
                       @RequestParam LocalDate endDate) {
        reservationService.edit(reservationId, startDate, endDate);
        BigDecimal newAmount = invoiceController.calculateInvoiceAmount(reservationId);

        // Оновлення суми рахунку після зміни дати резервації
        invoiceService.updateAmountForReservation(reservationId, newAmount);
        return "redirect:/reservations";
    }

    /**
     * Displays the form for adding a new reservation.
     *
     * @param model The model to add attributes to for rendering the view.
     * @return The name of the view template to render.
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("guests", guestService.getGuests());
        model.addAttribute("rooms", roomService.getVacantRooms());
        return "add_reservation_form";
    }

    /**
     * Adds a new reservation.
     *
     * @param guestId   The ID of the guest making the reservation.
     * @param roomId    The ID of the room being reserved.
     * @param startDate The start date of the reservation.
     * @param endDate   The end date of the reservation.
     * @return A redirect to the reservations page.
     */
    @PostMapping("/add")
    public String add(@RequestParam int guestId, @RequestParam int roomId,
                      @RequestParam String startDate, @RequestParam String endDate) {
        reservationService.add(guestId, roomId, startDate, endDate);
        return "redirect:/reservations";
    }
}
