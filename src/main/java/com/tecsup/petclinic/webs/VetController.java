package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author karim
 *
 */
@RestController
@Slf4j
public class VetController {

	private VetService vetService;

	private VetMapper mapper;

	public VetController(VetService vetService, VetMapper mapper) {
		this.vetService = vetService;
		this.mapper = mapper;
	}

	/**
	 * Get all vets
	 *
	 * @return
	 */
	@GetMapping(value = "/vets")
	public ResponseEntity<List<VetDTO>> findAllVets() {

		List<Vet> vets = vetService.findAll();
		vets.forEach(item -> log.info("Vet >>  {} ", item));

		List<VetDTO> vetsTO = this.mapper.mapToDtoList(vets);

		return ResponseEntity.ok(vetsTO);
	}

	/**
	 * Create vet
	 *
	 * @param vetTO
	 * @return
	 */
	@PostMapping(value = "/vets")
	ResponseEntity<VetDTO> create(@RequestBody VetDTO vetTO) {

		VetDTO newVetTO = vetService.create(vetTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(newVetTO);
	}

	/**
	 * Find vet by id
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/vets/{id}")
	ResponseEntity<VetDTO> findById(@PathVariable Integer id) {

		VetDTO vetDto = null;

		try {
			vetDto = vetService.findById(id);
		} catch (VetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(vetDto);
	}

	/**
	 * Update vet
	 *
	 * @param vetTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/vets/{id}")
	ResponseEntity<VetDTO> update(@RequestBody VetDTO vetTO, @PathVariable Integer id) {

		VetDTO updateVetDto = null;

		try {
			updateVetDto = vetService.findById(id);

			updateVetDto.setFirstName(vetTO.getFirstName());
			updateVetDto.setLastName(vetTO.getLastName());
			updateVetDto.setEmail(vetTO.getEmail());
			updateVetDto.setPhone(vetTO.getPhone());
			updateVetDto.setActive(vetTO.getActive());

			vetService.update(updateVetDto);

		} catch (VetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updateVetDto);
	}

	/**
	 * Delete vet by id
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/vets/{id}")
	ResponseEntity<String> delete(@PathVariable Integer id) {

		try {
			vetService.delete(id);
			return ResponseEntity.ok(" Delete ID :" + id);
		} catch (VetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
