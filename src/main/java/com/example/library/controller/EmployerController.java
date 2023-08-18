package com.example.library.controller;

import com.example.library.repository.Employer;
import com.example.library.service.EmployerService;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployerController {
    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("/addEmployer")
    public ModelAndView getEmployerForm() {
        var mav = new ModelAndView();
        mav.addObject("employer", new Employer());
        return mav;
    }

    @PostMapping("/addEmployer")
    public String addEmployer(@ModelAttribute Employer employer) {
        employerService.addEmployer(employer);
        return "redirect:/getEmployers";
    }

    @GetMapping("/getEmployers")
    public String getEmployers(Model model) {
        model.addAttribute("employers", employerService.getEmployers());
        return "getEmployers";
    }

    @GetMapping("/deleteEmployer")
    public String deleteEmployer(Integer id) {
        employerService.deleteEmployer(id);
        return "redirect:/getEmployers";
    }
}
