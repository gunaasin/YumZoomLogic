package com.guna.yumzoom.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant,Integer> {

    @Query("SELECT r FROM Restaurant r WHERE r.restaurantId = :restaurantId")
    Restaurant findByRestaurantId(@Param("restaurantId") String restaurantId);


    @Query("SELECT r FROM Restaurant r WHERE r.user.id = :adminId")
    Restaurant findByRestaurantAdminId(@Param("adminId") Integer adminId);

}
