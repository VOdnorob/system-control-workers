package com.valentyn.odnorob.diplom.service;

import com.valentyn.odnorob.diplom.domain.Vacancy;
import com.valentyn.odnorob.diplom.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    public void save(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }

    public Vacancy acceptVacancy(Vacancy vacancy){
        vacancy.setIsAccept(true);
        return vacancyRepository.save(vacancy);
    }

    public Vacancy findByPosition(String position) {
        return vacancyRepository.findByPosition(position);
    }

}
