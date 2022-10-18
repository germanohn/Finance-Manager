package com.germano.financemanager.controller.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;

public class DespesaForm {

	@NotNull
	private String descricao;
	@NotNull
	private Float valor;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss[.SSS]")
	private LocalDateTime data = LocalDateTime.now();

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
	
	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}	
	
	public Despesa convert() {
		return new Despesa(this.descricao, this.valor, this.data);
	}
	
	public Despesa update(Integer id, DespesaRepository despesaRepository) {
		Despesa despesa = despesaRepository.findById(id).orElseThrow(
				EntityNotFoundException::new);
		
		despesa.setDescricao(this.descricao);
		despesa.setValor(this.valor);
		
		return despesa;
	}
}
