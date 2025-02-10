package com.guna.yumzoom.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignInRequestDTO userSignInRequestDTO){
        try{

            System.out.println(userSignInRequestDTO);
            userService.saveUser(userSignInRequestDTO);
            return ResponseEntity.ok().body(Map.of("message","Account created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","Invalid"));
        }
    }

    @PostMapping("signin")
    public ResponseEntity<?> verifyUser( @RequestBody UserSignUpRequestDTO userSignUpRequestDTO){

        try{
            System.out.println(userSignUpRequestDTO);
            String token =(String)userService.verifyUser(userSignUpRequestDTO);
            return ResponseEntity.ok().body(Map.of("token",token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message","unauthorized"));
        }
    }





}
