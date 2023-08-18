package com.example.library.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Employer")
@Getter
@Setter
public class Employer {
    @Id
    @Column(name = "EMPLOYER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "EMPLOYER_NAME")
    private String name;
    @OneToMany(
            mappedBy = "employer",
            orphanRemoval = true
    )
    private List<Employee> employees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employer)) return false;
        Employer employer = (Employer) o;
        return Objects.equals(id, employer.id) && Objects.equals(name, employer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
