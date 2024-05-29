package com.clinic.clinic.Controller;

import com.clinic.clinic.Entity.*;
import com.clinic.clinic.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final UserService userService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DoctorService doctorService, UserService userService) {
        this.departmentService = departmentService;
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model){
        return "redirect:/departments/all";
    }

    @GetMapping("/add")
    public String showAddDoctorForm(Model model) {

        model.addAttribute("userId", '1');
        model.addAttribute("title", "Add Department");
        return "add-department-form";
    }

    @PostMapping("/add")
    public String addAppointment(@RequestParam String departmentName, @RequestParam Long doctorId) {

        Doctor doctor = doctorService.findById(doctorId);

        Department department = new Department();
        department.setDoctor(doctor);
        department.setDepartmentName(departmentName);

       departmentService.save(department);
        return "redirect:/departments";
    }
    @GetMapping("/all")
    public String getAllDepartments(Model model, @RequestParam(defaultValue = "0") int page) {


        Page<Department> departmentsPage = departmentService.getAllPaginated(page, 5);
        model.addAttribute("departmentsPage", departmentsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("title", "Departments");
        return "departments";
    }

    @GetMapping("/{departmentId}/update")
    public String showUpdateDepartmentForm(@PathVariable Long departmentId, Model model) {
        Department department = departmentService.findById(departmentId);
        if (department == null) {
            return "redirect:/error";
        }
        model.addAttribute("department", department);
        model.addAttribute("title", "Update Department");
        return "update-department-form"; // Name of the Thymeleaf template
    }

    @GetMapping("/{departmentId}")
    public String getAppointment(Model model, @PathVariable Long departmentId) {
        Department department = departmentService.findById(departmentId);
        if (department == null) {
            return "redirect:/error";
        }
        model.addAttribute("department", department);
        return "department";
    }

    @PutMapping("/{departmentId}/update")
    public String updateDepartment(@RequestParam String departmentName, @PathVariable Long departmentId) {
        Department department = departmentService.findById(departmentId);
        if (department == null) {
            return "redirect:/error";
        }
        department.setDepartmentName(departmentName);

        departmentService.save(department);
        return "redirect:/departments/all";
    }

    @DeleteMapping("/{departmentId}/delete")
    public String deleteDepartment(@PathVariable Long departmentId) {

        Department department = departmentService.findById(departmentId);
        if (department == null) {
            return "redirect:/error";
        }
        departmentService.deleteById(departmentId);  // Delete the  from the database

        return "redirect:/departments";
    }
}
