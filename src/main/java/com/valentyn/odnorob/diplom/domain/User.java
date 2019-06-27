package com.valentyn.odnorob.diplom.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    @Email
    private String email;

    private String password;

    @Size(min = 2, max = 32)
    private String name;

    @Size(min = 3)
    private String surname;

    private String country;

    private String city;

    private String street;

    private String numberOfHouse;

    private String phone;

    @Size(min = 9, max = 9)
    private String nip;

    @Size(min = 10, max = 10)
    private String pesel;

    private boolean isEnabled;

    @DBRef
    private Set<Role> roles;

    private List<Vacancy> vacancies;

    private String activationCode;

    public List<Vacancy> getVacancies() {
        if (vacancies == null) {
            vacancies = new ArrayList<>();
        }
        return vacancies;
    }


}
