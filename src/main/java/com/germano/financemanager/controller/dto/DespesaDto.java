package com.germano.financemanager.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.germano.financemanager.model.Despesa;

public class DespesaDto {

	private int id;
	private String descricao;
	private float valor;
	private LocalDateTime date = LocalDateTime.now();
	
	public DespesaDto(Despesa despesa) {
		this.id = despesa.getId();
		this.descricao = despesa.getDescricao();
		this.valor = despesa.getValor();
		this.date = despesa.getDate();
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * @return the valor
	 */
	public float getValor() {
		return valor;
	}
	
	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}
	
	public static List<DespesaDto> convert(List<Despesa> despesas) {
		return despesas.stream().map(DespesaDto::new).collect(Collectors.toList());
	}
	
}
