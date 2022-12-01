package com.germano.financemanager.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.germano.financemanager.controller.dto.ReceitaDto;
import com.germano.financemanager.controller.form.ReceitaForm;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Receita;
import com.germano.financemanager.repository.ReceitaRepository;
import com.germano.financemanager.utils.Utils;

@Service
public class ReceitasService {
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	// TODO: Validate month variable
	public Float findTotalReceitasByMonth(
			Integer year, 
			Integer month,
			ReceitaRepository repository) {
		
		List<Receita> receitas = repository.findByMonth(year, month);
		
		Float totalReceitas = receitas.stream()
									  .map(r -> r.getValor())
								  	  .reduce(0f, Float::sum);
				
		return totalReceitas;
	}
	
	public List<ReceitaDto> findAll(String descricao) {
		List<Receita> receitas;
		if (descricao == null || descricao.isBlank()) {
			receitas = receitaRepository.findAll();
		} else {
			receitas = receitaRepository.findByDescricao(descricao);
		}
		
		return ReceitaDto.convert(receitas);
	}

	public ResponseEntity<ReceitaDto> findById(Integer id) {
		Receita receita = receitaRepository.findById(id).
				orElseThrow(() -> new EntityNotFoundException());
		
		return ResponseEntity.ok(new ReceitaDto(receita));
	}
	
	public ResponseEntity<List<ReceitaDto>> findByMonth(Integer year, 
			Integer month) {
		List<Receita> receitas = receitaRepository.findByMonth(year, month);
		
		return ResponseEntity.ok(ReceitaDto.convert(receitas));
	}
	
	public ResponseEntity<ReceitaDto> save(ReceitaForm form, 
			UriComponentsBuilder uriBuilder) {
		Receita receita = form.convert();
		
		if (!Utils.existsReceitaByDescricaoAndMonth(
				receita, receitaRepository)) {
			receitaRepository.save(receita); 
			
			URI uri = uriBuilder.path("/receitas/{id}").
					buildAndExpand(receita.getId()).toUri();
			return ResponseEntity.created(uri).body(new ReceitaDto(receita));
		}
		
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	public ResponseEntity<ReceitaDto> update(Integer id, ReceitaForm form) {
		Receita receita = form.update(id, receitaRepository);
			
		return ResponseEntity.ok(new ReceitaDto(receita));
	}
	
	public ResponseEntity<?> delete(Integer id) {
		if (!receitaRepository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		
		receitaRepository.deleteById(id);
		
		return ResponseEntity.ok().build();
	}
	
}
