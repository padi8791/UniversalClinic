package com.clinic.clinic.Controller;

import com.clinic.clinic.Entity.Appointment;
import com.clinic.clinic.Entity.Doctor;
import com.clinic.clinic.Entity.User;
import com.clinic.clinic.Service.AppointmentService;
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
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    public AppointmentController(AppointmentService appointmentService, UserService userService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String index(Model model){
        return "index";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("userId", '1');
        model.addAttribute("appointment", new Appointment());
        return "add-appointment-form";
    }

    @PostMapping("/add")
    public ResponseEntity<User> addAppointment(@ModelAttribute Appointment appointment) {
        User user = userService.findById(1L);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        appointmentService.save(appointment);
        user.addAppointment(appointment);
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointmentsByUser() {
        User user = userService.findById(1L);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getAppointments());
    }

    @GetMapping("/{appointmentId}/edit")
    public String showUpdateDoctorForm(@PathVariable Long appointmentId, Model model) {
        Appointment appointment = appointmentService.findById(appointmentId);
        if (appointment == null) {
            return "redirect:/error"; // Redirect to an error page or another appropriate action
        }
        model.addAttribute("appointment", appointment);
        return "update-appointment-form"; // Name of the Thymeleaf template
    }

    @PutMapping("/{appointmentId}/update")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long appointmentId, @ModelAttribute Appointment updatedAppointment) {
        Appointment appointment = appointmentService.findById(appointmentId);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        // Update doctor details
        appointment.setDoctorId(updatedAppointment.getDoctorId());
        appointment.setPatientId(updatedAppointment.getPatientId());
        appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        // Save the updated doctor back to the database
        appointmentService.save(appointment);
        // Return the updated doctor
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/{appointmentId}/delete")
    public ResponseEntity<User> deleteAppointment(@PathVariable Long appointmentId) {
        User user = userService.findById(1L);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Appointment appointment = appointmentService.findById(appointmentId);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        user.removeAppointment(appointment);  // Remove the  from the user's list
        appointmentService.deleteById(appointmentId);  // Delete the  from the database
        userService.save(user);  // Update the user in the database
        return ResponseEntity.ok(user);  // Return an OK response
    }
}
