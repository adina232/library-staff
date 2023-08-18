package com.example.library.service;

import com.example.library.repository.Employer;
import com.example.library.repository.EmployerRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class EmployerServiceTest {

    EmployerRepository employerRepository = mock(EmployerRepository.class);
    EmployerService employerService = new EmployerService(employerRepository);

    @Test
    void addEmployer() {
        // declar datele cu care lucrez
        Employer employer = new Employer();

        // rulez programul
        employerService.addEmployer(employer);

        // verific programul
        verify(employerRepository).save(employer);
    }

    @Test
    void getEmployers() {
        // declar datele cu care lucrez
        Employer e1 = new Employer();
        Employer e2 = new Employer();
        e1.setName("a");
        e2.setName("b");
        List<Employer> employerList = new ArrayList<>();
        employerList.add(e2);
        employerList.add(e1);
        when(employerRepository.findAll()).thenReturn(employerList);

        // rulez programul
        List<Employer> result = employerService.getEmployers();

        //verific programul
        assertTrue(result.contains(e1));
        assertTrue(result.contains(e2));
        assertEquals(e1, result.get(0));
        assertEquals(e2, result.get(1));
    }

    @Test
    void deleteEmployer() {
        //declar datele

        //rulez programul
        employerService.deleteEmployer(1);

        //verific programul
        verify(employerRepository).deleteById(1);
    }
}