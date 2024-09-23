package com.example.myapp.config.startup;

import com.example.myapp.model.RoleEnum;
import com.example.myapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleService roleService;

    @Autowired
    public RoleInitializer(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        roleService.createRoleIfNotExists(RoleEnum.USER);
        roleService.createRoleIfNotExists(RoleEnum.TEMPORARY_USER);
        roleService.createRoleIfNotExists(RoleEnum.ADMIN);
        roleService.createRoleIfNotExists(RoleEnum.SUPER_ADMIN);
        roleService.loadRoles();
    }
}
