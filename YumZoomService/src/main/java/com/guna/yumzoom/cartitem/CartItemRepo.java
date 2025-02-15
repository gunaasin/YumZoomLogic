package com.guna.yumzoom.cartitem;
import com.guna.yumzoom.cart.Cart;
import com.guna.yumzoom.menu.Food;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
    @Query("SELECT c FROM CartItem c WHERE c.cart = :cart AND c.food = :food")
    CartItem findByCartAndFood(@Param("cart") Cart cart, @Param("food") Food food);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteAllCartItems(@Param("cartId") int cartId);

}
