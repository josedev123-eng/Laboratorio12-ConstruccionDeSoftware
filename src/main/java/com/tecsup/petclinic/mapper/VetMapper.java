package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VetMapper {

	VetMapper INSTANCE = Mappers.getMapper(VetMapper.class);

	@Mapping(target = "specialties", ignore = true)
	Vet mapToEntity(VetDTO vetDTO);

	VetDTO mapToDto(Vet vet);

	List<VetDTO> mapToDtoList(List<Vet> vetList);

}
