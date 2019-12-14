package com.jardiano.financas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jardiano.financas.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}