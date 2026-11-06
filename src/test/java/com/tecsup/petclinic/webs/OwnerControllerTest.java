package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Pruebas de integración para OwnerController con base de datos H2.
 *
 * @author jgomezm
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("h2")
@Transactional
@Slf4j
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAll() throws Exception {

        final int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testFindOK() throws Exception {

        this.mockMvc.perform(get("/owners/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("George")))
                .andExpect(jsonPath("$.lastName", is("Franklin")));
    }

    @Test
    public void testFindKO() throws Exception {

        mockMvc.perform(get("/owners/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {

        String FIRST_NAME = "Eduardo";
        String LAST_NAME = "Rodriguez";
        String ADDRESS = "Av. Arequipa 123";
        String CITY = "Lima";
        String TELEPHONE = "987654321";

        OwnerDTO newOwnerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .build();

        this.mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.address", is(ADDRESS)))
                .andExpect(jsonPath("$.city", is(CITY)))
                .andExpect(jsonPath("$.telephone", is(TELEPHONE)));
    }

    @Test
    public void testUpdate() throws Exception {

        String FIRST_NAME = "Eduardo";
        String LAST_NAME = "Rodriguez";
        String ADDRESS = "Av. Arequipa 123";
        String CITY = "Lima";
        String TELEPHONE = "987654321";

        String UP_FIRST_NAME = "Eduardo";
        String UP_LAST_NAME = "Paredes";
        String UP_ADDRESS = "Av. Javier Prado 456";
        String UP_CITY = "Surco";
        String UP_TELEPHONE = "999888777";

        OwnerDTO newOwnerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .build();

        // CREATE
        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.id", Long.class);

        // UPDATE
        OwnerDTO upOwnerDTO = OwnerDTO.builder()
                .id(id)
                .firstName(UP_FIRST_NAME)
                .lastName(UP_LAST_NAME)
                .address(UP_ADDRESS)
                .city(UP_CITY)
                .telephone(UP_TELEPHONE)
                .build();

        mockMvc.perform(put("/owners/" + id)
                        .content(om.writeValueAsString(upOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // FIND
        mockMvc.perform(get("/owners/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.firstName", is(UP_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(UP_LAST_NAME)))
                .andExpect(jsonPath("$.address", is(UP_ADDRESS)))
                .andExpect(jsonPath("$.city", is(UP_CITY)))
                .andExpect(jsonPath("$.telephone", is(UP_TELEPHONE)));
    }

    @Test
    public void testDelete() throws Exception {

        String FIRST_NAME = "Eduardo";
        String LAST_NAME = "Rodriguez";
        String ADDRESS = "Av. Arequipa 123";
        String CITY = "Lima";
        String TELEPHONE = "987654321";

        OwnerDTO newOwnerDTO = OwnerDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .address(ADDRESS)
                .city(CITY)
                .telephone(TELEPHONE)
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Long id = JsonPath.parse(response).read("$.id", Long.class);

        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteKO() throws Exception {

        mockMvc.perform(delete("/owners/1000"))
                .andExpect(status().isNotFound());
    }

}
