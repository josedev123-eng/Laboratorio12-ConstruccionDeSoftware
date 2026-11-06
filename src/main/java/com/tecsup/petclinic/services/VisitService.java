package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.util.List;

/**
 *
 * @author jrojas
 *
 */
public interface VisitService {

    /**
     *
     * @param visitDTO
     * @return
     */
    VisitDTO create(VisitDTO visitDTO);

    /**
     *
     * @param visitDTO
     * @return
     */
    VisitDTO update(VisitDTO visitDTO);

    /**
     *
     * @param id
     * @throws VisitNotFoundException
     */
    void delete(Integer id) throws VisitNotFoundException;

    /**
     *
     * @param id
     * @return
     * @throws VisitNotFoundException
     */
    VisitDTO findById(Integer id) throws VisitNotFoundException;

    /**
     *
     * @return
     */
    List<Visit> findAll();
}
