package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mapper.SpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author jrojas
 *
 */
@Service
@Slf4j
public class SpecialtyServiceImpl implements SpecialtyService {

    SpecialtyRepository specialtyRepository;
    SpecialtyMapper specialtyMapper;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    /**
     *
     * @param specialtyDTO
     * @return
     */
    @Override
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {

        Specialty newSpecialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));

        return specialtyMapper.mapToDto(newSpecialty);
    }

    /**
     *
     * @param specialtyDTO
     * @return
     */
    @Override
    public SpecialtyDTO update(SpecialtyDTO specialtyDTO) {

        Specialty updateSpecialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));

        return specialtyMapper.mapToDto(updateSpecialty);
    }

    /**
     *
     * @param id
     * @throws SpecialtyNotFoundException
     */
    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {

        SpecialtyDTO specialty = findById(id);

        specialtyRepository.deleteById(specialty.getId());
    }

    /**
     *
     * @param id
     * @return
     * @throws SpecialtyNotFoundException
     */
    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {

        Optional<Specialty> specialty = specialtyRepository.findById(id);

        if (!specialty.isPresent())
            throw new SpecialtyNotFoundException("Record not found...!");

        return this.specialtyMapper.mapToDto(specialty.get());
    }

    /**
     *
     * @return
     */
    @Override
    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }
}
