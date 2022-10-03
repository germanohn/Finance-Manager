package com.germano.financemanager.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.germano.financemanager.controller.dto.DespesaDto;
import com.germano.financemanager.controller.form.DespesaForm;
import com.germano.financemanager.exceptions.DespesaNotFoundException;
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
	
	@GetMapping("/despesas/{id}")
	public DespesaDto getDespesaById(@PathVariable Integer id) {
		Despesa despesa = despesaRepository.findById(id).
				orElseThrow(DespesaNotFoundException::new);
		
		return new DespesaDto(despesa);
	}
	
	@PostMapping("/despesas")
	public ResponseEntity<DespesaDto> post(@RequestBody DespesaForm form, UriComponentsBuilder uriBuilder) {
		Despesa despesa = form.convert();
		despesaRepository.save(despesa); 
		
		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(new DespesaDto(despesa));
	}
}
