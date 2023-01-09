package com.germano.financemanager.controller.dto;

import java.util.List;

public class ResumoDto {
	Float totalReceitas;
	Float totalDespesas;
	Float finalBalance;
	List<DespesasByCategoriaAndMonth> despesas;

	public ResumoDto() {
	}

	public ResumoDto(Float totalReceitas, Float totalDespesas, 
			Float finalBalance,
			List<DespesasByCategoriaAndMonth> despesas) {
		this.totalReceitas = totalReceitas;
		this.totalDespesas = totalDespesas;
		this.finalBalance = finalBalance;
		this.despesas = despesas;
	}

	public Float getTotalReceitas() {
		return totalReceitas;
	}

	public Float getTotalDespesas() {
		return totalDespesas;
	}

	public Float getFinalBalance() {
		return finalBalance;
	}

	public List<DespesasByCategoriaAndMonth> getDespesas() {
		return despesas;
	}
}
