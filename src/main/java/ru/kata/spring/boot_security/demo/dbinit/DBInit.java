package ru.kata.spring.boot_security.demo.dbinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {

    UserRepository userRepository;
    RoleRepository roleRepository;
    RoleService roleService;
    UserService userService;

    @Autowired
    public DBInit(UserRepository userRepository, RoleRepository roleRepository
            , RoleService roleService, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    private void initDB() {
        Set<Role> rolesList = new HashSet<>();
        Role role1 = new Role(1L, "ROLE_ADMIN");
        Role role2 = new Role(2L, "ROLE_USER");
        rolesList.add(role1);
        rolesList.add(role2);
        //password: user1
        User user1 = new User(1L, "User1", "Userovich1", 29, "user1@mail.ru"
                , "$2a$12$4srOVnNE1AM28N2dJJIIz.tvtHGOvRWgA9hBuOsfGeVJL7URvM/gm"
                , roleRepository.findByName("ROLE_ADMIN"));
        //password: user2
        User user2 = new User(2L, "User2", "Userovich2", 23,  "user2@mail.ru"
                ,"$2a$12$38L738ieITbjhF8gDfS4cOCFkvGBvW4iabEZ.T6XR/LGaIj.PNpEO"
                , roleRepository.findByName("ROLE_USER"));
        roleRepository.save(role1);
        roleRepository.save(role2);
        userRepository.save(user1);
        userRepository.save(user2);
    }
}
