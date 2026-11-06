package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

import java.util.List;

/**
 *
 * @author egalindo
 *
 */
public interface OwnerService {

    /**
     *
     * @param ownerDTO
     * @return
     */
    OwnerDTO create(OwnerDTO ownerDTO);

    /**
     *
     * @param ownerDTO
     * @return
     */
    OwnerDTO update(OwnerDTO ownerDTO);

    /**
     *
     * @param id
     * @throws OwnerNotFoundException
     */
    void delete(Integer id) throws OwnerNotFoundException;

    /**
     *
     * @param id
     * @return
     * @throws OwnerNotFoundException
     */
    OwnerDTO findById(Integer id) throws OwnerNotFoundException;

    /**
     *
     * @return
     */
    List<Owner> findAll();
}
