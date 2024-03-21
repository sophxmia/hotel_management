package com.hotelmanagement.hotel_management.controllers;

import com.hotelmanagement.hotel_management.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PostMapping("/add")
    public String add(@RequestParam String roomClass,@RequestParam Integer capacity,@RequestParam String status){
        roomService.add(roomClass,capacity, status);
        return "redirect:/rooms";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "room_id") int id){
        roomService.delete(id);
        return "redirect:/rooms";
    }
    @PostMapping("/edit")
    public String edit(@RequestParam int roomId, @RequestParam String roomClass,
                             @RequestParam Integer capacity, @RequestParam String status) {
        roomService.edit(roomId, roomClass, capacity, status);
        return "redirect:/rooms";
    }
}
