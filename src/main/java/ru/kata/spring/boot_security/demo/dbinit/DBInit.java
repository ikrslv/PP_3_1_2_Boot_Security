package ru.kata.spring.boot_security.demo.dbinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {

    UserRepository userRepository;
    RoleRepository roleRepository;
    RoleService roleService;

    @Autowired
    public DBInit(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @PostConstruct
    private void initDB() {
        Set<Role> rolesList = new HashSet<>();
        Role role1 = new Role(1L, "ROLE_ADMIN");
        Role role2 = new Role(2L, "ROLE_USER");
        rolesList.add(role1);
        rolesList.add(role2);
        //password: user1
        User user1 = new User(1L, "user1", "User1", "Userovich1"
                , 25, "$2a$12$qRghJRB70o/1m4PfgiaNyeTcILh2F.c7PLV/PSXbuqN7B8EIyKKk2"
                , roleRepository.findByName("ROLE_ADMIN"));
        //password: user2
        User user2 = new User(2L, "user2", "User2", "Userovich2"
                , 19, "$2a$12$HfC/4nF95dQxMCyVPhVPmeONVpv4nZxcwoesK1BBVjEyumoSHxzHy", roleRepository.findByName("ROLE_USER"));
        roleRepository.save(role1);
        roleRepository.save(role2);
        userRepository.save(user1);
        userRepository.save(user2);

    }
}
