package com.germano.financemanager.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.germano.financemanager.model.Receita;

public class ReceitaDto {

	private String descricao;
	private float valor;
	private LocalDate data = LocalDate.now();
	
	public ReceitaDto(Receita receita) {
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
		this.data = receita.getData();
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
	
	public static List<ReceitaDto> convert(List<Receita> receitas) {
		return receitas.stream().map(ReceitaDto::new).collect(Collectors.toList());
	}
}
