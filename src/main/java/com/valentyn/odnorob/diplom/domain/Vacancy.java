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

    private UUID uuid;

    private String position;

    private String companyName;

    private String city;

    private String country;

    private String description;

    private int price;

    private Boolean isAccept = false;

}
