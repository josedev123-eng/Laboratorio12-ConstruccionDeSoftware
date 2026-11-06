package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

	SpecialtyMapper INSTANCE = Mappers.getMapper(SpecialtyMapper.class);

	@Mapping(target = "vets", ignore = true)
	Specialty mapToEntity(SpecialtyDTO specialtyDTO);

	SpecialtyDTO mapToDto(Specialty specialty);

	List<SpecialtyDTO> mapToDtoList(List<Specialty> specialtyList);

}
