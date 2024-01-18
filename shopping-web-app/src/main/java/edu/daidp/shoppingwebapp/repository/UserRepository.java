package edu.daidp.shoppingwebapp.repository;


import edu.daidp.shoppingwebapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, BigInteger> {
    User findTopByEmail(String email);


    Optional<User> findByEmail(String email);

    @Query("select distinct u from User u join fetch u.addresses")
    Page<User> findAll(Pageable pageable);
}
