package com.germano.financemanager.controller;

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
			@PathVariable(name = "ano") Integer year, 
			@PathVariable(name = "mes") Integer month) {
		
		return service.findResumo(year, month);
	}

}
