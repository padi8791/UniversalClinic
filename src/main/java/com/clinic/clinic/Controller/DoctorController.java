package com.clinic.clinic.Controller;

import com.clinic.clinic.Entity.Doctor;
import com.clinic.clinic.Entity.User;
import com.clinic.clinic.Service.DoctorService;
import com.clinic.clinic.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
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
        return "redirect:/doctors/all";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("userId", '1');
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("title", "Add Doctor");
        return "add-doctor-form";
    }

    @PostMapping("/add")
    public String addDoctor(@ModelAttribute Doctor doctor) {
        User user = userService.findById(1L);
        if (user == null) {
            return "redirect:/login";
        }
        doctorService.save(doctor);
        user.addDoctor(doctor);
        userService.save(user);
        return "redirect:/doctors";
    }

    @GetMapping("/all")
    public String getAllDoctorsByUser(Model model, @RequestParam(defaultValue = "0") int page) {
        User user = userService.findById(1L);
        if (user == null) {
            return "redirect:/login";
        }

        Page<Doctor> doctorPage = doctorService.getAllPaginated(page, 5);
        model.addAttribute("doctorPage", doctorPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("title", "Doctors");
        return "doctors";
    }

    @GetMapping("/{doctorId}/update")
    public String showUpdateDoctorForm(@PathVariable Long doctorId, Model model) {
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            return "redirect:/error"; // Redirect to an error page or another appropriate action
        }
        model.addAttribute("doctor", doctor);
        model.addAttribute("title", "Update Doctor");
        return "update-doctor-form"; // Name of the Thymeleaf template
    }

    @PutMapping("/{doctorId}/update")
    public String updateDoctor(@PathVariable Long doctorId, @ModelAttribute Doctor updatedDoctor) {
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            return "redirect:/error";
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
        return "redirect:/doctors";
    }

    @GetMapping("/delete")
    public String deleteDoctorForm(Model model) {
        Doctor doctor = doctorService.findById(8L);
        model.addAttribute("userId", '1');
        model.addAttribute("doctor", doctor);
        return "delete-doctor-form";
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

    @GetMapping("/by-lastname/{lastName}")
    public ResponseEntity<List<Doctor>> getDoctorsByLastName(@PathVariable String lastName) {
        List<Doctor> doctors = doctorService.findByLastName(lastName);
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(doctors);
    }
}
