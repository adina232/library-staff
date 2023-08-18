package com.example.library.service;

import com.example.library.controller.dto.EmployeeDto;
import com.example.library.controller.dto.EmployeeDtoName;
import com.example.library.repository.Employee;
import com.example.library.repository.EmployeeRepository;
import com.example.library.repository.Employer;
import com.example.library.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;

    public void addEmployee(EmployeeDto employee) {

        Employee employeeEntity = new Employee();
        employeeEntity.setId(employee.getId());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setAge(employee.getAge());
        employeeEntity.setFunction(employee.getFunction());
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setExperience(employeeEntity.getExperience());
        Optional<Employer> employer = employerRepository.findById(employee.getEmployerId());

        if (employer.isPresent()) {
            employeeEntity.setEmployer(employer.get());
            employeeRepository.save(employeeEntity);
        } else {
            throw new RuntimeException("Employer not found by id " + employee.getEmployerId());
        }
    }

    public List<EmployeeDtoName> getEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeDtoName> employeeDtoNameList = new ArrayList<>();
        for (Employee employee : employeeList) {
            EmployeeDtoName employeeDtoName = new EmployeeDtoName();
            employeeDtoName.setId(employee.getId());
            employeeDtoName.setFirstName(employee.getFirstName());
            employeeDtoName.setLastName(employee.getLastName());
            employeeDtoName.setAge(employee.getAge());
            employeeDtoName.setFunction(employee.getFunction());
            employeeDtoName.setExperience(employee.getExperience());
            if (employee.getEmployer() != null) {
                employeeDtoName.setEmployerName(employee.getEmployer().getName());
            }

            employeeDtoNameList.add(employeeDtoName);
        }
        Collections.sort(employeeDtoNameList, new Comparator<EmployeeDtoName>() {
            @Override
            public int compare(EmployeeDtoName o1, EmployeeDtoName o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });
        return employeeDtoNameList;
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    public ModelAndView modifyEmployee(Integer id) {
        ModelAndView mav = new ModelAndView("addEmployee");
        Employee employee = employeeRepository.findById(id).get();

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setAge(employee.getAge());
        employeeDto.setFunction(employee.getFunction());
        employeeDto.setExperience(employee.getExperience());
        if (employee.getEmployer() != null) {
            employeeDto.setEmployerId(employee.getEmployer().getId());
        }

        mav.addObject("employee", employeeDto);
        return mav;
    }
}
