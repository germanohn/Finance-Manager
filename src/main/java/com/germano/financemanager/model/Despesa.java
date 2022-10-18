package com.germano.financemanager.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "despesas")
public class Despesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String descricao;
	private float valor;
	private LocalDateTime data = LocalDateTime.now();

	public Despesa() {
	}
	
	public Despesa(String descricao, float valor) {
		this.descricao = descricao;
		this.valor = valor;
	}

	public Despesa(String descricao, Float valor, LocalDateTime data) {
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

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

	public LocalDateTime getData() {
		return data;
	}
}
