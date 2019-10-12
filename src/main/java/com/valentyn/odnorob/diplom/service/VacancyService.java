package com.valentyn.odnorob.diplom.service;

import com.valentyn.odnorob.diplom.domain.User;
import com.valentyn.odnorob.diplom.domain.Vacancy;
import com.valentyn.odnorob.diplom.repository.UserRepository;
import com.valentyn.odnorob.diplom.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService userService;

    public void save(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }


    public Vacancy acceptVacancy(String vacancyId) throws RuntimeException {
        Vacancy vacancy = vacancyRepository.findById(vacancyId);
        String userEmail = userService.getLoginUser();
        User user = userRepository.findByEmail(userEmail);
        if (userService.allowUsed()){
            if (!vacancy.isAccept()){
                vacancy.setAccept(true);
                List<Vacancy> vacancyList = new ArrayList<>();
                vacancyList.add(vacancy);
                user.setVacancies(vacancyList);
                vacancyRepository.save(vacancy);
                userRepository.save(user);
                return vacancy;
            }
            return vacancy;
        } else {
            throw new RuntimeException("You can`t do it");
        }

    }

    public Iterable<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }

    public Vacancy findByPosition(String position) {
        return vacancyRepository.findByPosition(position);
    }

}
