package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for managing guest-related operations.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/guests")
public class GuestController {
    private GuestService guestService;

    /**
     * Displays all guests.
     *
     * @param model Model object to add attributes for rendering the view.
     * @return The view name for displaying all guests.
     */
    @GetMapping("")
    public String showAll(Model model) {
        model.addAttribute("guests", guestService.getGuests());
        return "guests";
    }

    /**
     * Adds a new guest.
     *
     * @param firstName     The first name of the guest.
     * @param lastName      The last name of the guest.
     * @param passportInfo  The passport information of the guest.
     * @param contactNumber The contact number of the guest.
     * @return Redirects to the view displaying all guests.
     */
    @PostMapping("/add")
    public String add(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String passportInfo, @RequestParam String contactNumber) {
        guestService.add(firstName, lastName, passportInfo, contactNumber);
        return "redirect:/guests";
    }

    /**
     * Deletes a guest.
     *
     * @param id The ID of the guest to delete.
     * @return Redirects to the view displaying all guests.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "guest_id") int id) {
        guestService.delete(id);
        return "redirect:/guests";
    }

    /**
     * Edits a guest's information.
     *
     * @param guestId       The ID of the guest to edit.
     * @param firstName     The updated first name of the guest.
     * @param lastName      The updated last name of the guest.
     * @param passportInfo  The updated passport information of the guest.
     * @param contactNumber The updated contact number of the guest.
     * @return Redirects to the view displaying all guests.
     */
    @PostMapping("/edit")
    public String edit(@RequestParam int guestId, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String passportInfo, @RequestParam String contactNumber) {
        guestService.edit(guestId, firstName, lastName, passportInfo, contactNumber);
        return "redirect:/guests";
    }

    /**
     * Searches for guests based on the provided query.
     *
     * @param query The search query.
     * @param model Model object to add attributes for rendering the view.
     * @return The view name for displaying search results.
     */
    @GetMapping("/search")
    public String search(@RequestParam(name = "query") String query, Model model) {
        model.addAttribute("guests", guestService.searchGuests(query));
        return "guests";
    }

}
