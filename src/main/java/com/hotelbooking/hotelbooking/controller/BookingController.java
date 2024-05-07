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

    @GetMapping("storeRoomToSession/{id}")
    public String storeRoomToSession(@PathVariable Long id , HttpSession session){
        session.setAttribute("selectedRoom",hotelService.getRoomById(id));
        return "redirect:/booking/guest";
    }

    @GetMapping("/guest")
    public String enterUserGuestInfo(Model model) {
        model.addAttribute("guest",new Guest());
        return "guest-info";
    }
    @PostMapping("/booking")
    public String enterBookingInfo(Model model , HttpSession session , Guest guest) {
        session.setAttribute("guest",guest);
        model.addAttribute("booking",new Booking());
        return "booking-info";
    }

    @PostMapping("/confirm")
    public String confirmBooking(Booking booking , HttpSession session , Model model) {
        List<Room> bookedRooms = new ArrayList<>();
        bookedRooms.add((Room) session.getAttribute("selectedRoom"));
        booking.setRooms(bookedRooms);
        session.setAttribute("booking", booking);
        model.addAttribute("guest",session.getAttribute("guest"));
        model.addAttribute("booking", booking);
        return "confirm-booking";
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        model.addAttribute("payment",new Payment());
        return "payment";
    }
    @PostMapping("/save-booking")
    public String saveBooking(@ModelAttribute("payment") Payment payment,HttpSession session) {
        Hotel hotel = (Hotel)session.getAttribute("hotel");
        Room room = (Room)session.getAttribute("selectedRoom");
        Guest guest = (Guest) session.getAttribute("guest");
        Booking booking = (Booking)session.getAttribute("booking");

        guest.setAppUser((AppUser) session.getAttribute("user"));
        hotelService.saveGuest(guest);
        hotelService.savePayment(payment);
        booking.setGuest(guest);
        booking.setPayment(payment);
        hotelService.saveBooking(booking);
        return "booking-successful";
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
