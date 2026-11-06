package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.SpecialtyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author jrojas
 *
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class SpecialtyControllerTest {

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFindAllSpecialties() throws Exception {

		final int ID_FIRST_RECORD = 1;

		this.mockMvc.perform(get("/specialties"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFindSpecialtyOK() throws Exception {

		String NAME = "radiology";
		String OFFICE = "Farewell";
		int OPEN_HOUR = 8;
		int CLOSE_HOUR = 18;

		this.mockMvc.perform(get("/specialties/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(NAME)))
				.andExpect(jsonPath("$.office", is(OFFICE)))
				.andExpect(jsonPath("$.openHour", is(OPEN_HOUR)))
				.andExpect(jsonPath("$.closeHour", is(CLOSE_HOUR)));
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFindSpecialtyKO() throws Exception {

		mockMvc.perform(get("/specialties/666"))
				.andExpect(status().isNotFound());
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testCreateSpecialty() throws Exception {

		String NAME = "cardiology";
		String OFFICE = "Lima";
		int OPEN_HOUR = 9;
		int CLOSE_HOUR = 17;

		SpecialtyDTO newSpecialtyTO = SpecialtyDTO.builder()
				.name(NAME)
				.office(OFFICE)
				.openHour(OPEN_HOUR)
				.closeHour(CLOSE_HOUR)
				.build();

		this.mockMvc.perform(post("/specialties")
						.content(om.writeValueAsString(newSpecialtyTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(NAME)))
				.andExpect(jsonPath("$.office", is(OFFICE)))
				.andExpect(jsonPath("$.openHour", is(OPEN_HOUR)))
				.andExpect(jsonPath("$.closeHour", is(CLOSE_HOUR)));
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteSpecialty() throws Exception {

		SpecialtyDTO newSpecialtyTO = SpecialtyDTO.builder()
				.name("neurology")
				.office("Arequipa")
				.openHour(8)
				.closeHour(14)
				.build();

		ResultActions mvcActions = mockMvc.perform(post("/specialties")
						.content(om.writeValueAsString(newSpecialtyTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer id = JsonPath.parse(response).read("$.id");

		mockMvc.perform(delete("/specialties/" + id))
				.andExpect(status().isOk());
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteSpecialtyKO() throws Exception {

		mockMvc.perform(delete("/specialties/" + "1000"))
				.andExpect(status().isNotFound());
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateSpecialty() throws Exception {

		String NAME = "oncology";
		String OFFICE = "Cusco";
		int OPEN_HOUR = 8;
		int CLOSE_HOUR = 16;

		String UP_NAME = "oncology2";
		String UP_OFFICE = "Trujillo";
		int UP_OPEN_HOUR = 10;
		int UP_CLOSE_HOUR = 20;

		SpecialtyDTO newSpecialtyTO = SpecialtyDTO.builder()
				.name(NAME)
				.office(OFFICE)
				.openHour(OPEN_HOUR)
				.closeHour(CLOSE_HOUR)
				.build();

		// CREATE
		ResultActions mvcActions = mockMvc.perform(post("/specialties")
						.content(om.writeValueAsString(newSpecialtyTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Integer id = JsonPath.parse(response).read("$.id");

		// UPDATE
		SpecialtyDTO upSpecialtyTO = SpecialtyDTO.builder()
				.id(id)
				.name(UP_NAME)
				.office(UP_OFFICE)
				.openHour(UP_OPEN_HOUR)
				.closeHour(UP_CLOSE_HOUR)
				.build();

		mockMvc.perform(put("/specialties/" + id)
						.content(om.writeValueAsString(upSpecialtyTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		// FIND
		mockMvc.perform(get("/specialties/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id)))
				.andExpect(jsonPath("$.name", is(UP_NAME)))
				.andExpect(jsonPath("$.office", is(UP_OFFICE)))
				.andExpect(jsonPath("$.openHour", is(UP_OPEN_HOUR)))
				.andExpect(jsonPath("$.closeHour", is(UP_CLOSE_HOUR)));

		// DELETE
		mockMvc.perform(delete("/specialties/" + id))
				.andExpect(status().isOk());
	}

}
