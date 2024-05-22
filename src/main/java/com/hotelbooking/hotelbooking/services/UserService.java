package com.hotelbooking.hotelbooking.services;

import com.hotelbooking.hotelbooking.model.AppUser;
import com.hotelbooking.hotelbooking.model.Role;
import com.hotelbooking.hotelbooking.repository.RoleRepository;
import com.hotelbooking.hotelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    public void saveUser(AppUser user , String roleName) {
        user.setPassword(user.getPassword());
        user.setRole(roleRepository.findByName(roleName));
        userRepository.save(user);
    }

    public AppUser findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public void updateUser(Long id,AppUser user) {
        AppUser existingAppUser = findUserById(id);
        existingAppUser.setPassword(user.getPassword());
        existingAppUser.setEmail(user.getEmail());
        userRepository.save(existingAppUser);
    }
    public List<AppUser> getAllFinanceOfficers(){
        return userRepository.findByRoleName("FINANCE_OFFICER");
    }

    public void deleteAppUser(Long id) {
        userRepository.deleteById(id);
    }

}
