package com.clinic.clinic.Controller;

import com.clinic.clinic.Entity.Doctor;
import com.clinic.clinic.Entity.User;
import com.clinic.clinic.Service.DoctorService;
import com.clinic.clinic.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final UserService userService;
    @Autowired
    public DoctorController(DoctorService doctorService, UserService userService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model){
        return "index";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("userId", '1');
        model.addAttribute("doctor", new Doctor());
        return "add-doctor-form";
    }

    @PostMapping("/add")
    public ResponseEntity<User> addDoctor(@ModelAttribute Doctor doctor) {
        User user = userService.findById(1L);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        doctorService.save(doctor);
        user.addDoctor(doctor);
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctorsByUser() {
        User user = userService.findById(1L);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getDoctors());
    }

    @GetMapping("/{doctorId}/edit")
    public String showUpdateDoctorForm(@PathVariable Long doctorId, Model model) {
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            return "redirect:/error"; // Redirect to an error page or another appropriate action
        }
        model.addAttribute("doctor", doctor);
        return "update-doctor-form"; // Name of the Thymeleaf template
    }

    @PutMapping("/{doctorId}/update")
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

    @DeleteMapping("/{doctorId}/delete")
    public ResponseEntity<User> deleteDoctor(@PathVariable Long doctorId) {
        User user = userService.findById(1L);
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
