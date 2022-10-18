package com.germano.financemanager.controller;

import java.net.URI;
import java.time.Month;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.germano.financemanager.controller.dto.DespesaDto;
import com.germano.financemanager.controller.form.DespesaForm;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;

@RestController
@RequestMapping("/despesas")
public class DespesasController {
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@GetMapping
	public List<DespesaDto> getDespesas() {
		List<Despesa> despesas = despesaRepository.findAll();
		return DespesaDto.convert(despesas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DespesaDto> getDespesaById(@PathVariable Integer id) {
		Despesa despesa = despesaRepository.findById(id).
				orElseThrow(() -> new EntityNotFoundException());
		
		return ResponseEntity.ok(new DespesaDto(despesa));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<DespesaDto> post(@RequestBody @Valid DespesaForm form, UriComponentsBuilder uriBuilder) {
		Despesa despesa = form.convert();
		
		List<Despesa> despesas = despesaRepository.findByDescricao(despesa.getDescricao());
		
		Month month = despesa.getData().getMonth();
		boolean monthIsPresent = false;
		for (int i = 0; !monthIsPresent && i < despesas.size(); i++) {
			monthIsPresent = (month == despesas.get(i).getData().getMonth()); 
		}
		
		if (!monthIsPresent) {
			despesaRepository.save(despesa); 
			
			URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
			return ResponseEntity.created(uri).body(new DespesaDto(despesa));
		}
		
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<DespesaDto> put(@PathVariable Integer id, @RequestBody @Valid DespesaForm form) {
		Despesa despesa = despesaRepository.findById(id).
				orElseThrow(() -> new EntityNotFoundException());
		
		despesa = form.update(id, despesaRepository);
			
		return ResponseEntity.ok(new DespesaDto(despesa));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		if (!despesaRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
		despesaRepository.deleteById(id);
		
		return ResponseEntity.ok().build(); 
	}
}
