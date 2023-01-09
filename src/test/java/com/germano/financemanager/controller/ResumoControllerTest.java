package com.germano.financemanager.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Month;
import java.time.Year;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/create_ledger_test.sql")
public class ResumoControllerTest {

//	- [ ]  A API deve possuir um endpoint para detalhar o resumo de determinado **mês**, sendo que ele deve aceitar requisições do tipo **GET** para a URI **/resumo/{ano}/{mes}**.
//  - Checar Validação do ano e do mês
//      - [ ]  ShouldNotQueryInvalidYear
//      - [ ]  ShouldNotQueryInvalidMonth
	@Autowired
	MockMvc mockMvc;
	
	@DisplayName("GET RESUMO: Should not detail resumo of invalid year")
	@Test
	public void ShouldNotDetailResumoOfInvalidYear() throws Exception {
		
		// Rule: The string must represent a valid year. Years outside 
		// the range 0000 to 9999 must be prefixed by the plus or minus symbol.
		
		// year greater than 9999 without plus symbol
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/resumo/{ano}/{mes}", Year.of(10000), Month.JULY))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.error", is("Bad Request")));
		
		// year between 0 and 999 without four digits
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/resumo/{ano}/{mes}", Year.of(1), Month.JULY))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.error", is("Bad Request")));
		
		
		// year between -1 and -999 without four digits
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/resumo/{ano}/{mes}", Year.of(-1), Month.JULY))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.error", is("Bad Request")));
		
	}
	
	@DisplayName("GET RESUMO: Should not detail resumo of invalid month")
	@Test
	public void ShouldNotDetailResumoOfInvalidMonth() throws Exception {
		
		// cannot be the number of the month
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/resumo/{ano}/{mes}", Year.of(2022), Month.of(-1)))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.error", is("Bad Request")));
		
	}

	@DisplayName("GET RESUMO: Should return resumo in JSON format")
	@Test
	public void ShouldReturnResumoInJSONFormat() throws Exception {
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/resumo/{ano}/{mes}", 
							Year.of(2022), 
							Month.SEPTEMBER))
				.andExpect(status().isOk())	
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
	}
	
	@DisplayName("GET RESUMO: Should get correct resumo")
	@Test
	public void ShouldGetCorrectResumo() throws Exception {
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.get("/resumo/{ano}/{mes}", 
							Year.of(2022), 
							Month.SEPTEMBER))
				.andExpect(status().isOk())	
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalReceitas", is(3000.0)))
				.andExpect(jsonPath("$.totalDespesas", is(4.59)))
				.andExpect(jsonPath("$.finalBalance", is(2995.41)))
				.andExpect(jsonPath("$.despesas[0].categoria", 
						is("ALIMENTACAO")))
				.andExpect(jsonPath("$.despesas[0].total", 
						is(4.59)))
				.andExpect(jsonPath("$.despesas[1].categoria", 
						is("SAUDE")))
				.andExpect(jsonPath("$.despesas[1].total", 
						is(1000.0)))
				.andExpect(jsonPath("$.despesas[2].categoria", 
						is("MORADIA")))
				.andExpect(jsonPath("$.despesas[2].total", 
						is(0)))
				.andExpect(jsonPath("$.despesas[3].categoria", 
						is("TRANSPORTE")))
				.andExpect(jsonPath("$.despesas[3].total", 
						is(0)))
				.andExpect(jsonPath("$.despesas[4].categoria", 
						is("EDUCACAO")))
				.andExpect(jsonPath("$.despesas[4].total", 
						is(0)))
				.andExpect(jsonPath("$.despesas[5].categoria", 
						is("LAZER")))
				.andExpect(jsonPath("$.despesas[5].total", 
						is(0)))
				.andExpect(jsonPath("$.despesas[6].categoria", 
						is("IMPREVISTOS")))
				.andExpect(jsonPath("$.despesas[6].total", 
						is(0)))
				.andExpect(jsonPath("$.despesas[7].categoria", 
						is("OUTRAS")))
				.andExpect(jsonPath("$.despesas[7].total", 
						is(0)));
				
	}
	
//	- Valor total das receitas no mês
//	    - Float totalReceitas
//	    - [ ]  ShouldGetWrongTotalReceitas
	
//	- Valor total das despesas no mês
//	    - Float totalDespesas
//	    - [ ]  ShouldGetWrongTotalDespesas
	    
//	- Saldo final no mês
//	    - Float finalBalance = totalReceitas - totalDespesas
//	    - [ ]  ShouldGetWrongFinalBalance
	    
//	- Valor total gasto no **mês** em cada uma das **categorias**, ou seja, discriminar os gastos quanto a categorias
//	    - [ ]  ShouldGetWrongTotalDespesasInAMonthForSomeCategoria
	
}
