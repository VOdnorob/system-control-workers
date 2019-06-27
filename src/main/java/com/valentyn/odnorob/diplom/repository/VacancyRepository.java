package com.valentyn.odnorob.diplom.repository;

import com.valentyn.odnorob.diplom.domain.Vacancy;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VacancyRepository extends CrudRepository<Vacancy, String> {

    @Override
    void delete(Vacancy delete);

    Vacancy findByPosition(String position);

    Optional<Vacancy> findById(String id);
}