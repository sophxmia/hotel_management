package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.data.Reservation;
//import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private ReservationService reservationService;

    //    private GuestService guestService;
    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("reservations", reservationService.getReservations());
        return "reservations";
    }

    // Null Object pattern
    @GetMapping("/guest/{id}")
    public String reservationsForGuest(@PathVariable(name = "id") int guestId, Model model) {
        List<Reservation> reservationsForGuest = Collections.emptyList();
        if (reservationService != null) {
            reservationsForGuest = reservationService.getReservationsForGuest(guestId);
        }
        model.addAttribute("reservations", reservationsForGuest);
        return "guest_reservation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "reservation_id") int id) {
        reservationService.delete(id);
        return "redirect:/reservations";
    }
}
