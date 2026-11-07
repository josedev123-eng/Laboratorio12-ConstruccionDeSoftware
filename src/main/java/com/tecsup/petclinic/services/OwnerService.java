package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

import java.util.List;

/**
 * Servicio de negocio para la entidad Owner.
 *
 * @author jgomezm
 */
public interface OwnerService {

    /**
     * Crea un nuevo owner.
     *
     * @param ownerDTO datos del owner
     * @return owner creado
     */
    OwnerDTO create(OwnerDTO ownerDTO);

    /**
     * Actualiza un owner existente.
     *
     * @param ownerDTO datos del owner
     * @return owner actualizado
     * @throws OwnerNotFoundException si el owner no existe
     */
    OwnerDTO update(OwnerDTO ownerDTO) throws OwnerNotFoundException;

    /**
     * Elimina un owner por su id.
     *
     * @param id identificador del owner
     * @throws OwnerNotFoundException si el owner no existe
     */
    void delete(Long id) throws OwnerNotFoundException;

    /**
     * Busca un owner por su id.
     *
     * @param id identificador del owner
     * @return owner encontrado
     * @throws OwnerNotFoundException si el owner no existe
     */
    OwnerDTO findById(Long id) throws OwnerNotFoundException;

    /**
     * Lista todos los owners.
     *
     * @return lista de owners
     */
    List<OwnerDTO> findAll();

}
