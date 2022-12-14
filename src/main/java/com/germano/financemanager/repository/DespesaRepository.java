package com.germano.financemanager.repository;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.germano.financemanager.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

	public Optional<Despesa> findById(Integer id);

	public List<Despesa> findByDescricao(String descricao);
	
	@Query(
			value = "SELECT * FROM despesas d WHERE " 
					+ "YEAR(d.data) = ?1 AND "
					+ "MONTH(d.data) = ?2", 
			nativeQuery = true)
	public List<Despesa> findByMonth(Integer year, Integer month);
	
	@Query(
			value = "SELECT * FROM despesas d WHERE "
					+ "d.descricao = ?1 AND "
					+ "YEAR(d.data) = ?2 AND "
					+ "MONTH(d.data) = ?3", 
			nativeQuery = true)
	public List<Despesa> findByDescricaoAndMonth(
			String descricao, 
			Integer year,
			Integer month);
	
	@Query(
			value = "SELECT * FROM despesas d WHERE "
					+ "d.categoria = ?1 AND "
					+ "YEAR(d.data) = ?2 AND "
					+ "MONTH(d.data) = ?3", 
			nativeQuery = true)
	public List<Despesa> findByCategoriaAndMonth(
			String categoria, 
			Integer year,
			Integer month);
}
