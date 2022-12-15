package com.germano.financemanager.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.time.Month;
import java.time.Year;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/create_ledger_test.sql")
public class DespesasControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DespesaRepository repository;
	
	@DisplayName("GET: Response body should be in JSON format")
	@Test
	public void shouldReturnInJSONFormat() throws Exception {
		
		URI uri = new URI("/despesas");
		
		mockMvc.perform(MockMvcRequestBuilders.get(uri))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}
	
	@DisplayName("GET: Array of despesas in the db should have three entries")
	@Test
	public void arrayOfDespesasInTheDbShouldHaveThreeEntries() throws Exception {
		
		URI uri = new URI("/despesas");
		
		mockMvc.perform(MockMvcRequestBuilders.get(uri))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));
		
	}
	
	@DisplayName("GET: Should find despesa of id 1")
	@Test
	public void shouldFindDespesaOfIdOne() throws Exception {
		
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
		
	}
	
	@DisplayName("GET: Should find despesa of id 2")
	@Test
	public void shouldFindDespesaOfIdTwo() throws Exception {
	
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
	
	@DisplayName("GET: Should find despesa of id 3")
	@Test
	public void shouldFindDespesaOfIdThree() throws Exception {
	
		// Assert segunda despesa esta no db de testes
		assertTrue(repository.findById(3).isPresent());
		
		// Buscar segunda despesa e checar status, content, e response body
		mockMvc.perform(MockMvcRequestBuilders.get("/despesas/{id}", 3))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.descricao", is("rapadura")))
		.andExpect(jsonPath("$.valor", is(5.0)))
		.andExpect(jsonPath("$.data", is("2023-09-05")))
		.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));
	
	}
	
	@DisplayName("GET: Invalid id should not be present and return right"
			+ " error message")
	@Test
	public void invalidIdShouldNotBePresentAndReturnRightErrorMessage() 
			throws Exception {
		
		Optional<Despesa> despesa = repository.findById(0);
		
		assertFalse(despesa.isPresent());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/despesas/{id}", 0))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status", is("NOT_FOUND")))
				.andExpect(jsonPath("$.message", is("Despesa was not found")));
		
	}
	
	@DisplayName("GET: Invalid descricao should return empty array")
	@Test
	public void invalidDescricaoShouldReturnEmptyArray() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/despesas?descricao=vidro"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(0)));
		
	}
	
	@DisplayName("GET: Should return despesas with descricao trakinas")
	@Test
	public void shouldReturnDespesasWithDescricaoTrakinas() throws Exception {
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/despesas?descricao=trakinas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)))
				.andExpect(jsonPath("$.[0].descricao", is("trakinas")))
				.andExpect(jsonPath("$.[0].valor", is(3.59)))
				.andExpect(jsonPath("$.[0].data", is("2022-09-05")))
				.andExpect(jsonPath("$.[0].categoria", is("ALIMENTACAO")));	
				
	}
	
	@DisplayName("GET: Should return despesas with descricao rapadura")
	@Test
	public void shouldReturnDespesasWithDescricaoRapadura() throws Exception {
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/despesas?descricao=rapadura"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$.[0].descricao", is("rapadura")))
				.andExpect(jsonPath("$.[0].valor", is(1.0)))
				.andExpect(jsonPath("$.[0].data", is("2022-09-05")))
				.andExpect(jsonPath("$.[0].categoria", is("ALIMENTACAO")))
				.andExpect(jsonPath("$.[1].descricao", is("rapadura")))
				.andExpect(jsonPath("$.[1].valor", is(5.0)))
				.andExpect(jsonPath("$.[1].data", is("2023-09-05")))
				.andExpect(jsonPath("$.[1].categoria", is("ALIMENTACAO")));
				
	}
	
	@DisplayName("GET: Should have despesas of 2022/09")
	@Test
	public void shouldHaveDespesasOfSeptemberOf2022() throws Exception {
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/despesas/{year}/{month}", 
						Year.of(2022), Month.SEPTEMBER))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$.[0].descricao", is("trakinas")))
				.andExpect(jsonPath("$.[0].valor", is(3.59)))
				.andExpect(jsonPath("$.[0].data", is("2022-09-05")))
				.andExpect(jsonPath("$.[0].categoria", is("ALIMENTACAO")))
				.andExpect(jsonPath("$.[1].descricao", is("rapadura")))
				.andExpect(jsonPath("$.[1].valor", is(1.0)))
				.andExpect(jsonPath("$.[1].data", is("2022-09-05")))
				.andExpect(jsonPath("$.[1].categoria", is("ALIMENTACAO")));
		
	}
	
	@DisplayName("GET: Should have despesas of September of 2023")
	@Test
	public void shouldHaveDespesasOfSeptemberOf2023() throws Exception {
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/despesas/{year}/{month}", 
						Year.of(2023), Month.SEPTEMBER))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)))
				.andExpect(jsonPath("$.[0].descricao", is("rapadura")))
				.andExpect(jsonPath("$.[0].valor", is(5.0)))
				.andExpect(jsonPath("$.[0].data", is("2023-09-05")))
				.andExpect(jsonPath("$.[0].categoria", is("ALIMENTACAO")));
				
	}
	
	@DisplayName("GET: Should have no despesas in this month")
	@Test 
	public void shouldHaveNoDespesasInThisMonth() throws Exception {
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/despesas/{year}/{month}", 
						Year.of(2024), Month.SEPTEMBER))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(0)));
		
	}

	@DisplayName("POST: should post despesa with four fields")
	@Test
	public void shouldPostDespesaWithFourFields() throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"cinema\", \n"
				+ " \"valor\" : 30, \n"
				+ " \"data\" : \"2022-12-07\", \n"
				+ " \"categoria\" : \"LAZER\" \n"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post("/despesas")
				.contentType(
						MediaType.APPLICATION_JSON)
				.content(jsonString))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.descricao", is("cinema")))
		.andExpect(jsonPath("$.valor", is(30.0)))
		.andExpect(jsonPath("$.data", is("2022-12-07")))
		.andExpect(jsonPath("$.categoria", is("LAZER")));
		
	}
	
	@DisplayName("POST: should not post despesa with invalid data")
	@Test
	public void shouldNotPostDespesaWithInvalidData() throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"cinema\", \n"
				+ " \"valor\" : 30, \n"
				+ " \"data\" : \"2022-13-07\", \n"
				+ " \"categoria\" : \"LAZER\" \n"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status", is("BAD_REQUEST")));
		
	}
	
	@DisplayName("POST: should not post despesa with invalid categoria")
	@Test
	public void shouldNotPostDespesaWithInvalidCategoria() throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"cinema\", \n"
				+ " \"valor\" : 30, \n"
				+ " \"data\" : \"2022-12-07\", \n"
				+ " \"categoria\" : \"TUBOS\" \n"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status", is("BAD_REQUEST")));
		
	}
	
	
	@DisplayName("POST: Should post despesa with required fields but without "
			+ "optional field")
	@Test
	public void shouldPostDespesaWithRequiredFieldsButWithoutOptionalField() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"cinema\", \n"
				+ " \"valor\" : 30, \n"
				+ " \"data\" : \"2022-12-07\" \n"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("cinema")))
				.andExpect(jsonPath("$.valor", is(30.0)))
				.andExpect(jsonPath("$.data", is("2022-12-07")))
				.andExpect(jsonPath("$.categoria", is("OUTRAS")));
		
	}
	
	@DisplayName("POST: Should post despesa with required fields but without "
			+ "optional field")
	@Test
	public void shouldNotPostDespesaWithoutFieldSomeRequiredField() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"valor\" : 30, \n"
				+ " \"data\" : \"2022-12-07\", \n"
				+ " \"categoria\" : \"LAZER\" \n"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.[0].field", is("descricao")))
				.andExpect(jsonPath("$.[0].message", is("must not be null")));		
		
		jsonString = "{"
				+ " \"descricao\" : \"cinema\", \n"
				+ " \"data\" : \"2022-12-07\", \n"
				+ " \"categoria\" : \"LAZER\" \n"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.[0].field", is("valor")))
				.andExpect(jsonPath("$.[0].message", is("must not be null")));
		
		jsonString = "{"
				+ " \"descricao\" : \"cinema\", \n"
				+ " \"valor\" : 30, \n"
				+ " \"categoria\" : \"LAZER\" \n"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.[0].field", is("data")))
				.andExpect(jsonPath("$.[0].message", is("must not be null")));
		
	}
	
	@DisplayName("POST: Should not post despesa in format not JSON")
	@Test
	public void shouldNotPostDespesaInFormatNotJSON() throws Exception {
		
		String xmlString = "<despesa>"
				+ "<descricao>par</descricao>"
				+ "<valor>110</valor>"
				+ "<data>2022-12-06</data>"
				+ "<categoria>SAUDE</categoria>"
				+ "</despesa>";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_XML)
					.content(xmlString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is4xxClientError());
		
	}
	
	@DisplayName("POST: Should be able to have two despesas with same "
			+ "descricao but distinct month")
	@Test
	public void 
		shouldBeAbleToHaveTwoDespesasWithSameDescricaoButDistinctMonth() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"trakinas\", \n"
				+ " \"valor\" : 4.3, \n"
				+ " \"data\" : \"2021-10-07\", \n"
				+ " \"categoria\" : \"ALIMENTACAO\" "
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("trakinas")))
				.andExpect(jsonPath("$.valor", is(4.3)))
				.andExpect(jsonPath("$.data", is("2021-10-07")))
				.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));
		
	}
	
	
	@DisplayName("POST: Should be able to have two despesas with distinct "
			+ "descricao but same month")
	@Test
	public void 
		shouldBeAbleToHaveTwoDespesasWithDistinctDescricaoButSameMonth() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"chocolate\", \n"
				+ " \"valor\" : 30, \n"
				+ " \"data\" : \"2022-09-07\", \n"
				+ " \"categoria\" : \"ALIMENTACAO\" "
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("chocolate")))
				.andExpect(jsonPath("$.valor", is(30.0)))
				.andExpect(jsonPath("$.data", is("2022-09-07")))
				.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));
		
	}
	
	
	@DisplayName("POST: Should not be able to post despesa with same "
			+ "descricao and month of another despesa in the db")
	@Test
	public void shouldNotBeAbleToPostDespesaWithSameDescricaoAndMonthOfAnotherDespesaInTheDB() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"trakinas\", \n"
				+ " \"valor\" : 5, \n"
				+ " \"data\" : \"2022-09-07\", \n"
				+ " \"categoria\" : \"ALIMENTACAO\" "
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/despesas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is4xxClientError());
				
	}
	
	
	@DisplayName("PUT: Should be able to update with same despesa")
	@Test
	public void ShouldBeAbleToUpdateWithSameDespesa() throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"rapadura\", \n"
				+ " \"valor\" : 1, \n"
				+ " \"data\" : \"2022-09-05\", \n"
				+ " \"categoria\" : \"ALIMENTACAO\" "
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.put("/despesas/{id}", 2)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("rapadura")))
				.andExpect(jsonPath("$.valor", is(1.0)))
				.andExpect(jsonPath("$.data", is("2022-09-05")))
				.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));
		
	}
	
	@DisplayName("PUT: Should be able to update despesa so that there are"
			+ "duplicates in the Db")
	@Test
	public void ShouldNotBeAbleToUpdateDespesaSoThatThereAreDuplicatesInTheDb() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"rapadura\", \n"
				+ " \"valor\" : 1, \n"
				+ " \"data\" : \"2022-09-05\", \n"
				+ " \"categoria\" : \"ALIMENTACAO\" "
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.put("/despesas/{id}", 3)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message", is("there exists another "
						+ "despesa with same descricao and month")))
				.andExpect(jsonPath("$.status", is("CONFLICT")));
		
	}
	
	@DisplayName("PUT: Should update despesa with descricao and month"
			+ "new in the db")
	@Test
	public void ShouldUpdateDespesaWithDescricaoAndMonthNewInTheDb() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"parmegiana\", \n"
				+ " \"valor\" : 110, \n"
				+ " \"data\" : \"2022-11-15\", \n"
				+ " \"categoria\" : \"ALIMENTACAO\" "
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.put("/despesas/{id}", 2)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("parmegiana")))
				.andExpect(jsonPath("$.valor", is(110.0)))
				.andExpect(jsonPath("$.data", is("2022-11-15")))
				.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));
		
	}
	
	@DisplayName("PUT: Should update despesa with same descricao of another"
			+ "but distinct month")
	@Test
	public void ShouldUpdateDespesaWithSameDescricaoOfAnotherButDistinctMonth() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"rapadura\", \n"
				+ " \"valor\" : 110, \n"
				+ " \"data\" : \"2022-11-15\", \n"
				+ " \"categoria\" : \"ALIMENTACAO\" "
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.put("/despesas/{id}", 3)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("rapadura")))
				.andExpect(jsonPath("$.valor", is(110.0)))
				.andExpect(jsonPath("$.data", is("2022-11-15")))
				.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));
		
	}
	
	@DisplayName("PUT: Should update despesa with same month of another but"
			+ "distinct descricao")
	@Test
	public void ShouldUpdateDespesaWithSameMonthOfAnotherButDistinctDescricao() 
			throws Exception {
		
		String jsonString = "{"
				+ " \"descricao\" : \"parmegiana\", \n"
				+ " \"valor\" : 110, \n"
				+ " \"data\" : \"2022-09-15\", \n"
				+ " \"categoria\" : \"ALIMENTACAO\" "
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.put("/despesas/{id}", 3)
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.descricao", is("parmegiana")))
				.andExpect(jsonPath("$.valor", is(110.0)))
				.andExpect(jsonPath("$.data", is("2022-09-15")))
				.andExpect(jsonPath("$.categoria", is("ALIMENTACAO")));
		
	}
}
