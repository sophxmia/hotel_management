package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class to handle operations related to rooms.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private RoomService roomService;

    /**
     * Displays all rooms.
     *
     * @param model The model to add attributes to for rendering the view.
     * @return The name of the view template to render.
     */
    @GetMapping("")
    public String findAll(Model model) {
        model.addAttribute("rooms", roomService.getRooms());
        return "rooms";
    }

    /**
     * Adds a new room.
     *
     * @param roomClass The class/type of the room.
     * @param capacity  The capacity of the room.
     * @param status    The status of the room.
     * @return A redirect to the rooms page.
     */
    @PostMapping("/add")
    public String add(@RequestParam String roomClass, @RequestParam Integer capacity, @RequestParam String status) {
        roomService.add(roomClass, capacity, status);
        return "redirect:/rooms";
    }

    /**
     * Deletes a room by ID.
     *
     * @param id The ID of the room to delete.
     * @return A redirect to the rooms page.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "room_id") int id) {
        roomService.delete(id);
        return "redirect:/rooms";
    }

    /**
     * Edits a room.
     *
     * @param roomId    The ID of the room to edit.
     * @param roomClass The new class/type of the room.
     * @param capacity  The new capacity of the room.
     * @param status    The new status of the room.
     * @return A redirect to the rooms page.
     */
    @PostMapping("/edit")
    public String edit(@RequestParam int roomId, @RequestParam String roomClass,
                       @RequestParam Integer capacity, @RequestParam String status) {
        roomService.edit(roomId, roomClass, capacity, status);
        return "redirect:/rooms";
    }
}
