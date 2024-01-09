package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.Order;
import edu.daidp.shoppingwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface OrderRepository  extends JpaRepository<Order, BigInteger> {
}
