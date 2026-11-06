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

/**
 *
 * @author egalindo
 *
 */
@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    OwnerRepository ownerRepository;
    OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    /**
     *
     * @param ownerDTO
     * @return
     */
    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {

        Owner newOwner = ownerRepository.save(ownerMapper.mapToEntity(ownerDTO));

        return ownerMapper.mapToDto(newOwner);
    }

    /**
     *
     * @param ownerDTO
     * @return
     */
    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) {

        Owner updateOwner = ownerRepository.save(ownerMapper.mapToEntity(ownerDTO));

        return ownerMapper.mapToDto(updateOwner);
    }

    /**
     *
     * @param id
     * @throws OwnerNotFoundException
     */
    @Override
    public void delete(Integer id) throws OwnerNotFoundException {

        findById(id);

        ownerRepository.deleteById(Long.valueOf(id));
    }

    /**
     *
     * @param id
     * @return
     * @throws OwnerNotFoundException
     */
    @Override
    public OwnerDTO findById(Integer id) throws OwnerNotFoundException {

        Optional<Owner> owner = ownerRepository.findById(Long.valueOf(id));

        if (!owner.isPresent())
            throw new OwnerNotFoundException("Record not found...!");

        return this.ownerMapper.mapToDto(owner.get());
    }

    /**
     *
     * @return
     */
    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
}
