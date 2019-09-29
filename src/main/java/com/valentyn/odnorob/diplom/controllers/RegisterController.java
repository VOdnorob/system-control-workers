package com.valentyn.odnorob.diplom.controllers;

import com.valentyn.odnorob.diplom.domain.User;
import com.valentyn.odnorob.diplom.repository.UserRepository;
import com.valentyn.odnorob.diplom.service.EmailSender;
import com.valentyn.odnorob.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller("/api/user/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/signupEmployer")
    public ModelAndView signupEmployer() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signupEmployer");
        return modelAndView;
    }

    @PostMapping("/signupEmployer")
    public ModelAndView createNewUserEmployer(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userFoundEmail = userService.findUserByEmail(user.getEmail());
        User userFoundNip = userRepository.findByNip(user.getNip());
        User userFoundPesel = userRepository.findByPesel(user.getPesel());

        if (userFoundEmail != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (userFoundNip != null) {
            bindingResult.
                    rejectValue("nip", "error.user",
                            "There is already a user registered with this nip provided");
        }

        if (userFoundPesel != null) {
            bindingResult
                    .rejectValue("pesel", "user.error",
                            "There is already a user registered with the pesel provided");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signupEmployer");
        } else {
            userService.saveUserEmployer(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }

        return modelAndView;
    }

    @GetMapping("/signupWorker")
    public ModelAndView signupWorker() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signupWorker");
        return modelAndView;
    }

    @PostMapping("/signupWorker")
    public ModelAndView createNewUserWorker(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userFoundEmail = userService.findUserByEmail(user.getEmail());
        User userFoundNip = userRepository.findByNip(user.getNip());
        User userFoundPesel = userRepository.findByPesel(user.getPesel());

        if (userFoundEmail != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (userFoundNip != null) {
            bindingResult.
                    rejectValue("nip", "error.user",
                            "There is already a user registered with this nip provided");
        }

        if (userFoundPesel != null) {
            bindingResult
                    .rejectValue("pesel", "user.error",
                            "There is already a user registered with the pesel provided");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signupWorker");
        } else {

            userService.saveUserWorker(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    @GetMapping("/activate/{code}")
    public ModelAndView activateCode(@PathVariable String code){
        ModelAndView modelAndView = new ModelAndView();
        boolean isActivated = userService.activateUser(code);

        if (isActivated){
            modelAndView.addObject("message", "User successfully activated");
        }else {
            modelAndView.addObject("message", "Activation code is not found");
        }

        modelAndView.setViewName("login");
        return modelAndView;
    }

}
