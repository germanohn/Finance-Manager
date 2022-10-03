package com.germano.financemanager.controller.form;

import com.germano.financemanager.model.Despesa;

public class DespesaForm {

	private String descricao;
	private float valor;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public Despesa convert() {
		// TODO Auto-generated method stub
		return new Despesa(this.descricao, this.valor);
	}
}
