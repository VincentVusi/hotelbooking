package com.hotelbooking.hotelbooking;

import com.hotelbooking.hotelbooking.model.AppUser;
import com.hotelbooking.hotelbooking.model.Image;
import com.hotelbooking.hotelbooking.model.Role;
import com.hotelbooking.hotelbooking.repository.ImageRepository;
import com.hotelbooking.hotelbooking.repository.RoleRepository;
import com.hotelbooking.hotelbooking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hotelbooking.hotelbooking"})
public class HotelbookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelbookingApplication.class, args);
	}

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;
	@RequestMapping("/")
	public String index(Model model) {
		List<Image> images = imageRepository.findAll();
		model.addAttribute("images", images);
		return "index";
	}

	@Bean
	public CommandLineRunner setupDefaultUser() {
		return args -> {
			if(roleRepository.findAll().isEmpty()){
				List<Role> roles = new ArrayList<>();
				Role userRole = new Role();
				userRole.setName("USER");
				roles.add(userRole);
				Role adminRole = new Role();
				adminRole.setName("ADMIN");
				roles.add(adminRole);
				Role financeOfficer = new Role();
				financeOfficer.setName("FINANCE_OFFICER");
				roles.add(financeOfficer);
				roleRepository.saveAll(roles);
			}
			if(userService.findUserByEmail("admin@amazing.com") == null){
				AppUser user = new AppUser();
				user.setEmail("admin@amazing.com");
				user.EncryptPassword("Admin@123");
				userService.saveUser(user,"ADMIN");}
		};
	}
}
