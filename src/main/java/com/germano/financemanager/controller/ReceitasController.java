package com.germano.financemanager.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.germano.financemanager.controller.dto.ReceitaDto;
import com.germano.financemanager.controller.form.ReceitaForm;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Receita;
import com.germano.financemanager.repository.ReceitaRepository;
import com.germano.financemanager.utils.Utils;

@RestController
@RequestMapping("/receitas")
public class ReceitasController {
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	@GetMapping
	public List<ReceitaDto> findAll(
			@RequestParam(required = false) String descricao) {
		
		List<Receita> receitas;
		if (descricao == null || descricao.isBlank()) {
			receitas = receitaRepository.findAll();
		} else {
			receitas = receitaRepository.findByDescricao(descricao);
		}
		
		return ReceitaDto.convert(receitas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDto> findById(@PathVariable Integer id) {
		Receita receita = receitaRepository.findById(id).
				orElseThrow(() -> new EntityNotFoundException());
		
		return ResponseEntity.ok(new ReceitaDto(receita));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<ReceitaDto> post(
			@RequestBody @Valid ReceitaForm form,
			UriComponentsBuilder uriBuilder) {
		Receita receita = form.convert();
		
		if (!Utils.existsReceitaByDescricaoAndMonth(
				receita, receitaRepository)) {
			receitaRepository.save(receita); 
			
			URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
			return ResponseEntity.created(uri).body(new ReceitaDto(receita));
		}
		
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ReceitaDto> put(
			@PathVariable Integer id, 
			@RequestBody @Valid ReceitaForm form) {
		Receita receita = form.update(id, receitaRepository);
			
		return ResponseEntity.ok(new ReceitaDto(receita));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		if (!receitaRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
		receitaRepository.deleteById(id);
		
		return ResponseEntity.ok().build(); 
	}
}
