package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private RoomService roomService;
    @GetMapping("")
    public String findAll(Model model){
        model.addAttribute("rooms", roomService.getRooms());
        return "rooms";
    }
}
