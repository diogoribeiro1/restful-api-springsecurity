package com.apirestful.eventos.controller;

import com.apirestful.eventos.models.Event;
import com.apirestful.eventos.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventRepository eventRepository;


    @BeforeEach
    public void setup()
    {
        Event eventModel = new Event(1, "festa", "amh", "em casa");

        Mockito.when(eventRepository.findAll())
                .thenReturn(List.of(eventModel));

        Mockito.when(eventRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(eventModel));

        Mockito.when(eventRepository.save(Mockito.any()))
                .thenReturn(eventModel);

    }

    @Test
    void testGetAllEvents () throws Exception
    {
        this.mockMvc.perform(get("/event")
                        .with(user("diogo").password("123").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("festa"))
                .andExpect(jsonPath("$[0].date").value("amh"))
                .andExpect(jsonPath("$[0].locale").value("em casa"));
    }

    @Test
    void testGetEventById () throws Exception
    {
        this.mockMvc.perform(get("/event/1")
                        .with(user("diogo").password("123").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("festa"))
                .andExpect(jsonPath("$.date").value("amh"))
                .andExpect(jsonPath("$.locale").value("em casa"));
    }

    @Test
    void testSaveEvent () throws Exception
    {
        this.mockMvc.perform(post("/event")
                        .with(user("diogo").password("123").roles("ADMIN"))
                        .content("{\"id\":\"2\",\"name\": \"festa\",\"date\": \"amh\",\"locale\": \"em casa\"}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("festa"))
                .andExpect(jsonPath("$.date").value("amh"))
                .andExpect(jsonPath("$.locale").value("em casa"));
    }

    @Test
    void testCanDeleteAnEvent () throws Exception
    {
        this.mockMvc.perform(delete("/event")
                        .with(user("diogo").password("123").roles("ADMIN"))
                        .content("{\"id\":\"2\",\"name\": \"festa\",\"date\": \"amh\",\"locale\": \"em casa\"}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testCanDeleteAnEventById () throws Exception
    {
        this.mockMvc.perform(delete("/event/1")
                        .with(user("diogo").password("123").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void testCanUpdateAnEventById () throws Exception
    {
        this.mockMvc.perform(put("/event/1")
                        .with(user("diogo").password("123").roles("ADMIN"))
                        .content("{\"id\":\"2\",\"name\": \"festa\",\"date\": \"amh\",\"locale\": \"em casa\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("festa"))
                .andExpect(jsonPath("$.date").value("amh"))
                .andExpect(jsonPath("$.locale").value("em casa"));
    }





}
