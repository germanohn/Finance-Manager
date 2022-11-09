package com.germano.financemanager.controller.dto;

import com.germano.financemanager.model.Categoria;

public class ExpensesByCategoriaAndMonth {

	private Categoria categoria;
	private Float total;
	
	public ExpensesByCategoriaAndMonth() {
	}

	public ExpensesByCategoriaAndMonth(Categoria categoria, Float total) {
		this.categoria = categoria;
		this.total = total;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public Float getTotal() {
		return total;
	}
}
