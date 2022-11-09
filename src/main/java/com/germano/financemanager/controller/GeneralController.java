package com.germano.financemanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.germano.financemanager.controller.dto.ExpensesByCategoriaAndMonth;
import com.germano.financemanager.controller.dto.ResumoDto;
import com.germano.financemanager.model.Categoria;
import com.germano.financemanager.model.Despesa;
import com.germano.financemanager.repository.DespesaRepository;
import com.germano.financemanager.repository.ReceitaRepository;
import com.germano.financemanager.service.Service;

@RestController
public class GeneralController {
	
	@Autowired
	private DespesaRepository despesaRepository;
	@Autowired
	private ReceitaRepository receitaRepository;
	private Service service = new Service();

	@GetMapping("/resumo/{ano}/{mes}")
	public ResponseEntity<ResumoDto> findResumo(
			@PathVariable(name = "ano") Integer year, 
			@PathVariable(name = "mes") Integer month) {
		
		Float totalReceitas = service.findTotalReceitasByMonth(
				year, month, receitaRepository);
		
		Float totalDespesas = service.findTotalDespesasByMonth(
				year, month, despesaRepository);
		
		Float finalBalance = totalReceitas - totalDespesas;
		
		List<ExpensesByCategoriaAndMonth> expenses = 
				new ArrayList<ExpensesByCategoriaAndMonth>();
		
		for (Categoria categoria : Categoria.values()) {
			List<Despesa> despesas = despesaRepository.
					  findByCategoriaAndMonth(
							  categoria.toString(), year, month); 
			Float totalExpenses = 0f;
			for (Despesa despesa : despesas) {
				totalExpenses += despesa.getValor();
			}
			
			expenses.add(new ExpensesByCategoriaAndMonth(categoria, 
					totalExpenses)); 
		}
		
		return ResponseEntity.ok(new ResumoDto(totalReceitas, totalDespesas, 
				finalBalance, expenses));
	}
}
