package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import java.util.List;

/**
 *
 * @author jrojas
 *
 */
public interface SpecialtyService {

    /**
     *
     * @param specialtyDTO
     * @return
     */
    SpecialtyDTO create(SpecialtyDTO specialtyDTO);

    /**
     *
     * @param specialtyDTO
     * @return
     */
    SpecialtyDTO update(SpecialtyDTO specialtyDTO);

    /**
     *
     * @param id
     * @throws SpecialtyNotFoundException
     */
    void delete(Integer id) throws SpecialtyNotFoundException;

    /**
     *
     * @param id
     * @return
     * @throws SpecialtyNotFoundException
     */
    SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException;

    /**
     *
     * @return
     */
    List<Specialty> findAll();
}
