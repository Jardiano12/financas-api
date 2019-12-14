package com.jardiano.financas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jardiano.financas.model.Pessoa;


public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}