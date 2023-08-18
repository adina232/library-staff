package com.example.library.controller;

import com.example.library.repository.Employer;
import com.example.library.service.EmployerService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EmployerControllerTest {

    EmployerService employerService = mock(EmployerService.class);
    EmployerController employerController = new EmployerController(employerService);

    @Test
    void getEmployerForm() {
        ModelAndView actual = employerController.getEmployerForm();

        assertNull(actual.getViewName());
        assertTrue(actual.getModel().containsKey("employer"));
        assertNull(((Employer) actual.getModel().get("employer")).getName());
        assertNull(((Employer) actual.getModel().get("employer")).getId());
    }

    @Test
    void addEmployer() {
        Employer employer = new Employer();
        assertEquals("redirect:/getEmployers",
                employerController.addEmployer(employer));
        verify(employerService).addEmployer(employer);
    }

    @Test
    void getEmployers() {
        Model model = mock(Model.class);
        assertEquals("getEmployers",
                employerController.getEmployers(model));
        verify(employerService).getEmployers();
    }

    @Test
    void deleteEmployer() {
        assertEquals(employerController.deleteEmployer(1),
                "redirect:/getEmployers");
        verify(employerService).deleteEmployer(1);
    }
}