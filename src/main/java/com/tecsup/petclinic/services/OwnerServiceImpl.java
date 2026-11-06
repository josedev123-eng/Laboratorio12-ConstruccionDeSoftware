package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Owner.
 *
 * @author jgomezm
 */
@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {

        Owner newOwner = ownerRepository.save(ownerMapper.toEntity(ownerDTO));

        return ownerMapper.toDTO(newOwner);
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) throws OwnerNotFoundException {

        // Valida que el owner exista antes de actualizar
        findById(ownerDTO.getId());

        Owner updatedOwner = ownerRepository.save(ownerMapper.toEntity(ownerDTO));

        return ownerMapper.toDTO(updatedOwner);
    }

    @Override
    public void delete(Long id) throws OwnerNotFoundException {

        OwnerDTO owner = findById(id);

        ownerRepository.delete(ownerMapper.toEntity(owner));
    }

    @Override
    public OwnerDTO findById(Long id) throws OwnerNotFoundException {

        Optional<Owner> owner = ownerRepository.findById(id);

        if (owner.isEmpty())
            throw new OwnerNotFoundException("Record not found...!");

        return ownerMapper.toDTO(owner.get());
    }

    @Override
    public List<OwnerDTO> findAll() {

        List<Owner> owners = ownerRepository.findAll();

        owners.forEach(owner -> log.info("Owner >> {}", owner));

        return owners
                .stream()
                .map(ownerMapper::toDTO)
                .collect(Collectors.toList());
    }

}
