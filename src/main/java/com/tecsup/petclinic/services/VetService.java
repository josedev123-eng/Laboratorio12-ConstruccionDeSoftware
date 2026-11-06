package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

import java.util.List;

/**
 *
 * @author karim
 *
 */
public interface VetService {

    /**
     *
     * @param vetDTO
     * @return
     */
    VetDTO create(VetDTO vetDTO);

    /**
     *
     * @param vetDTO
     * @return
     */
    VetDTO update(VetDTO vetDTO);

    /**
     *
     * @param id
     * @throws VetNotFoundException
     */
    void delete(Integer id) throws VetNotFoundException;

    /**
     *
     * @param id
     * @return
     * @throws VetNotFoundException
     */
    VetDTO findById(Integer id) throws VetNotFoundException;

    /**
     *
     * @return
     */
    List<Vet> findAll();
}
