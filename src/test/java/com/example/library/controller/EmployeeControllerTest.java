package com.example.library.controller;

import com.example.library.controller.dto.EmployeeDto;
import com.example.library.controller.dto.EmployeeDtoName;
import com.example.library.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {
    EmployeeService employeeService = mock(EmployeeService.class);
    EmployeeController employeeController = new EmployeeController(employeeService);

    @Test
    void home() {
        assertEquals("base", employeeController.home());
    }

    @Test
    void getEmployeeForm() {
        ModelAndView actual = employeeController.getEmployeeForm();
        assertEquals("addEmployee", actual.getViewName());
        assertTrue(actual.getModel().containsKey("employee"));
        assertNull(((EmployeeDto) actual.getModel().get("employee")).getId());
        assertNull(((EmployeeDto) actual.getModel().get("employee")).getFirstName());
        assertNull(((EmployeeDto) actual.getModel().get("employee")).getLastName());
        assertEquals(0,
                ((EmployeeDto) actual.getModel().get("employee")).getAge());
        assertNull(((EmployeeDto) actual.getModel().get("employee")).getFunction());
        assertEquals(0.0,
                ((EmployeeDto) actual.getModel().get("employee")).getExperience());
        assertNull(((EmployeeDto) actual.getModel().get("employee")).getEmployerId());
    }

    @Test
    void addEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        assertEquals("redirect:/employeeList",
                employeeController.addEmployee(employeeDto));
        verify(employeeService).addEmployee(employeeDto);
    }

    @Test
    void getEmployeesList() {
        //pregatesti datele
        Model actual = mock(Model.class);
        List<EmployeeDtoName> actualList = new ArrayList<>();
        when(employeeService.getEmployees()).thenReturn(actualList);

        //apelezi metoda pe care vrei sa o testezi
        String result = employeeController.getEmployeesList(actual);

        //verifici ca s-a intamplat ce trebuie
        verify(actual).addAttribute("employees", actualList);
        assertEquals("employeeList", result);
    }

    @Test
    void deleteEmployee() {
        assertEquals("redirect:/employeeList",
                employeeController.deleteEmployee(1));
        verify(employeeService).deleteEmployee(1);
    }

    @Test
    void modifyEmployee() {
        employeeController.modifyEmployee(1);
        verify(employeeService).modifyEmployee(1);
    }
}