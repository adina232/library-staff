package com.example.library.controller;

import com.example.library.controller.dto.EmployeeDto;
import com.example.library.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home() {
        return "base";
    }



    @GetMapping("/addEmployee")
    public ModelAndView getEmployeeForm() {
        ModelAndView addEmployee = new ModelAndView("addEmployee");
        addEmployee.addObject("employee", new EmployeeDto());
        return addEmployee;
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute EmployeeDto employee) {
        employeeService.addEmployee(employee);
        return "redirect:/employeeList";
    }

    @GetMapping("/employeeList")
    public String getEmployeesList(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        return "employeeList";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Integer id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employeeList";
    }

    @GetMapping("/modifyEmployee")
    public ModelAndView modifyEmployee(@RequestParam Integer id) {
        return employeeService.modifyEmployee(id);
    }
}
