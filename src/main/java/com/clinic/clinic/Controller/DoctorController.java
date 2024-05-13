package com.clinic.clinic.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping
    public String index(Model model){
        return "index";
    }

    @GetMapping("/test")
    public String index2(Model model){
        return "index";
    }

    @PostMapping("/add")
    public String add(Model model){
        return "index";
    }

    @PostMapping("/delete")
    public String delete(Model model){
        return "index";
    }
}
