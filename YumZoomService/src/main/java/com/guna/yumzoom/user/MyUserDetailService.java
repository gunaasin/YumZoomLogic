package com.guna.yumzoom.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String mailId) throws UsernameNotFoundException {
        System.out.println(mailId);
        var user = userRepo.findByEmail(mailId);
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("no user is there in this name");
        }
        return new UserPrincipal(user);
    }
}
