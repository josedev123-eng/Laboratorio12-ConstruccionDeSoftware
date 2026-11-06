package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author karim
 *
 */
@Service
@Slf4j
public class VetServiceImpl implements VetService {

    VetRepository vetRepository;
    VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    /**
     *
     * @param vetDTO
     * @return
     */
    @Override
    public VetDTO create(VetDTO vetDTO) {

        Vet newVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));

        return vetMapper.mapToDto(newVet);
    }

    /**
     *
     * @param vetDTO
     * @return
     */
    @Override
    public VetDTO update(VetDTO vetDTO) {

        Vet updateVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));

        return vetMapper.mapToDto(updateVet);
    }

    /**
     *
     * @param id
     * @throws VetNotFoundException
     */
    @Override
    public void delete(Integer id) throws VetNotFoundException {

        VetDTO vet = findById(id);

        vetRepository.deleteById(vet.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws VetNotFoundException
     */
    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {

        Optional<Vet> vet = vetRepository.findById(id);

        if (!vet.isPresent())
            throw new VetNotFoundException("Record not found...!");

        return this.vetMapper.mapToDto(vet.get());
    }

    /**
     *
     * @return
     */
    @Override
    public List<Vet> findAll() {
        return vetRepository.findAll();
    }
}
