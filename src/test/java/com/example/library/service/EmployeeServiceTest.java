package com.example.library.service;

import com.example.library.controller.dto.EmployeeDto;
import com.example.library.controller.dto.EmployeeDtoName;
import com.example.library.repository.Employee;
import com.example.library.repository.EmployeeRepository;
import com.example.library.repository.Employer;
import com.example.library.repository.EmployerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    EmployerRepository employerRepository = mock(EmployerRepository.class);
    EmployeeService employeeService = new EmployeeService(employeeRepository, employerRepository);

    @Test
    void addEmployee_whenEmployerIsPresent_thanSaveEmployeeWithEmployer() {
        // declar datele cu care lucrez
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Test");
        employeeDto.setEmployerId(1);
        employeeDto.setDateOfBirth("2000-09-01");

        Employer employer = new Employer();

        Employee employee = new Employee();
        employee.setFirstName("Test");
        employee.setEmployer(employer);
        employee.setDateOfBirth(parseDate("2000-09-01"));

        when(employerRepository.findById(1))
                .thenReturn(Optional.of(employer));

        // rulez programul
        employeeService.addEmployee(employeeDto);

        //verific programul

        verify(employeeRepository).save(employee);

//        verify(employeeRepository).save(argThat(arg -> arg.getFirstName().equals("Test")));
    }

    private static Date parseDate(String date) {
        Date response;
        try {
            response = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Test
    void addEmployee_whenEmployerIsNotPresent_thanSaveEmployeeWithoutEmployer() {
        // declar datele cu care lucrez
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Test");
        employeeDto.setEmployerId(1);
        employeeDto.setDateOfBirth("2021-03-21");

        Employer employer = new Employer();

        Employee employee = new Employee();
        employee.setFirstName("Test");
        employee.setEmployer(employer);
        employee.setDateOfBirth(parseDate("2000-03-21"));


        when(employerRepository.findById(2))
                .thenReturn(Optional.of(employer));

        // rulez programul
//        assertThrows(RuntimeException.class,() ->employeeService.addEmployee(employeeDto));

        Exception exception = null;
        try {
            employeeService.addEmployee(employeeDto);
        } catch (Exception e) {
            exception = e;
        }

        //verific programul
        assertTrue(exception instanceof RuntimeException);
        assertEquals("Employer not found by id 1", exception.getMessage());
    }

    @Test
    void getEmployees() {
        // declar datele
        List<Employee> employeeList = new ArrayList<>();
        Employee e1 = new Employee();
        e1.setFirstName("a");
        e1.setId(1);
        e1.setDateOfBirth(parseDate("2000-03-21"));

        Employee e2 = new Employee();
        e2.setFirstName("b");
        e2.setId(2);
        Employer employer = new Employer();
        employer.setName("Test");
        e2.setEmployer(employer);
        e2.setDateOfBirth(parseDate("2021-03-21"));

        employeeList.add(e2);
        employeeList.add(e1);

        when(employeeRepository.findAll()).thenReturn(employeeList);

        // rulez programul
        List<EmployeeDtoName> employeeDtoNameList = employeeService.getEmployees();

        //verific
        assertEquals(1, employeeDtoNameList.get(0).getId());
        assertEquals(2, employeeDtoNameList.get(1).getId());
        assertEquals("a", employeeDtoNameList.get(0).getFirstName());
        assertEquals("b", employeeDtoNameList.get(1).getFirstName());
        assertNull(employeeDtoNameList.get(0).getLastName());
        assertNull(employeeDtoNameList.get(1).getLastName());
        assertEquals(0, employeeDtoNameList.get(0).getAge());
        assertEquals(0, employeeDtoNameList.get(1).getAge());
        assertNull(employeeDtoNameList.get(0).getFunction());
        assertNull(employeeDtoNameList.get(1).getFunction());
        assertEquals(0.0, employeeDtoNameList.get(0).getExperience());
        assertEquals(0.0, employeeDtoNameList.get(1).getExperience());
        assertNull(employeeDtoNameList.get(0).getEmployerName());
        assertEquals("Test", employeeDtoNameList.get(1).getEmployerName());
        assertEquals("2000-03-21", employeeDtoNameList.get(0).getDateOfBirth());
        assertEquals("2021-03-21", employeeDtoNameList.get(1).getDateOfBirth());
    }

    @Test
    void deleteEmployee() {
        // declar datele
        //rulez programul
        employeeService.deleteEmployee(1);
        //verific programul
        verify(employeeRepository).deleteById(1);
    }

    @Test
    void modifyEmployee_whenEmployerIsNotNull_thenReturnEmployeeWithEmployerId() {
        //declar datele
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Test");
        employee.setDateOfBirth(parseDate("2021-03-21"));
        Employer employer = new Employer();
        employer.setId(2);
        employee.setDateOfBirth(parseDate("2000-03-21"));
        employee.setEmployer(employer);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //rulez programul
        ModelAndView result = employeeService.modifyEmployee(1);

        //verific
        assertEquals("addEmployee", result.getViewName());
        assertTrue(result.getModel().containsKey("employee"));
        EmployeeDto employeeDtoResult = (EmployeeDto) result.getModel().get("employee");
        assertEquals(1, employeeDtoResult.getId());
        assertEquals("Test", employeeDtoResult.getFirstName());
        assertNull(employeeDtoResult.getLastName());
        assertEquals(0.0, employeeDtoResult.getExperience());
        assertEquals(0, employeeDtoResult.getAge());
        assertNull(employeeDtoResult.getFunction());
        assertEquals(1, employeeDtoResult.getId());
        assertEquals(2, employeeDtoResult.getEmployerId());
    }

    @Test
    void modifyEmployee_whenEmployerIsNull_thenReturnEmployeeWithoutEmployerId() {
        //declar datele
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Test");
        employee.setDateOfBirth(parseDate("2021-03-21"));

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //rulez programul
        ModelAndView result = employeeService.modifyEmployee(1);

        //verific
        assertEquals("addEmployee", result.getViewName());
        assertTrue(result.getModel().containsKey("employee"));
        EmployeeDto employeeDtoResult = (EmployeeDto) result.getModel().get("employee");
        assertEquals(1, employeeDtoResult.getId());
        assertEquals("Test", employeeDtoResult.getFirstName());
        assertNull(employeeDtoResult.getLastName());
        assertEquals(0.0, employeeDtoResult.getExperience());
        assertEquals(0, employeeDtoResult.getAge());
        assertNull(employeeDtoResult.getFunction());
        assertEquals(1, employeeDtoResult.getId());
        assertNull(employeeDtoResult.getEmployerId());
    }
}