package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/guests")
public class GuestController {
    private GuestService guestService;

    @GetMapping("")
    public String showAll(Model model) {
        model.addAttribute("guests", guestService.getGuests());
        return "guests";
    }

    @PostMapping("/add")
    public String add(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String passportInfo, @RequestParam String contactNumber) {
        guestService.add(firstName, lastName, passportInfo, contactNumber);
        return "redirect:/guests";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "guest_id") int id) {
        guestService.delete(id);
        return "redirect:/guests";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "query") String query, Model model) {
        model.addAttribute("guests", guestService.searchGuests(query));
        return "guests";
    }

}
