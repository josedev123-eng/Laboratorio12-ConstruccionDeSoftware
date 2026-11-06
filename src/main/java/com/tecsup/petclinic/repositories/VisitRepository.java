package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author jrojas
 *
 */
@Repository
public interface VisitRepository
	extends JpaRepository<Visit, Long> {

	// Fetch visits by vetId
	List<Visit> findByVetId(Integer vetId);

	@Override
	List<Visit> findAll();

}
