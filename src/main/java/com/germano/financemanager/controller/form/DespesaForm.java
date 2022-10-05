package com.germano.financemanager.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;

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

	public Despesa update(Integer id, DespesaRepository despesaRepository) {
		Despesa despesa = despesaRepository.findById(id).orElseThrow(
				EntityNotFoundException::new);
		
		despesa.setDescricao(this.descricao);
		despesa.setValor(this.valor);
		
		return despesa;
	}
}
