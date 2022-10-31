package com.germano.financemanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.germano.financemanager.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Integer> {
	
	public Optional<Receita> findById(Integer id);
	
	public List<Receita> findByDescricao(String descricao);

	@Query(
			value = "SELECT * FROM receitas r WHERE " 
					+ "r.descricao = ?1 AND " 
					+ "YEAR(r.data) = ?2 AND "  
					+ "MONTH(r.data) = ?3",
			nativeQuery = true)
	public List<Receita> findByDescricaoAndMonth(
			String descricao, 
			Integer year,
			Integer month);
}
