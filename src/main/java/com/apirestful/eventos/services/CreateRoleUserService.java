package com.apirestful.eventos.services;

import com.apirestful.eventos.dto.CreateUserRoleDTO;
import com.apirestful.eventos.models.Role;
import com.apirestful.eventos.models.User;
import com.apirestful.eventos.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreateRoleUserService {


    final UserRepository userRepository;


    final CreateUserService userService;

    public CreateRoleUserService(UserRepository userRepository, CreateUserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public User execute(CreateUserRoleDTO createUserRoleDTO) {

        Optional<User> userExists = userRepository.findById(createUserRoleDTO.getIdUser());
        List<Role> roles = new ArrayList<>();

        if (userExists.isEmpty()) {
            throw new Error("User does not exists!");
        }

        roles = createUserRoleDTO.getIdsRoles().stream().map(role -> {
            return new Role(role);
        }).collect(Collectors.toList());

        User user = userExists.get();

        user.setRoles(roles);

        userRepository.save(user);

        return user;

    }
}
