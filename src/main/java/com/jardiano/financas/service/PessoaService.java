package com.jardiano.financas.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jardiano.financas.model.Pessoa;
import com.jardiano.financas.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher; 
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscaPessoaPeloCadigo(codigo);
		
		  BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

		  return this.pessoaRepository.save(pessoaSalva);
	}

	public void atualizarPropiedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscaPessoaPeloCadigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva); 
	}
	
	private Pessoa buscaPessoaPeloCadigo(Long codigo) {
		Pessoa pessoaSalva = this.pessoaRepository.findById(codigo)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaSalva;
	}
}
