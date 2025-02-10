package com.guna.yumzoom.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepo extends JpaRepository<Food,Integer> {
    @Query("SELECT f FROM Food f WHERE f.restaurant.id = :restaurantId")
    List<Food> findAllFoodsByRestaurantId(@Param("restaurantId") int restaurantId);

    @Query("SELECT f FROM Food f WHERE f.id = :foodId")
    Optional<Food> findByIdAndRestaurantId(@Param("foodId") int foodId);
}
