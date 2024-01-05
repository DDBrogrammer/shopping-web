package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
}
