package com.germano.financemanager.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.germano.financemanager.model.Despesa;

public class DespesaForm {

	@NotNull
	private String descricao;
	@NotNull
	private Float valor;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	public Despesa convert() {
		// TODO Auto-generated method stub
		return new Despesa(this.descricao, this.valor);
	}
}
