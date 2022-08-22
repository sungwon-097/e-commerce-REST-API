package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Secured("ROLE_USER") // 단일 권한
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public @ResponseBody User user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        String username = principalDetails.getUsername();
        System.out.println(username);
        return userRepository.findByUsername(username);
    }

    @DeleteMapping("/")
    public @ResponseBody String deleteAccount(@AuthenticationPrincipal PrincipalDetails principalDetails){
        userRepository.delete(principalDetails.getUserEntity());
        List<User> user = userRepository.findAll();
        return "delete user";
    }

    @PatchMapping("/password")
    public @ResponseBody String updatePassword(
            @AuthenticationPrincipal PrincipalDetails principalDetails ,String username, String password){
        String newPassword = bCryptPasswordEncoder.encode(password);
        User userEntity = userRepository.findByUsername(username);
        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);

        return "update password";
    }
}