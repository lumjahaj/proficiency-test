package com.elba.proficiencytest.repositories;

import com.elba.proficiencytest.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCityAndNeighborhood(String city, String neighborhood);

}
