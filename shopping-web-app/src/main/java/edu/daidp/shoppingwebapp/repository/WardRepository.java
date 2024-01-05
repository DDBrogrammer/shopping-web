package edu.daidp.shoppingwebapp.repository;

import edu.daidp.shoppingwebapp.entity.District;
import edu.daidp.shoppingwebapp.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    List<Ward> findAllByDistrict(District district);

    Optional<Ward> findFirstByDistrict(District district);


    Optional<Ward> findByCode(long code);
}

