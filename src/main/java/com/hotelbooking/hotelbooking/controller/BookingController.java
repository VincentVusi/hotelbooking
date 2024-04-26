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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    
    private final HotelService hotelService;

    @Autowired
    public BookingController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/rooms")
    public String listRooms(Model model, HttpSession session) {
        model.addAttribute("rooms", hotelService.getAllRoomsForThisHotel(((Hotel)(session.getAttribute("hotel"))).getId()));
        return "select-room";
    }

    @GetMapping("/hotels/addToSession/{id}")
    public String addHotelToSession(@PathVariable Long id,HttpSession session) {
        session.setAttribute("hotel",hotelService.getHotelById(id));
        return "redirect:/booking/rooms";
    }
    @GetMapping("/hotels")
    public String listHotels(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "select-hotel";
    }

    @GetMapping("AddRoomToSession/{id}")
    public String storeRoomToSession(@PathVariable Long id,HttpSession session){
        List<AvailableRoom> rooms = (List<AvailableRoom>)session.getAttribute("rooms");
        if(rooms==null) {
            rooms = new ArrayList<>();
            rooms.add(hotelService.getAvailableRoom(id ," "));
        }else{
        }


        session.setAttribute("rooms",rooms);

        return "redirect:/booking/guest";
    }
    @GetMapping("/guest")
    public String enterUserGuestInfo(Model model) {
        model.addAttribute("guest",new Guest());
        return "guest-info";
    }
    @GetMapping("/booking")
    public String enterBookingInfo(Model model , HttpSession session , Guest guest) {
        session.setAttribute("guest",guest);
        model.addAttribute("booking",new Booking());
        return "booking-info";
    }

    @PostMapping("/confirm")
    public String confirmBooking(Booking booking , HttpSession session , Model model) {
        session.setAttribute("booking",booking);
        model.addAttribute("guest",session.getAttribute("guest"));
        model.addAttribute("booking",booking);
        return "confirm-booking";
    }

    @GetMapping("/hotel/edit/{id}")
    public String showEditHotelForm(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel", hotel);
        return "book-edit-hotel";
    }

    // Update an existing hotel
    @PostMapping("/hotel/update/{id}")
    public String updateHotel(@PathVariable Long id, @ModelAttribute("hotel") Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "book-edit-hotel";
        }
        hotelService.updateHotel(id, hotel);
        redirectAttributes.addFlashAttribute("message", "Hotel updated successfully!");
        return "redirect:/book/hotels";
    }

    // Delete a hotel
    @GetMapping("/hotel/delete/{id}")
    public String deleteHotel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        hotelService.deleteHotel(id);
        redirectAttributes.addFlashAttribute("message", "Hotel  deleted successfully!");
        return "redirect:/book/hotels";
    }
}
