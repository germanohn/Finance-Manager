package com.germano.financemanager.controller;

import java.time.Month;
import java.time.Year;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.germano.financemanager.controller.dto.DespesaDto;
import com.germano.financemanager.controller.form.DespesaForm;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.service.DespesasService;

@RestController
@RequestMapping("/despesas")
public class DespesasController {

	@Autowired
	private DespesasService service;

	@GetMapping
	public List<DespesaDto> findAll(
			@RequestParam(required = false) String descricao) {

		return service.findAll(descricao);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DespesaDto> findById(
			@PathVariable Integer id) throws EntityNotFoundException {

		return service.findById(id);
	}

	@GetMapping("/{year}/{month}")
	public ResponseEntity<List<DespesaDto>> findByMonth(
			@PathVariable @Valid Year year, 
			@PathVariable @Valid Month month) {

		return service.findByMonth(year, month);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<DespesaDto> post(
			@RequestBody @Valid DespesaForm form, 
			UriComponentsBuilder uriBuilder) {

		return service.save(form, uriBuilder);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<DespesaDto> put(
			@PathVariable Integer id,
			@RequestBody @Valid DespesaForm form) {

		return service.update(id, form);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Integer id) {

		return service.delete(id);
	}
}
