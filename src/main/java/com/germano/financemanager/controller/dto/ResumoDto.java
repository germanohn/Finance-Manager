package com.germano.financemanager.controller.dto;

import java.util.List;

public class ResumoDto {
	Float totalReceitas;
	Float totalDespesas;
	Float finalBalance;
	List<ExpensesByCategoriaAndMonth> expenses;

	public ResumoDto() {
	}

	public ResumoDto(Float totalReceitas, Float totalDespesas, 
			Float finalBalance,
			List<ExpensesByCategoriaAndMonth> expenses) {
		this.totalReceitas = totalReceitas;
		this.totalDespesas = totalDespesas;
		this.finalBalance = finalBalance;
		this.expenses = expenses;
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

	public List<ExpensesByCategoriaAndMonth> getExpenses() {
		return expenses;
	}
}
