package com.germano.financemanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.germano.financemanager.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

	public Optional<Despesa> findById(Integer id);

	public List<Despesa> findByDescricao(String descricao);
}
