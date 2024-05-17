package com.hotelbooking.hotelbooking.controller;

import com.hotelbooking.hotelbooking.model.*;
import com.hotelbooking.hotelbooking.services.HotelService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final HotelService hotelService;

    @Autowired
    public ProfileController(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    // Update an existing room
    @GetMapping("/user")
    public String profile(Model model , HttpSession session) {
        model.addAttribute("bookings" , hotelService.getAllBookingByUserId(((AppUser)session.getAttribute("user")).getId()));
        return "profile";
    }

    // Delete a room 
    @GetMapping("/room/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hotelService.deleteRoom(id);
        redirectAttributes.addFlashAttribute("message", "Room  deleted successfully!");
        return "redirect:/admin/rooms";
    }

    @GetMapping("/ars")
    public String listAvailableRooms(Model model) {
        model.addAttribute("ars", hotelService.getAllAvailableRooms());
        model.addAttribute("ar", new AvailableRoom()); // For the modal form
        model.addAttribute("rooms", hotelService.getAllRooms());
        return "admin-ar-manager";
    }

    // Sare a new ar
    @PostMapping("/ars")
    public String sareAvailableRoom(@ModelAttribute("ar") AvailableRoom ar, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-ar-manager";
        }
        Room room = hotelService.getRoomById(ar.getRoom().getId()); // Assuming you hare a method to get a room by ID
        ar.setRoom(room);
        hotelService.saveAvailableRoom(ar);
        redirectAttributes.addFlashAttribute("message", "AvailableRoom added successfully!");
        return "redirect:/admin/ars";
    }


    // Show form to edit an existing ar
    @GetMapping("/ar/edit/{id}")
    public String showEditAvailableRoomForm(@PathVariable Long id, Model model) {
        AvailableRoom ar = hotelService.getAvailableRoomById(id);
        model.addAttribute("ar", ar);
        model.addAttribute("rooms", hotelService.getAllRooms());
        return "admin-edit-ar";
    }

    // Update an existing ar
    @PostMapping("/ar/update/{id}")
    public String updateAvailableRoom(@PathVariable Long id, @ModelAttribute("ar") AvailableRoom ar, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-edit-ar";
        }
        hotelService.updateAvailableRoom(id, ar);
        redirectAttributes.addFlashAttribute("message", "AvailableRoom updated successfully!");
        return "redirect:/admin/ars";
    }

    // Delete a ar
    @GetMapping("/ar/delete/{id}")
    public String deleteAvailableRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hotelService.deleteAvailableRoom(id);
        redirectAttributes.addFlashAttribute("message", "AvailableRoom  deleted successfully!");
        return "redirect:/admin/ars";
    }


    @GetMapping("/hotels")
    public String showHotels(@RequestParam(required = false) String filter, @RequestParam(required = false) String value, Model model) {
        List<Hotel> hotels;
        if (filter != null && value != null) {
            switch (filter) {
                case "name":
                    hotels = hotelService.findByName(value);
                    break;
                case "location":
                    hotels = hotelService.findByLocation(value);
                    break;
                case "phoneNumber":
                    hotels = hotelService.findByPhoneNumber(value);
                    break;
                default:
                    hotels = hotelService.getAllHotels();
            }
        } else {
            hotels = hotelService.getAllHotels();
        }
        model.addAttribute("hotels", hotels);
        model.addAttribute("hotel", new Hotel()); // For the modal form6
        return "admin-hotel-manager";
    }


    // Save a new hotel
    @PostMapping("/hotels")
    public String saveHotel(@ModelAttribute("hotel") Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-hotel-manager";
        }
        hotelService.saveHotel(hotel);
        redirectAttributes.addFlashAttribute("message", "Hotel added successfully!");
        return "redirect:/admin/hotels";
    }

    // Show form to edit an existing hotel
    @GetMapping("/hotel/edit/{id}")
    public String showEditHotelForm(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel", hotel);
        return "admin-edit-hotel";
    }

    // Update an existing hotel
    @PostMapping("/hotel/update/{id}")
    public String updateHotel(@PathVariable Long id, @ModelAttribute("hotel") Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-edit-hotel";
        }
        hotelService.updateHotel(id, hotel);
        redirectAttributes.addFlashAttribute("message", "Hotel updated successfully!");
        return "redirect:/admin/hotels";
    }

    // Delete a hotel
    @GetMapping("/hotel/delete/{id}")
    public String deleteHotel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hotelService.deleteHotel(id);
        redirectAttributes.addFlashAttribute("message", "Hotel  deleted successfully!");
        return "redirect:/admin/hotels";
    }
}
