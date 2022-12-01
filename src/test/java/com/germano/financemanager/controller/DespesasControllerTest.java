package com.germano.financemanager.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.germano.financemanager.repository.DespesaRepository;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class DespesasControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DespesaRepository repository;
	
	@DisplayName("Test number of despesas in the db")
	@Test
	public void findAll() throws Exception {
		
		URI uri = new URI("/despesas");
		
		mockMvc.perform(MockMvcRequestBuilders.get(uri))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)));

	}
	
	@DisplayName("Test the content of each despesa in the db")
	@Test
	public void findById() throws Exception {
		
		// Assert primeira despesa esta no db de testes
		assertTrue(repository.findById(1).isPresent());
		
		// Buscar primeira e segunda despesa e checar status, content, e response body
		mockMvc.perform(MockMvcRequestBuilders.get("/despesas/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("trakinas")))
				.andExpect(jsonPath("$.valor", is(3.59)))
				.andExpect(jsonPath("$.data", is("2022-09-05")))
				.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));		
		
		// Assert segunda despesa esta no db de testes
		assertTrue(repository.findById(2).isPresent());
		
		// Buscar segunda despesa e checar status, content, e response body
		mockMvc.perform(MockMvcRequestBuilders.get("/despesas/{id}", 2))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("rapadura")))
				.andExpect(jsonPath("$.valor", is(1.0)))
				.andExpect(jsonPath("$.data", is("2022-09-05")))
				.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));
	}
}
