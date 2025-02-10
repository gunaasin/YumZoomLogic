package com.guna.yumzoom.user;

import com.guna.yumzoom.address.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User convertUserRequestToUser(UserSignInRequestDTO userSignInRequestDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = LocalDateTime.now().format(formatter);
        return User.builder()
                .name(userSignInRequestDTO.name())
                .username(userSignInRequestDTO.username())
                .email(userSignInRequestDTO.email())
                .password(bCryptPasswordEncoder.encode(userSignInRequestDTO.password()))
                .phone(userSignInRequestDTO.phone())
                .role(Role.valueOf(userSignInRequestDTO.role()))
                .createdDate(formattedDate)
                .address(new Address())
                .build();
    }

    public UserSignInResponseDTO convertUserToResponse(User user){
        return UserSignInResponseDTO.builder()
                .username(user.getUsername())
                .build();
    }


}
