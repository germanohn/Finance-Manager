package com.germano.financemanager.utils;

import java.time.Month;
import java.time.Year;
import java.util.List;

import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;

public class Utils {
	
	/*
	 * Rule: a despesa is present if there is already an entry in the 
	 * db with same descricao and month.
	 */
	public static int findDespesaByDescricaoAndMonth(Despesa despesa, DespesaRepository despesaRepository) {
		List<Despesa> despesas = despesaRepository.findByDescricao(despesa.getDescricao());
		
		int despesaId = -1;
		int year = despesa.getData().getYear();
		Month month = despesa.getData().getMonth();
		for (int i = 0; despesaId == -1 && i < despesas.size(); i++) {
			Despesa despesaI = despesas.get(i);
			if (year == despesaI.getData().getYear() && 
					month == despesaI.getData().getMonth()) {
				despesaId = despesaI.getId();
			}
		}
		
		return despesaId;
	}
	
	public static void printDespesa(Despesa despesa) { 
		System.out.println("id " + despesa.getId() + 
				", descricao: " + despesa.getDescricao() + 
				", valor: " + despesa.getValor() + 
				", data: " + despesa.getData());
	}
}
