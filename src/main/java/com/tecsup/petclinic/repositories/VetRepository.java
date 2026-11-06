package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author karim
 *
 */
@Repository
public interface VetRepository
	extends JpaRepository<Vet, Integer> {

	// Fetch vets by last name
	List<Vet> findByLastName(String lastName);

	@Override
	List<Vet> findAll();

}
