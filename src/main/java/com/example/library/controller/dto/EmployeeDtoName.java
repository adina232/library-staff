package com.example.library.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDtoName {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private String function;
    private float experience;

    private String employerName;
}
