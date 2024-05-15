package com.hotelbooking.hotelbooking.controller;

import com.hotelbooking.hotelbooking.model.AvailableRoom;
import com.hotelbooking.hotelbooking.model.Hotel;
import com.hotelbooking.hotelbooking.model.Image;
import com.hotelbooking.hotelbooking.model.Room;
import com.hotelbooking.hotelbooking.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private final HotelService hotelService;

    @Autowired
    public AdminController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping()
    public String adminPage() {
        return "admin-dashboard";
    }
    @GetMapping("/rooms")
    public String listRooms(Model model) {
        model.addAttribute("rooms", hotelService.getAllRooms());
        model.addAttribute("room", new Room()); // For the modal form
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "admin-room-manager";
    }

    // Save a new room
    @PostMapping("/rooms")
    public String saveRoom(@ModelAttribute("room") Room room, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-room-manager";
        }
        Hotel hotel = hotelService.getHotelById(room.getHotel().getId()); // Assuming you have a method to get a hotel by ID
        room.setHotel(hotel);
        hotelService.saveRoom(room);
        redirectAttributes.addFlashAttribute("message", "Room added successfully!");
        return "redirect:/admin/rooms";
    }


    // Show form to edit an existing room
    @GetMapping("/room/edit/{id}")
    public String showEditRoomForm(@PathVariable Long id, Model model) {
        Room room = hotelService.getRoomById(id);
        model.addAttribute("room", room);
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "admin-edit-room";
    }

    // Update an existing room
    @PostMapping("/room/update/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute("room") Room room, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin-edit-room";
        }
        hotelService.updateRoom(id, room);
        redirectAttributes.addFlashAttribute("message", "Room updated successfully!");
        return "redirect:/admin/rooms";
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
    @GetMapping("/images")
    public String addImagesPage(){
        return "add-images";
    }

    @PostMapping("/save/images")
    public String uploadImages(@RequestParam("images") MultipartFile[] images) {
        for (MultipartFile multipartFile : images) {
            try {
                byte[] data = multipartFile.getBytes();
                Image image = new Image(data);
                hotelService.saveImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }
}
