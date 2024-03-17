package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/guests")
public class GuestController {
    private GuestService guestService;
    @GetMapping("")
    public String showAll(Model model){
        model.addAttribute("guests", guestService.getGuests());
        return "guests";
    }
}
