package com.jardiano.financas.repository.lancamento;

import java.util.List;

import com.jardiano.financas.model.Lancamento;
import com.jardiano.financas.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}