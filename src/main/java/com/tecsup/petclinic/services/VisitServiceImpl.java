package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
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
public class VisitServiceImpl implements VisitService {

    VisitRepository visitRepository;
    VisitMapper visitMapper;

    public VisitServiceImpl(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    /**
     *
     * @param visitDTO
     * @return
     */
    @Override
    public VisitDTO create(VisitDTO visitDTO) {

        Visit newVisit = visitRepository.save(visitMapper.mapToEntity(visitDTO));

        return visitMapper.mapToDto(newVisit);
    }

    /**
     *
     * @param visitDTO
     * @return
     */
    @Override
    public VisitDTO update(VisitDTO visitDTO) {

        Visit updateVisit = visitRepository.save(visitMapper.mapToEntity(visitDTO));

        return visitMapper.mapToDto(updateVisit);
    }

    /**
     *
     * @param id
     * @throws VisitNotFoundException
     */
    @Override
    public void delete(Integer id) throws VisitNotFoundException {

        findById(id);

        visitRepository.deleteById(Long.valueOf(id));
    }

    /**
     *
     * @param id
     * @return
     * @throws VisitNotFoundException
     */
    @Override
    public VisitDTO findById(Integer id) throws VisitNotFoundException {

        Optional<Visit> visit = visitRepository.findById(Long.valueOf(id));

        if (!visit.isPresent())
            throw new VisitNotFoundException("Record not found...!");

        return this.visitMapper.mapToDto(visit.get());
    }

    /**
     *
     * @return
     */
    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }
}
