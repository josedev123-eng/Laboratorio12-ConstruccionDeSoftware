package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper entre la entidad Owner y su DTO.
 *
 * @author jgomezm
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface OwnerMapper {

    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    OwnerDTO toDTO(Owner owner);

    Owner toEntity(OwnerDTO ownerDTO);

    List<OwnerDTO> toDTOList(List<Owner> ownerList);

    List<Owner> toEntityList(List<OwnerDTO> ownerDTOList);

}
