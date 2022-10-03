package com.germano.financemanager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.germano.financemanager.model.Receita;

@RestController
public class ReceitasController {
	
	private List<Receita> receitas = new ArrayList<Receita>();
	
	@GetMapping("/receitas")
	public List<Receita> getReceitas() {
		return this.receitas;
	}
	
	@GetMapping("/receitas/{id}")
	public Receita getReceita(int id) {
		return this.receitas.get(id);
	}
	
	@PostMapping("/receitas")
	public void post(Receita receita) {
		// A API não deve permitir o cadastro de depesas 
		// duplicadas(contendo a mesma descrição, dentro do mesmo mês)
	
		this.receitas.add(receita);
	}
	
	@PutMapping("/receitas/{id}")
	public void update(int id, Receita receita) {
		// A API não deve permitir o cadastro de depesas 
		// duplicadas(contendo a mesma descrição, dentro do mesmo mês)
		
		this.receitas.remove(id);
		this.receitas.add(receita);
	}
	
	@DeleteMapping("/receitas/{id}")
	public void deleteReceita(int id) {
		receitas.remove(id);
	}
}
