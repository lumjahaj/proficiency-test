package com.elba.proficiencytest.repositories;

import com.elba.proficiencytest.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
