package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.Address;
import edu.daidp.shoppingwebapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProductRepository extends JpaRepository<Product, BigInteger> {
}

