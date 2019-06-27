package com.valentyn.odnorob.diplom.service;

import com.valentyn.odnorob.diplom.domain.Role;
import com.valentyn.odnorob.diplom.domain.User;
import com.valentyn.odnorob.diplom.repository.RoleRepository;
import com.valentyn.odnorob.diplom.repository.UserRepository;
import com.valentyn.odnorob.diplom.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailSender emailSender;


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUserEmployer(User user) {
        user.setActivationCode(UUID.randomUUID().toString());
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole("EMPLOYER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("Hello %s! \n" +
                            "You registered how employer. \n"
                            + "Welcome to our site. Please visit next link: http://localhost/activate/%s",
                    user.getName(),
                    user.getActivationCode());
            emailSender.sendEmail(user.getEmail(),  message,"Registered user");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUserWorker(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole("WORKER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("Hello %s! \n " +
                            "You registered how worker. \n"
                            + "Welcome to our site. Please visit next link: http://localhost/activate/%s",
                    user.getName(),
                    user.getActivationCode());
            emailSender.sendEmail(user.getEmail(),  message,"Registered user");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);


    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null){
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        return new ArrayList<>(roles);
    }


    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


}