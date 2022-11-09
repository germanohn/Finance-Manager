package com.germano.financemanager.service;

import java.util.List;

import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.model.Receita;
import com.germano.financemanager.repository.DespesaRepository;
import com.germano.financemanager.repository.ReceitaRepository;

public class Service {

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

	public Float findTotalDespesasByMonth(			
			Integer year, 
			Integer month,
			DespesaRepository repository) {
		
		List<Despesa> despesas = repository.findByMonth(year, month);
		
		Float totalDespesas = despesas.stream()
									  .map(r -> r.getValor())
									  .reduce(0f, Float::sum);
				
		return totalDespesas;
	}
}
