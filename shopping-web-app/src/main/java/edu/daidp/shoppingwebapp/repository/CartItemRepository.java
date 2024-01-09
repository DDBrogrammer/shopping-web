package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CartItemRepository extends JpaRepository<CartItem, BigInteger> {
    void deleteAllByCart(Cart cart);
}
