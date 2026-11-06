package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author egalindo
 *
 */
@Repository
public interface OwnerRepository
	extends JpaRepository<Owner, Long> {

	// Fetch owners by lastName
	List<Owner> findByLastName(String lastName);

	@Override
	List<Owner> findAll();

}
