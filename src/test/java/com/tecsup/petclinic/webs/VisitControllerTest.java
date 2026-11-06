package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.VisitDTO;
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
public class VisitControllerTest {

	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFindAllVisits() throws Exception {

		final int ID_FIRST_RECORD = 1;

		this.mockMvc.perform(get("/visits"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFindVisitOK() throws Exception {

		int PET_ID = 7;
		int VET_ID = 2;
		String VISIT_DATE = "2010-03-04";
		String DESCRIPTION = "rabies shot";
		double COST = 45.0;

		this.mockMvc.perform(get("/visits/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.petId", is(PET_ID)))
				.andExpect(jsonPath("$.vetId", is(VET_ID)))
				.andExpect(jsonPath("$.visitDate", is(VISIT_DATE)))
				.andExpect(jsonPath("$.description", is(DESCRIPTION)))
				.andExpect(jsonPath("$.cost", is(COST)));
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFindVisitKO() throws Exception {

		mockMvc.perform(get("/visits/666"))
				.andExpect(status().isNotFound());
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testCreateVisit() throws Exception {

		int PET_ID = 1;
		int VET_ID = 1;
		String VISIT_DATE = "2025-10-20";
		String DESCRIPTION = "vaccination";
		double COST = 50.5;

		VisitDTO newVisitTO = VisitDTO.builder()
				.petId(PET_ID)
				.vetId(VET_ID)
				.visitDate(VISIT_DATE)
				.description(DESCRIPTION)
				.cost(COST)
				.build();

		this.mockMvc.perform(post("/visits")
						.content(om.writeValueAsString(newVisitTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.petId", is(PET_ID)))
				.andExpect(jsonPath("$.vetId", is(VET_ID)))
				.andExpect(jsonPath("$.visitDate", is(VISIT_DATE)))
				.andExpect(jsonPath("$.description", is(DESCRIPTION)))
				.andExpect(jsonPath("$.cost", is(COST)));
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteVisit() throws Exception {

		VisitDTO newVisitTO = VisitDTO.builder()
				.petId(2)
				.vetId(3)
				.visitDate("2025-11-01")
				.description("deworming")
				.cost(30.0)
				.build();

		ResultActions mvcActions = mockMvc.perform(post("/visits")
						.content(om.writeValueAsString(newVisitTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer id = JsonPath.parse(response).read("$.id");

		mockMvc.perform(delete("/visits/" + id))
				.andExpect(status().isOk());
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteVisitKO() throws Exception {

		mockMvc.perform(delete("/visits/" + "1000"))
				.andExpect(status().isNotFound());
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateVisit() throws Exception {

		int PET_ID = 3;
		int VET_ID = 4;
		String VISIT_DATE = "2025-09-10";
		String DESCRIPTION = "check up";
		double COST = 60.0;

		int UP_PET_ID = 4;
		int UP_VET_ID = 5;
		String UP_VISIT_DATE = "2025-09-15";
		String UP_DESCRIPTION = "check up 2";
		double UP_COST = 80.0;

		VisitDTO newVisitTO = VisitDTO.builder()
				.petId(PET_ID)
				.vetId(VET_ID)
				.visitDate(VISIT_DATE)
				.description(DESCRIPTION)
				.cost(COST)
				.build();

		// CREATE
		ResultActions mvcActions = mockMvc.perform(post("/visits")
						.content(om.writeValueAsString(newVisitTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Integer id = JsonPath.parse(response).read("$.id");

		// UPDATE
		VisitDTO upVisitTO = VisitDTO.builder()
				.id(id)
				.petId(UP_PET_ID)
				.vetId(UP_VET_ID)
				.visitDate(UP_VISIT_DATE)
				.description(UP_DESCRIPTION)
				.cost(UP_COST)
				.build();

		mockMvc.perform(put("/visits/" + id)
						.content(om.writeValueAsString(upVisitTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		// FIND
		mockMvc.perform(get("/visits/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id)))
				.andExpect(jsonPath("$.petId", is(UP_PET_ID)))
				.andExpect(jsonPath("$.vetId", is(UP_VET_ID)))
				.andExpect(jsonPath("$.visitDate", is(UP_VISIT_DATE)))
				.andExpect(jsonPath("$.description", is(UP_DESCRIPTION)))
				.andExpect(jsonPath("$.cost", is(UP_COST)));

		// DELETE
		mockMvc.perform(delete("/visits/" + id))
				.andExpect(status().isOk());
	}

}
