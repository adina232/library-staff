package com.example.library.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private String function;
    private float experience;

    private Integer employerId;
}
