package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface CategoryRepository extends JpaRepository<Category, BigInteger> {
}
