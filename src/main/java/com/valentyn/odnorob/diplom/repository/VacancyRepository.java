package com.valentyn.odnorob.diplom.repository;

import com.valentyn.odnorob.diplom.domain.Vacancy;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VacancyRepository extends CrudRepository<Vacancy, Integer> {

    @Override
    void delete(Vacancy delete);

    Vacancy findByPosition(String position);

    Vacancy findById(String id);

}