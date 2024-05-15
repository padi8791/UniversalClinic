package com.clinic.clinic.Controller;

import com.clinic.clinic.Entity.Doctor;
import com.clinic.clinic.Entity.Patient;
import com.clinic.clinic.Entity.User;
import com.clinic.clinic.Service.PatientService;
import com.clinic.clinic.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    public PatientController(PatientService patientService, UserService userService) {
        this.patientService = patientService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model){
        return "index";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("userId", '1');
        model.addAttribute("patient", new Patient());
        return "add-patient-form";
    }

    @PostMapping("/add")
    public ResponseEntity<User> addDoctor(@ModelAttribute Patient patient) {
        User user = userService.findById(1L);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        patientService.save(patient);
        user.addPatient(patient);
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatientsByUser() {
        User user = userService.findById(1L);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getPatients());
    }

    @GetMapping("/{patientId}/edit")
    public String showUpdatePatientForm(@PathVariable Long patientId, Model model) {
        Patient patient = patientService.findById(patientId);
        if (patient == null) {
            return "redirect:/error"; // Redirect to an error page or another appropriate action
        }
        model.addAttribute("patient", patient);
        return "update-patient-form"; // Name of the Thymeleaf template
    }

    @PutMapping("/{patientId}/update")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long patientId, @ModelAttribute Patient updatedPatient) {
        Patient patient = patientService.findById(patientId);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setEmail(updatedPatient.getEmail());
        patient.setPhone(updatedPatient.getPhone());
        patient.setBirthday(updatedPatient.getBirthday());
        patientService.save(patient);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{patientId}/delete")
    public ResponseEntity<User> deletePatient(@PathVariable Long patientId) {
        User user = userService.findById(1L);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Patient patient = patientService.findById(patientId);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        user.removePatient(patient);  // Remove the doctor from the user's list
        patientService.deleteById(patientId);  // Delete the doctor from the database
        userService.save(user);  // Update the user in the database
        return ResponseEntity.ok(user);  // Return an OK response
    }


}
