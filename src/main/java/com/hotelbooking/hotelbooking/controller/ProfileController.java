package com.hotelbooking.hotelbooking.controller;

import com.hotelbooking.hotelbooking.model.*;
import com.hotelbooking.hotelbooking.services.HotelService;
import com.hotelbooking.hotelbooking.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final UserService userService;

    @Autowired
    public ProfileController(HotelService hotelService , UserService userService) {
        this.hotelService = hotelService;
        this.userService = userService;
    }


    // Update an existing room
    @GetMapping("/user")
    public String profile(Model model , HttpSession session , RedirectAttributes redirectAttributes) {
        List<Booking> bookings = hotelService.getAllBookingByUserId(((AppUser)session.getAttribute("user")).getId());
        model.addAttribute("bookings" , bookings);
        return "profile";
    }

    @GetMapping("/guest/edit/{id}")
    public String enterUserGuestInfo(@PathVariable Long id,Model model) {
        model.addAttribute("guest",hotelService.getBookingById(id).getGuest());
        return "update-guest-info";
    }


    @PostMapping("/save-guest")
    public String saveGuest(Guest guest , RedirectAttributes redirectAttributes){
        hotelService.updateGuest(guest.getId(), guest);
        redirectAttributes.addFlashAttribute("message", "Hotel updated successfully!");
        return "redirect:/profile/user";
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,HttpSession session,RedirectAttributes redirectAttributes){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser user = (AppUser) session.getAttribute("user");
        if(encoder.matches(oldPassword , user.getPassword())){
            System.err.println("-------------correct");
            user.EncryptPassword(newPassword);
            userService.updateUser(user.getId(), user);
            redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
        }else{
            System.err.println("here----------------------------");
            redirectAttributes.addFlashAttribute("message", "Old password is not correct");
        }
        return "redirect:/profile/user";
    }


    @PostMapping("/deleteProfile")
    public String deleteUser(@RequestParam("oldPassword") String oldPassword , HttpSession session , RedirectAttributes redirectAttributes){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser user = (AppUser) session.getAttribute("user");
        if(encoder.matches(oldPassword , user.getPassword())){
            userService.deleteAppUser(user.getId());
            return "account-deleted";
        }else{
            redirectAttributes.addFlashAttribute("message", "Wrong password");
            return "redirect:/profile/user";
        }

    }
}
