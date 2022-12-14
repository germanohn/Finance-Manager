package com.germano.financemanager.controller;

import java.time.Month;
import java.time.Year;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.germano.financemanager.controller.dto.ResumoDto;
import com.germano.financemanager.service.ResumoService;

@RestController
public class ResumoController {
	
	@Autowired
	private ResumoService service;
	
	@GetMapping("/resumo/{ano}/{mes}")
	public ResponseEntity<ResumoDto> findResumo(
			@PathVariable(name = "ano") @Valid Year year, 
			@PathVariable(name = "mes") @Valid Month month) {
		
		return service.findResumo(year, month);
	}

}
