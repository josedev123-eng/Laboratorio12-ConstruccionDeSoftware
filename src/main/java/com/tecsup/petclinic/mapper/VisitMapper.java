package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {

	VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

	@Mapping(source = "petId", target = "pet")
	@Mapping(source = "visitDate", target = "visitDate", dateFormat = "yyyy-MM-dd")
	Visit mapToEntity(VisitDTO visitDTO);

	@Mapping(source = "pet.id", target = "petId")
	@Mapping(source = "visitDate", target = "visitDate", dateFormat = "yyyy-MM-dd")
	VisitDTO mapToDto(Visit visit);

	List<VisitDTO> mapToDtoList(List<Visit> visitList);

	// Arma la mascota solo con su id para guardar la visita
	default Pet petIdToPet(Integer petId) {

		if (petId == null) {
			return null;
		}

		Pet pet = new Pet();
		pet.setId(petId);
		return pet;
	}

}
