package com.hotelbooking.hotelbooking.controller;

import com.hotelbooking.hotelbooking.model.AppUser;
import com.hotelbooking.hotelbooking.model.Room;
import com.hotelbooking.hotelbooking.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<AppUser> users = userService.getAllFinanceOfficers();
        model.addAttribute("users", users);
        model.addAttribute("user",new AppUser());
        return "admin-user-manager";
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AppUser existingUser = userService.findUserByEmail(user.getEmail());
        if(existingUser != null ){
            if (encoder.matches(user.getPassword() , existingUser.getPassword())) {
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
    public String saveUser(@ModelAttribute("user") AppUser user , RedirectAttributes redirectAttributes , HttpSession session) {
        AppUser existingUser = userService.findUserByEmail(user.getEmail());
        if(existingUser == null) {
            user.EncryptPassword(user.getPassword());
            userService.saveUser(user, "USER");
            session.setAttribute("user",user);
            return "account-created";
        }else{
            redirectAttributes.addFlashAttribute("message", "User exists , try new username");
            return "redirect:/users/create-account";
        }

    }
    @PostMapping("/createFinance")
    public String saveFinance(@ModelAttribute("user") AppUser user , RedirectAttributes redirectAttributes , HttpSession session) {
        AppUser existingUser = userService.findUserByEmail(user.getEmail());
        if(existingUser == null) {
            user.EncryptPassword(user.getPassword());
            userService.saveUser(user, "FINANCE_OFFICER");
            return "redirect:/users/users";
        }else{
            redirectAttributes.addFlashAttribute("message", "User exists , try new username");
            return "redirect:/users/createFinance";
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

    @GetMapping("/user/edit/{id}")
    public String updateUser(@PathVariable Long id, AppUser user , RedirectAttributes redirectAttributes) {
        userService.updateUser(id, user);
        redirectAttributes.addFlashAttribute("message", "User exists , try new username");
        return "admin-update-finance";
    }
    @GetMapping("/user/update")
    public String updateNow(@ModelAttribute("user") AppUser user , RedirectAttributes redirectAttributes) {
        userService.updateUser(user.getId(), user);
        redirectAttributes.addFlashAttribute("message", "User updated");
        return "redirect:/users/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteAppUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted");
        return "redirect:/users/users";
    }


}
