package com.germano.financemanager.controller.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.dao.DataIntegrityViolationException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;
import com.germano.financemanager.utils.Utils;

public class DespesaForm {

	@NotNull
	private String descricao;
	@NotNull
	private Float valor;
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate data;

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
	
	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}	
	
	public Despesa convert() {
		return new Despesa(this.descricao, this.valor, this.data);
	}
	
	/**
	 * Update despesa of id {id} with this.convert(). It does not update
	 * the corresponding despesa
	 * 
	 * @param id
	 * @param despesaRepository
	 * @return  
	 */
	public Despesa update(Integer id, DespesaRepository despesaRepository) {
		Despesa currentDespesaOfId = despesaRepository.findById(id).orElseThrow(
				EntityNotFoundException::new);
		
		Despesa newDespesaOfId = this.convert();
		
		int idDespesaWithSameDescricaoAndMonth = Utils.
				findDespesaByDescricaoAndMonth(newDespesaOfId, despesaRepository);
				
		if (idDespesaWithSameDescricaoAndMonth == -1 || 
				idDespesaWithSameDescricaoAndMonth == id) {
			currentDespesaOfId.setDescricao(newDespesaOfId.getDescricao());
			currentDespesaOfId.setValor(newDespesaOfId.getValor());
			currentDespesaOfId.setData(newDespesaOfId.getData());
		} else {
			throw new DataIntegrityViolationException(
					"there exists another despesa with same descricao and month");
		}
		
		return currentDespesaOfId;
	}
}
