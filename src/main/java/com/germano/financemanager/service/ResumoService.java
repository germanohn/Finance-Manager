package com.germano.financemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.germano.financemanager.controller.dto.DespesasByCategoriaAndMonth;
import com.germano.financemanager.controller.dto.ResumoDto;
import com.germano.financemanager.model.Categoria;
import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;
import com.germano.financemanager.repository.ReceitaRepository;

@Service
public class ResumoService {
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	@Autowired
	private DespesasService despesasService;
	
	@Autowired 
	private ReceitasService receitasService;

	public ResponseEntity<ResumoDto> findResumo(Integer year, Integer month) {
		Float totalReceitas = receitasService.findTotalReceitasByMonth(
				year, month, receitaRepository);
		
		Float totalDespesas = despesasService.findTotalDespesasByMonth(
				year, month, despesaRepository);
		
		Float finalBalance = totalReceitas - totalDespesas;
		
		List<DespesasByCategoriaAndMonth> despesas = findDespesas(year, month);
		
		return ResponseEntity.ok(new ResumoDto(totalReceitas, totalDespesas, 
				finalBalance, despesas));
	}

	private List<DespesasByCategoriaAndMonth> findDespesas(Integer year, Integer month) {
		List<DespesasByCategoriaAndMonth> despesas = 
				new ArrayList<DespesasByCategoriaAndMonth>();
		
		for (Categoria categoria : Categoria.values()) {
			List<Despesa> d = despesaRepository.findByCategoriaAndMonth(
							  categoria.toString(), year, month); 
			Float totalDespesas = 0f;
			for (Despesa despesa : d) {
				totalDespesas += despesa.getValor();
			}
			
			despesas.add(new DespesasByCategoriaAndMonth(categoria, 
					totalDespesas)); 
		}
		return despesas;
	}

}
