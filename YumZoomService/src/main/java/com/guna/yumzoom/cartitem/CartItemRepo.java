package com.guna.yumzoom.cartitem;
import com.guna.yumzoom.cart.Cart;
import com.guna.yumzoom.menu.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
    @Query("SELECT c FROM CartItem c WHERE c.cart = :cart AND c.food = :food")
    CartItem findByCartAndFood(@Param("cart") Cart cart, @Param("food") Food food);

}
