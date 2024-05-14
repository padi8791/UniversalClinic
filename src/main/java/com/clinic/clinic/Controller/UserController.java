package com.clinic.clinic.Controller;

import com.clinic.clinic.Entity.Doctor;
import com.clinic.clinic.Entity.User;
import com.clinic.clinic.Service.DoctorService;
import com.clinic.clinic.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final DoctorService doctorService;

    // Constructor-based injection is recommended
    public UserController(UserService userService, DoctorService doctorService) {
        this.userService = userService;
        this.doctorService = doctorService;
    }

    @GetMapping("/new")
    public ResponseEntity<User> createUser() {
        User newUser = new User("Name", "Email", "0000");
        userService.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

//    @GetMapping("/{userId}/add-doctor-to-user")
//    public ResponseEntity<User> addDoctorToUser(@PathVariable Long userId) {
//        User user = userService.findById(userId);
//        if (user == null) {
//            return ResponseEntity.notFound().build(); // Properly handle the case where the user doesn't exist
//        }
//
//        Doctor doctor = new Doctor("FirstName", "LastName", "Email", "Phone", "Department");
//        doctorService.save(doctor);
//        user.addDoctor(doctor);
//        userService.save(user); // Assume userService.save() handles cascading or appropriate saving
//
//        return ResponseEntity.ok(user); // Return the updated user with the new doctor added
//    }


}
