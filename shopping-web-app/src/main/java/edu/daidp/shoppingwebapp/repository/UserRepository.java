package edu.daidp.shoppingwebapp.repository;


import edu.daidp.shoppingwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, BigInteger> {
    User findTopByEmail(String email);


    Optional<User> findByEmail(String email);
}
