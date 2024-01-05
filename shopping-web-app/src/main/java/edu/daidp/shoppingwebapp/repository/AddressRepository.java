package edu.daidp.shoppingwebapp.repository;


import edu.daidp.shoppingwebapp.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface AddressRepository extends JpaRepository<Address, BigInteger> {
}
