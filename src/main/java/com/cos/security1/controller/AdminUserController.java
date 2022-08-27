package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Secured("ROLE_ADMIN") // 단일 권한
@RequestMapping("admin/user")
@Api(tags = "ROLE : Admin")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    @ApiOperation(value = "Find all User Entities")
    public @ResponseBody List<User> retrieveAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/userid={id}")
    @ApiOperation(value = "Find user by id")
    public @ResponseBody Optional<User> retrieveUserid(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);

        if(user == null){
            throw new UsernameNotFoundException(String.format("Id[%s] not found", id));
        }
        return user;
    }
    @GetMapping("/username={username}")
    @ApiOperation(value = "Find user by username")
    public @ResponseBody Optional<User> retrieveUsername(@PathVariable String username){
        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
        return Optional.of(user);
    }
    @GetMapping("/email={email}")
    @ApiOperation(value = "Find user by email")
    public @ResponseBody Optional<User> retrieveEmail(@PathVariable String email){
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException(String.format("Email[%s] not found", email));
        }
        return Optional.of(user);
    }
    @GetMapping("/provider={provider}")
    @ApiOperation(value = "Find user by social provider")
    public @ResponseBody
    Optional<List<User>> retrieveProvider(@PathVariable String provider){
        List<User> user = (List<User>) userRepository.findByProvider(provider);

        if(user == null){
            throw new UsernameNotFoundException(String.format("Provider[%s] not found", provider));
        }
        return Optional.of(user);
    }
    @GetMapping("/role={role}")
    @ApiOperation(value = "Find user by role")
    public @ResponseBody Optional<List<User>> retrieveRole(@PathVariable String role){
        List<User> user = (List<User>) userRepository.findByRole(role);

        if(user == null){
            throw new UsernameNotFoundException(String.format("Provider[%s] not found", role));
        }
        return Optional.of(user);
    }
    @DeleteMapping("/id={id}")
    @ApiOperation(value = "Delete user by id")
    public @ResponseBody Optional<List<User>> deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        List<User> user = userRepository.findAll();
        return Optional.of(user);
    }

    @PatchMapping("/role/{role}/{username}")
    public @ResponseBody String changeRole(@PathVariable String role, @PathVariable String username){
        User user = userRepository.findByUsername(username);
        user.setRole(role);
        userRepository.save(user);
        return "changed";
    }
}