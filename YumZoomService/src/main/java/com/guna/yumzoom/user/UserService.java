package com.guna.yumzoom.user;

import com.guna.yumzoom.Agent.Agent;
import com.guna.yumzoom.Agent.AgentRepo;
import com.guna.yumzoom.Agent.AgentRoles;
import com.guna.yumzoom.cart.Cart;
import com.guna.yumzoom.cart.CartRepo;
import com.guna.yumzoom.restaurant.Restaurant;
import com.guna.yumzoom.restaurant.RestaurantIdGen;
import com.guna.yumzoom.restaurant.RestaurantRepo;
import com.guna.yumzoom.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RestaurantRepo restaurantRepo;
    private final CartRepo cartRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AgentRepo agentRepo;


    public UserSignInResponseDTO saveUser(UserSignInRequestDTO userSignInRequestDTO) {
        var user = userRepo.save(userMapper.convertUserRequestToUser(userSignInRequestDTO));
        // if the role is RESTAURANT then set s restaurant for this user
        if(user.getRole().equals(Role.RESTAURANT)){
            var restaurant = Restaurant.builder()
                    .user(user)
//                    .address(new Address())
                    .restaurantId(RestaurantIdGen.generateRestaurantId())
                    .build();
            restaurantRepo.save(restaurant);
        }else if(user.getRole().equals(Role.CUSTOMER)){
            var cart = Cart.builder()
                    .user(user)
                    .build();
            cartRepo.save(cart);
        } else if (user.getRole().equals(Role.DELIVERY_AGENT)) {
            Agent agent = agentRepo.save(new Agent());
            agent.setAgentId(user.getId());
            agent.setAgentEmail(user.getEmail());
            agent.setAvailable(AgentRoles.ONLINE.name());
            agentRepo.save(agent);
        }
        return userMapper.convertUserToResponse(user);
    }

    public Object verifyUser(UserSignUpRequestDTO userSignUpRequestDTO) {
        System.out.println(userSignUpRequestDTO.email() +" test one user service");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userSignUpRequestDTO.email(),
                        userSignUpRequestDTO.password()
                ));
        var role = userRepo.findByEmail(userSignUpRequestDTO.email()).getRole();
        System.out.println(authentication.isAuthenticated() +" test two user service");
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(userSignUpRequestDTO.email() , List.of(role.name()));
        }
        return ("fail");
    }
}
