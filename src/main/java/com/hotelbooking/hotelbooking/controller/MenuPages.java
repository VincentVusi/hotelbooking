package com.hotelbooking.hotelbooking.controller;

import com.hotelbooking.hotelbooking.model.Image;
import com.hotelbooking.hotelbooking.repository.ImageRepository;
import com.hotelbooking.hotelbooking.repository.RoleRepository;
import com.hotelbooking.hotelbooking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pages")
public class MenuPages {
    @Autowired
    private ImageRepository imageRepository;
    @RequestMapping("/landing")
    public String landing(Model model) {
        List<Image> images = imageRepository.findAll();
        if(images.isEmpty())
        {
            return "web-app-not-ready";
        }else {
            model.addAttribute("images", images);
            return "landing";
        }
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }
    @GetMapping("/service")
    public String servicePage(){
        return "service";
    }
}
