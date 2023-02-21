package com.example.backend.controller;

import com.example.backend.exception.NoRoleException;
import com.example.backend.exception.NoUserException;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.RoleService;
import com.google.cloud.storage.testing.RemoteStorageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private  final RoleService roleService;
    @GetMapping("/showAll")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @GetMapping("/showById/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer roleId){
        return roleService.getRoleById(roleId);
    }
    @PostMapping("/create")
    public Role createRole(@RequestBody Role role){
        return roleService.createRole(role);
    }

//    @PutMapping("/assignUserToRole/{userId}/{roleId}")
//    public void assignUserToRole(@PathVariable Integer roleId, @PathVariable Integer userId) throws NoRoleException, NoUserException{
//        roleService.assignUserToRole(roleId,userId);
//    }
    @PutMapping("/add/{userId}/{roleId}")
    public void assignUserToRole(@PathVariable Integer userId,@PathVariable  Integer roleId) throws NoRoleException, NoUserException {
        roleService.assignUserToRole(userId,roleId);
    }

}
