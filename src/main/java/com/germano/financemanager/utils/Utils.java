package com.germano.financemanager.utils;

import java.util.List;
import java.util.Optional;

import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.model.Receita;
import com.germano.financemanager.repository.DespesaRepository;
import com.germano.financemanager.repository.ReceitaRepository;

public class Utils {
	
	public static Optional<Integer> findDespesaIdByDescricaoAndMonth(
			Despesa despesa, 
			DespesaRepository repository) {
		List<Despesa> despesas = repository.findByDescricaoAndMonth(
				despesa.getDescricao(), 
				despesa.getData().getYear(),
				despesa.getData().getMonthValue()); 
		
		Optional<Integer> despesaId;
		
		if (!despesas.isEmpty()) {
			despesaId = Optional.of(despesas.get(0).getId());
		} else {
			despesaId = Optional.empty();
		}
			
		return despesaId;
	}

	public static boolean existsDespesaByDescricaoAndMonth(
			Despesa despesa, 
			DespesaRepository repository) {
		List<Despesa> despesas = repository.findByDescricaoAndMonth(
				despesa.getDescricao(), 
				despesa.getData().getYear(),
				despesa.getData().getMonthValue()); 

		return !despesas.isEmpty();
	}
	
	public static void printDespesa(Despesa despesa) { 
		System.out.println("id " + despesa.getId() + 
				", descricao: " + despesa.getDescricao() + 
				", valor: " + despesa.getValor() + 
				", data: " + despesa.getData());
	}

	public static Optional<Integer> findReceitaIdByDescricaoAndMonth(
			Receita receita, 
			ReceitaRepository repository) {
		List<Receita> receitas = repository.findByDescricaoAndMonth(
				receita.getDescricao(), 
				receita.getData().getYear(),
				receita.getData().getMonthValue()); 
		
		Optional<Integer> receitaId;
		
		if (!receitas.isEmpty()) {
			receitaId = Optional.of(receitas.get(0).getId());
		} else {
			receitaId = Optional.empty();
		}
			
		return receitaId;
	}

	public static boolean existsReceitaByDescricaoAndMonth(
			Receita receita,
			ReceitaRepository repository) {
		List<Receita> receitas = repository.findByDescricaoAndMonth(
				receita.getDescricao(), 
				receita.getData().getYear(),
				receita.getData().getMonthValue()); 

		return !receitas.isEmpty();
	}
}
