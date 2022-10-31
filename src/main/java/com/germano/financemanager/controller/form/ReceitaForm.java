package com.germano.financemanager.controller.form;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.dao.DataIntegrityViolationException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Receita;
import com.germano.financemanager.repository.ReceitaRepository;
import com.germano.financemanager.utils.Utils;

public class ReceitaForm {

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
	
	public Receita convert() {
		return new Receita(this.descricao, this.valor, this.data);
	}
	
	/**
	 * Update receita of id {id} with this.convert(). It does not update
	 * the corresponding receita
	 * 
	 * @param id
	 * @param receitaRepository
	 * @return  
	 */
	public Receita update(Integer id, ReceitaRepository repository) {
		Receita receita = repository.
				findById(id).
				orElseThrow(EntityNotFoundException::new);
		
		Receita newReceita = this.convert();
		
		
		Optional<Integer> receitaIdWithSameDescricaoAndMonth = 
				Utils.findReceitaIdByDescricaoAndMonth(
						newReceita, 
						repository);
		
		if (receitaIdWithSameDescricaoAndMonth.isEmpty() || 
				receitaIdWithSameDescricaoAndMonth.get() == id) {
			receita.setDescricao(newReceita.getDescricao());
			receita.setValor(newReceita.getValor());
			receita.setData(newReceita.getData());
		} else {
			throw new DataIntegrityViolationException(
					"there exists another receita with same descricao and month");
		}
		
		return receita;
	}
}
