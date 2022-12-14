package com.germano.financemanager.service;

import java.net.URI;
import java.time.Month;
import java.time.Year;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import com.germano.financemanager.controller.dto.DespesaDto;
import com.germano.financemanager.controller.form.DespesaForm;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;
import com.germano.financemanager.utils.Utils;

@Service
public class DespesasService {
		
	@Autowired
	private DespesaRepository despesaRepository;

	public List<DespesaDto> findAll(String descricao) {
		List<Despesa> despesas;
		if (descricao == null || descricao.isBlank()) {
			despesas = despesaRepository.findAll();
		} else {
			despesas = despesaRepository.findByDescricao(descricao);
		}
		
		return DespesaDto.convert(despesas);
	}

	public ResponseEntity<DespesaDto> findById(Integer id) {
		Despesa despesa = despesaRepository.findById(id).
				orElseThrow(() -> new EntityNotFoundException("Despesa was not found"));
		
		return ResponseEntity.ok(new DespesaDto(despesa));
	}

	public ResponseEntity<List<DespesaDto>> findByMonth(
			Year year, 
			Month month) {

		List<Despesa> despesas = despesaRepository.
				findByMonth(year.getValue(), month.getValue());
		
		return ResponseEntity.ok(DespesaDto.convert(despesas));
	}
	
	public Float findTotalDespesasByMonth(			
			Year year, 
			Month month,
			DespesaRepository repository) {
		
		List<Despesa> despesas = repository.
				findByMonth(year.getValue(), month.getValue());
		
		Float totalDespesas = despesas.stream()
				.map(r -> r.getValor())
				.reduce(0f, Float::sum);
		
		return totalDespesas;
	}
	
	public ResponseEntity<DespesaDto> save(DespesaForm form, 
			UriComponentsBuilder uriBuilder) {
		Despesa despesa = form.convert();

		if (!Utils.existsDespesaByDescricaoAndMonth(despesa, 
				despesaRepository)) {
			despesaRepository.save(despesa); 
			
			URI uri = uriBuilder.path("/despesas/{id}").
					buildAndExpand(despesa.getId()).toUri();
			return ResponseEntity.created(uri).body(new DespesaDto(despesa));
		}
		
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	public ResponseEntity<DespesaDto> update(Integer id, DespesaForm form) {
		Despesa despesa = form.update(id, despesaRepository);
		
		return ResponseEntity.ok(new DespesaDto(despesa));
	}

	public ResponseEntity<?> delete(Integer id) {
		if (!despesaRepository.existsById(id)) {
			throw new EntityNotFoundException("Despesa not found");
		}
		
		despesaRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
}
