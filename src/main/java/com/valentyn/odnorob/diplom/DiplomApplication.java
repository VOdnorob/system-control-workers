package com.valentyn.odnorob.diplom;

import com.valentyn.odnorob.diplom.domain.Role;
import com.valentyn.odnorob.diplom.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiplomApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {

        return args -> {

            Role adminRole = roleRepository.findByRole("EMPLOYER");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("EMPLOYER");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByRole("WORKER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("WORKER");
                roleRepository.save(newUserRole);
            }
        };

    }

}
