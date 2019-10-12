package com.valentyn.odnorob.diplom.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "vacancy")
public class Vacancy {

    @Id
    private String id;

    private String companyName;

    private String position;

    private String city;

    private String country;

    private String description;

    private int price;

    private boolean isAccept = false;

}
