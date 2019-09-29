package com.valentyn.odnorob.diplom.controllers;

import com.valentyn.odnorob.diplom.domain.Vacancy;
import com.valentyn.odnorob.diplom.repository.VacancyRepository;
import com.valentyn.odnorob.diplom.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RestController("/api/user/vacancy")
public class VacancyController {

    private VacancyService vacancyService;

    private final VacancyRepository vacancyRepository;

    @Autowired
    public VacancyController(VacancyService vacancyService, VacancyRepository vacancyRepository) {
        this.vacancyService = vacancyService;
        this.vacancyRepository = vacancyRepository;
    }

    @PreAuthorize(value = "hasRole('EMPLOYER')")
    @GetMapping("/addVacancy")
    public ModelAndView vacancies() {
        ModelAndView modelAndView = new ModelAndView();
        Vacancy vacancy = new Vacancy();
        modelAndView.addObject("vacancy", vacancy);
        modelAndView.setViewName("addVacancies");
        return modelAndView;
    }

    @PreAuthorize(value = "hasRole('EMPLOYER')")
    @PostMapping("/addVacancy")
    public ModelAndView addVacancies(@Valid Vacancy vacancy, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        vacancyService.save(vacancy);
        modelAndView.addObject("vacancy", vacancy);
        modelAndView.addObject("successMassage", "You added vacancy");
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @PreAuthorize(value = "hasAnyRole('EMPLOYER', 'WORKER')")
    @GetMapping("/getAllVacancies")
    public ModelAndView getAllVacancies(Vacancy vacancy) {
        ModelAndView modelAndView = new ModelAndView();
        Iterable<Vacancy> allVacancies = vacancyRepository.findAll();
        modelAndView.addObject("vacancies", allVacancies);
        modelAndView.setViewName("listVacancies");
        return modelAndView;
    }


    @PreAuthorize("hasRole('WORKER')")
    @PostMapping("/takeVacancy")
    public ModelAndView acceptVacancy(Vacancy vacancy) {
        ModelAndView modelAndView = new ModelAndView();
        Vacancy acceptVacancy = vacancyService.acceptVacancy(vacancy);
        modelAndView.addObject("vacancy", acceptVacancy);
        modelAndView.setViewName("addVacancies");
        return modelAndView;
    }

}
