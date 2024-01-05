package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.Country;
import edu.daidp.shoppingwebapp.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province,Long>
{
  List<Province> findAllByCountry(Country country);

  Optional<Province> findByCode(long code);
}
