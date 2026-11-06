package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la entidad Owner.
 *
 * @author jgomezm
 */
@RestController
@RequestMapping("/owners")
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Get all owners
     *
     * @return lista de owners
     */
    @GetMapping
    public ResponseEntity<List<OwnerDTO>> findAllOwners() {

        List<OwnerDTO> owners = ownerService.findAll();
        log.info("owners: {}", owners);

        return ResponseEntity.ok(owners);
    }

    /**
     * Find owner by id
     *
     * @param id identificador del owner
     * @return owner encontrado o 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(ownerService.findById(id));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create owner
     *
     * @param ownerDTO datos del owner
     * @return owner creado con 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OwnerDTO> create(@RequestBody OwnerDTO ownerDTO) {

        OwnerDTO newOwner = ownerService.create(ownerDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newOwner);
    }

    /**
     * Update owner
     *
     * @param ownerDTO datos del owner
     * @param id identificador del owner
     * @return owner actualizado o 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<OwnerDTO> update(@RequestBody OwnerDTO ownerDTO, @PathVariable Long id) {

        try {

            OwnerDTO updateOwner = ownerService.findById(id);

            updateOwner.setFirstName(ownerDTO.getFirstName());
            updateOwner.setLastName(ownerDTO.getLastName());
            updateOwner.setAddress(ownerDTO.getAddress());
            updateOwner.setCity(ownerDTO.getCity());
            updateOwner.setTelephone(ownerDTO.getTelephone());

            return ResponseEntity.ok(ownerService.update(updateOwner));

        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete owner by id
     *
     * @param id identificador del owner
     * @return mensaje de confirmación o 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        try {
            ownerService.delete(id);
            return ResponseEntity.ok(" Delete ID :" + id);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
