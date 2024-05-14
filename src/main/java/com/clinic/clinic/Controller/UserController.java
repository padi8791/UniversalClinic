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

    @GetMapping("/{userId}/add-doctor")
    public String showAddDoctorForm(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("doctor", new Doctor());
        return "add-doctor-form";
    }

    @PostMapping("/{userId}/add-doctor")
    public ResponseEntity<User> addDoctor(@PathVariable Long userId, @ModelAttribute Doctor doctor) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        doctorService.save(doctor);
        user.addDoctor(doctor);
        userService.save(user);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/{userId}/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctorsByUser(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getDoctors());
    }

    @GetMapping("/doctor/{doctorId}/edit")
    public String showUpdateDoctorForm(@PathVariable Long doctorId, Model model) {
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            return "redirect:/error"; // Redirect to an error page or another appropriate action
        }
        model.addAttribute("doctor", doctor);
        return "update-doctor-form"; // Name of the Thymeleaf template
    }

    @PutMapping("/doctor/{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long doctorId, @ModelAttribute Doctor updatedDoctor) {
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        // Update doctor details
        doctor.setFirstName(updatedDoctor.getFirstName());
        doctor.setLastName(updatedDoctor.getLastName());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setPhone(updatedDoctor.getPhone());
        doctor.setDepartment(updatedDoctor.getDepartment());
        // Save the updated doctor back to the database
        doctorService.save(doctor);
        // Return the updated doctor
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{userId}/delete-doctor/{doctorId}")
    public ResponseEntity<User> deleteDoctor(@PathVariable Long userId, @PathVariable Long doctorId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null || !user.getDoctors().contains(doctor)) {
            return ResponseEntity.notFound().build();
        }
        user.removeDoctor(doctor);  // Remove the doctor from the user's list
        doctorService.deleteById(doctorId);  // Delete the doctor from the database
        userService.save(user);  // Update the user in the database
        return ResponseEntity.ok(user);  // Return an OK response
    }
}
