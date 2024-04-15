package com.hotelbooking.hotelbooking.controller;

import com.hotelbooking.hotelbooking.model.Room;
import com.hotelbooking.hotelbooking.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping()
    public String listRooms(Model model) {
        model.addAttribute("rooms", adminService.getAllRooms());
        model.addAttribute("room", new Room()); // For the modal form
        return "admin";
    }

    // Save a new room
    @PostMapping("/room")
    public String saveRoom(@ModelAttribute("room") Room room, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin";
        }
        adminService.saveRoom(room);
        redirectAttributes.addFlashAttribute("message", "Room added successfully!");
        return "redirect:/admin";
    }

    // Show form to edit an existing room
    @GetMapping("/room/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Room room = adminService.findRoomById(id);
        model.addAttribute("room", room);
        return "admin-edit-room";
    }

    // Update an existing room
    @PostMapping("/room/update/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute("room") Room room, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-edit-room";
        }
        adminService.updateRoom(id, room);
        redirectAttributes.addFlashAttribute("message", "Room updated successfully!");
        return "redirect:/admin";
    }

    // Delete a room 
    @GetMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteRoom(id);
        redirectAttributes.addFlashAttribute("message", "Room  deleted successfully!");
        return "redirect:/admin";
    }

}
