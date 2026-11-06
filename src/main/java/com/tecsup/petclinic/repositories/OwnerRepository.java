package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Owner.
 *
 * @author jgomezm
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    // Fetch owners by last name
    List<Owner> findByLastName(String lastName);

    // Fetch owners by city
    List<Owner> findByCity(String city);

    // Fetch owners by telephone
    List<Owner> findByTelephone(String telephone);

    // Fetch all owners
    @Override
    List<Owner> findAll();

}
