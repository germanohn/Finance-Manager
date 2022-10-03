package com.germano.financemanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.germano.financemanager.controller.dto.DespesaDto;
import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;

@RestController
public class DespesasController {
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@GetMapping("/despesas")
	public List<DespesaDto> getDespesas() {
		List<Despesa> despesas = despesaRepository.findAll();
		return DespesaDto.convert(despesas);
	}
	
	/*
	@GetMapping("/despesas/{id}")
	public DespesaDto getDespesaById(@PathVariable Integer id) {
		Optional<Despesa> optinalDespesa = despesaRepository.findById(id);
	}*/
}
