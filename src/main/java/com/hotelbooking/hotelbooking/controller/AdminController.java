package com.hotelbooking.hotelbooking.controller;

import com.hotelbooking.hotelbooking.model.Hotel;
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
    public String adminPage() {
        return "admin-dashboard";
    }
    @GetMapping("/rooms")
    public String listRooms(Model model) {
        model.addAttribute("rooms", adminService.getAllRooms());
        model.addAttribute("room", new Room()); // For the modal form
        model.addAttribute("hotels", adminService.getAllHotels());
        return "admin-room-manager";
    }

    // Save a new room
    @PostMapping("/rooms")
    public String saveRoom(@ModelAttribute("room") Room room, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-room-manager";
        }
        Hotel hotel = adminService.getHotelById(room.getHotel().getId()); // Assuming you have a method to get a hotel by ID
        room.setHotel(hotel);
        adminService.saveRoom(room);
        redirectAttributes.addFlashAttribute("message", "Room added successfully!");
        return "redirect:/admin/rooms";
    }


    // Show form to edit an existing room
    @GetMapping("/room/edit/{id}")
    public String showEditRoomForm(@PathVariable Long id, Model model) {
        Room room = adminService.findRoomById(id);
        model.addAttribute("room", room);
        model.addAttribute("hotels", adminService.getAllHotels());
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
        return "redirect:/admin/rooms";
    }

    // Delete a room 
    @GetMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteRoom(id);
        redirectAttributes.addFlashAttribute("message", "Room  deleted successfully!");
        return "redirect:/admin/rooms";
    }

    @GetMapping("/hotels")
    public String listHotels(Model model) {
        model.addAttribute("hotels", adminService.getAllHotels());
        model.addAttribute("hotel", new Hotel()); // For the modal form
        return "admin-hotel-manager";
    }

    // Save a new hotel
    @PostMapping("/hotels")
    public String saveHotel(@ModelAttribute("hotel") Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-hotel-manager";
        }
        adminService.saveHotel(hotel);
        redirectAttributes.addFlashAttribute("message", "Hotel added successfully!");
        return "redirect:/admin/hotels";
    }

    // Show form to edit an existing hotel
    @GetMapping("/hotel/edit/{id}")
    public String showEditHotelForm(@PathVariable Long id, Model model) {
        Hotel hotel = adminService.getHotelById(id);
        model.addAttribute("hotel", hotel);
        return "admin-edit-hotel";
    }

    // Update an existing hotel
    @PostMapping("/hotel/update/{id}")
    public String updateHotel(@PathVariable Long id, @ModelAttribute("hotel") Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-edit-hotel";
        }
        adminService.updateHotel(id, hotel);
        redirectAttributes.addFlashAttribute("message", "Hotel updated successfully!");
        return "redirect:/admin/hotels";
    }

    // Delete a hotel
    @GetMapping("/hotel/delete/{id}")
    public String deleteHotel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteHotel(id);
        redirectAttributes.addFlashAttribute("message", "Hotel  deleted successfully!");
        return "redirect:/admin/hotels";
    }
}
