package com.apirestful.eventos.controller;

import com.apirestful.eventos.dto.CreateUserRoleDTO;
import com.apirestful.eventos.models.Role;
import com.apirestful.eventos.models.User;
import com.apirestful.eventos.services.CreateRoleUserService;
import com.apirestful.eventos.services.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    CreateUserService userService;

    @Autowired
    CreateRoleUserService roleService;

    // CRIA UM USER
    @PostMapping( "/create")
    public User save(@RequestBody User user) {
        return userService.execute(user);
    }

    //     CRIANDO USER COM ROLE('ADMIN')
    @PostMapping( "/createAdmin")
    public User saveAdmin(@RequestBody User user)
    {
        User userModel = userService.execute(user);
        CreateUserRoleDTO dto = new CreateUserRoleDTO();
        dto.setIdUser(userModel.getUserId());
        List<UUID> lista = new ArrayList<>();
        UUID uuidObj = UUID.fromString("b60974bc-7fe1-44c9-a874-cd989b961ec9");
        lista.add(uuidObj);
        dto.setIdsRoles(lista);
        roleService.execute(dto);
        return userModel;
    }

    //     CRIANDO USER COM ROLE('USER')
    @PostMapping( "/createUSER")
    public User saveUser(@RequestBody User user)
    {
        User userModel = userService.execute(user);
        CreateUserRoleDTO dto = new CreateUserRoleDTO();
        dto.setIdUser(userModel.getUserId());
        List<UUID> lista = new ArrayList<>();
        UUID uuidObj = UUID.fromString("81e47e1c-a566-11ec-b909-0242ac120002");
        lista.add(uuidObj);
        dto.setIdsRoles(lista);
        roleService.execute(dto);
        return userModel;
    }

    // ADCIONA ROLE EM USER
    @PostMapping(value = "/role")
    public User role(@RequestBody CreateUserRoleDTO createUserRoleDTO)
    {
        return roleService.execute(createUserRoleDTO);
    }
}
