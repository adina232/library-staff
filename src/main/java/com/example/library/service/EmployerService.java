package com.example.library.service;

import com.example.library.repository.Employer;
import com.example.library.repository.EmployerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class EmployerService {
    private final EmployerRepository employerRepository;

    public EmployerService(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public void addEmployer(Employer employer) {
        employerRepository.save(employer);
    }

    public List<Employer> getEmployers() {
        List<Employer> employers = employerRepository.findAll();
        Collections.sort(employers, new Comparator<Employer>() {
            @Override
            public int compare(Employer o1, Employer o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return employers;
    }


    public void deleteEmployer(Integer id) {
        employerRepository.deleteById(id);
    }
}
