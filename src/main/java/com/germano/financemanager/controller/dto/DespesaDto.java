package com.germano.financemanager.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.germano.financemanager.model.Despesa;

public class DespesaDto {

	private int id;
	private String descricao;
	private float valor;
	private LocalDate data = LocalDate.now();
	
	public DespesaDto(Despesa despesa) {
		this.id = despesa.getId();
		this.descricao = despesa.getDescricao();
		this.valor = despesa.getValor();
		this.data = despesa.getData();
	}
	
	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public float getValor() {
		return valor;
	}

	public LocalDate getData() {
		return data;
	}
	
	public static List<DespesaDto> convert(List<Despesa> despesas) {
		return despesas.stream().map(DespesaDto::new).collect(Collectors.toList());
	}
}
