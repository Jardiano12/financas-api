package com.jardiano.financas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jardiano.financas.model.Lancamento;
import com.jardiano.financas.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}