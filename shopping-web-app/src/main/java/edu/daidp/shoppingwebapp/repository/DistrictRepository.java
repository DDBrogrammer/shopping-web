package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.District;
import edu.daidp.shoppingwebapp.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {
    List<District> findAllByProvince(Province province);
    Optional<District> findByCode(long code);
}
