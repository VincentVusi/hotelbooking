package com.hotelbooking.hotelbooking.controller;

import com.hotelbooking.hotelbooking.model.AppUser;
import com.hotelbooking.hotelbooking.model.Room;
import com.hotelbooking.hotelbooking.services.UserService;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<AppUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session){
        AppUser user = (AppUser)session.getAttribute("user");
        if(user != null){
            return switch (user.getRole().getName()) {
                case "FINANCE_OFFICER" -> "redirect:/finance";
                case "ADMIN" -> "redirect:/admin";
                default -> "redirect:/booking/hotels";
            };
        }
        model.addAttribute("user" , new AppUser());
        return "login";
    }
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") AppUser user, Model model , HttpSession session , RedirectAttributes redirectAttributes) {
        AppUser existingUser = userService.findUserByEmail(user.getEmail());
        if(existingUser != null ){
            if (existingUser.getPassword().equals(user.getPassword())) {
                session.setAttribute("user",existingUser);
                switch (existingUser.getRole().getName()){
                    case "FINANCE_OFFICER" : return "redirect:/finance";
                    case "ADMIN" : return "redirect:/admin";
                    default:
                        return "redirect:/booking/hotels";
                }

            } else {
                redirectAttributes.addFlashAttribute("message", "Incorrect password");
                return "redirect:/users/login";
            }
        }else{
            redirectAttributes.addFlashAttribute("message", "User does not exist");
            return "redirect:/users/login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session){
        return "logout";
    }
    @GetMapping("/logout-now")
    public String logoutNow(Model model, HttpSession session){
        session.invalidate();
        return "redirect:/pages/landing";
    }
    @GetMapping("/create-account")
    public String createAccount(Model model){
        model.addAttribute("user" , new AppUser());
        return "create-account";
    }

    @PostMapping("/create-account")
    public String saveUser(@ModelAttribute("user") AppUser user , RedirectAttributes redirectAttributes) {
        AppUser existingUser = userService.findUserByEmail(user.getEmail());
        if(existingUser == null) {
            userService.saveUser(user, "USER");
            return "account-created";
        }else{
            redirectAttributes.addFlashAttribute("message", "User exists , try new username");
            return "redirect:/users/create-account";
        }

    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        AppUser user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/email/{email}")
    public String getUserByEmail(@PathVariable String email, Model model) {
        AppUser user = userService.findUserByEmail(email);
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, AppUser user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteAppUser(id);
        return "redirect:/users";
    }
}
