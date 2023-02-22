package com.example.backend.service;

import com.example.backend.exception.NoRoleException;
import com.example.backend.exception.NoUserException;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.google.cloud.storage.testing.RemoteStorageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role createRole(Role role){
        return roleRepository.save(role);
    }

    public ResponseEntity<Role> getRoleById(Integer roleId){
        Role role= roleRepository.findById(roleId)
                .orElseThrow(()-> new RemoteStorageHelper.StorageHelperException("Role doesnt exist"));
        return ResponseEntity.ok(role);
    }

    public ResponseEntity<String> assignUserToRole(Integer userId,Integer roleId) throws NoRoleException, NoUserException {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(roleOptional.isEmpty())
            throw new NoRoleException();
        else if(userOptional.isEmpty()){
            throw new NoUserException();
        }
        else {
            userOptional.get().setRole(roleRepository.getRoleById(roleId));
            userRepository.save(userOptional.get());
            return ResponseEntity.ok("the association was made successfuly.");
        }
    }
}
