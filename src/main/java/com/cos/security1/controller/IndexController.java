package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    @ApiOperation(value = "Landing Page")
    public String index(){
        return "index";
    }

    @GetMapping("/test/login")
    @ApiOperation(value = "Authorization test")
    public @ResponseBody String loginTest(Authentication authentication
            , @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("/test/login ==================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : "+principalDetails.getUsername());
        System.out.println("userDetail : "+userDetails.getUsername());
        return "Local login test";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication
            , @AuthenticationPrincipal OAuth2User oAuth){
        System.out.println("/test/oauth/login ==================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication : "+oAuth2User.getAttributes());
        System.out.println("oauth2User : "+ oAuth.getAttributes());
        return "OAuth login test";
    }

    // ????????? ????????? ????????? Spring Security ??? ?????? ????????? ?????????
    // SecurityConfig ?????? ?????? ??? ?????? ??????
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user); // ???????????? ???????????? ?????? ????????? ????????? Security ??? ???????????? ??? ??? ??????
        return "redirect:/loginForm"; // redirect ??? ?????? loginForm method ??? ?????????
    }
}