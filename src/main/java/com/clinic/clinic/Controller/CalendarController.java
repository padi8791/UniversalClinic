package com.clinic.clinic.Controller;

import com.clinic.clinic.Entity.Appointment;
import com.clinic.clinic.Entity.User;
import com.clinic.clinic.Service.AppointmentService;
import com.clinic.clinic.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    private final AppointmentService appointmentService;

    private final UserService userService;
    public CalendarController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model){
        User user = userService.findById(1L);
        if (user == null) {
            return "redirect:/error";
        }
        List<Appointment> appointments = appointmentService.findByUser(user);

        model.addAttribute("appointments", appointments);
        model.addAttribute("title", "Calendar");
        return "calendar";
    }
}
