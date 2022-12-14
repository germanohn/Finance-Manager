package com.germano.financemanager.controller.form;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.dao.DataIntegrityViolationException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.germano.financemanager.exceptions.EntityNotFoundException;
import com.germano.financemanager.model.Categoria;
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
	private Categoria categoria = Categoria.OUTRAS;
	
	public DespesaForm(
			@NotNull String descricao, 
			@NotNull Float valor, 
			@NotNull LocalDate data, 
			Categoria categoria) {
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
		if (categoria == null)
			this.categoria = Categoria.OUTRAS;
		else 
			this.categoria = categoria;
	}

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
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		if (categoria == null)
			this.categoria = Categoria.OUTRAS;
		else 
			this.categoria = categoria;
	}	
	
	public Despesa convert() {
		return new Despesa(this.descricao, this.valor, this.data, 
				this.categoria);
	}
	
	/**
	 * Update despesa of id {id} with this.convert(). It does not update
	 * the corresponding despesa
	 * 
	 * @param id
	 * @param repository
	 * @return  
	 */
	public Despesa update(Integer id, DespesaRepository repository) {
		Despesa despesa = repository.
				findById(id).
				orElseThrow(EntityNotFoundException::new);
		
		Despesa newDespesa = this.convert();
		
		Optional<Integer> despesaIdWithSameDescricaoAndMonth = 
				Utils.findDespesaIdByDescricaoAndMonth(
						newDespesa,
						repository); 
		
		if (despesaIdWithSameDescricaoAndMonth.isEmpty() || 
				despesaIdWithSameDescricaoAndMonth.get() == id) {
			despesa.setDescricao(newDespesa.getDescricao());
			despesa.setValor(newDespesa.getValor());
			despesa.setData(newDespesa.getData());
			despesa.setCategoria(newDespesa.getCategoria());
		} else {
			throw new DataIntegrityViolationException(
					"there exists another despesa with same descricao and month");
		}
		
		return despesa;
	}
}
