package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private ReservationService reservationService;
    @GetMapping("")
    public String findAll(Model model){
        model.addAttribute("reservations", reservationService.getReservations());
        return "reservations";
    }
}
