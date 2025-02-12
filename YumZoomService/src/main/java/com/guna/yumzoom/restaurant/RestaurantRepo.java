package com.guna.yumzoom.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant,Integer> {

    @Query("SELECT r FROM Restaurant r WHERE r.restaurantId = :restaurantId")
    Restaurant findByRestaurantId(@Param("restaurantId") String restaurantId);


    @Query("SELECT r FROM Restaurant r WHERE r.user.id = :adminId")
    Restaurant findByRestaurantAdminId(@Param("adminId") Integer adminId);


    @Query("""
    SELECT DISTINCT r.name FROM Restaurant r
    WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%'))
    ORDER BY r.name
    """)
    List<String> findRestaurantSuggestions(String query);


    @Query("SELECT DISTINCT r.cusineType FROM  Restaurant r WHERE LOWER(r.cusineType) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY r.cusineType")
    List<String> findCusBasedRestaurant(String query);


    @Query("SELECT r from Restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> findRestaurantsByName(String query);

    @Query("SELECT r from Restaurant r WHERE LOWER(r.cusineType) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> findRestaurantByCusType(String query);
}
