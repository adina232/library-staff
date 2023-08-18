package com.example.library.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
public class Employee {
    @Column(name = "EMPLOYEE_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "AGE")
    private int age;
    @Column(name = "FUNCTION")
    private String function;
    @Column(name = "EXPERIENCE")
    private float experience;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYER_ID")
    private Employer employer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return age == employee.age && Float.compare(employee.experience, experience) == 0 && Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(function, employee.function) && Objects.equals(employer, employee.employer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, function, experience, employer);
    }
}
