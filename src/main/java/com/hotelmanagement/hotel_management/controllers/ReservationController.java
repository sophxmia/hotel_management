package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.data.Reservation;
//import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.GuestService;
import com.hotelmanagement.hotel_management.services.ReservationService;
import com.hotelmanagement.hotel_management.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private ReservationService reservationService;
    private GuestService guestService;
    private RoomService roomService;

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

    @PostMapping("/edit")
    public String edit(@RequestParam int reservationId, @RequestParam LocalDate startDate,
                       @RequestParam LocalDate endDate) {
        reservationService.edit(reservationId, startDate, endDate);
        return "redirect:/reservations";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("guests", guestService.getGuests());
        model.addAttribute("rooms", roomService.getVacantRooms());
        return "add_reservation_form"; // Передаємо модель у форму для додавання резервації
    }

    @PostMapping("/add")
    public String add(@RequestParam int guestId, @RequestParam int roomId,
                      @RequestParam String startDate, @RequestParam String endDate) {
        reservationService.add(guestId, roomId, startDate, endDate);
        return "redirect:/reservations";
    }
}
