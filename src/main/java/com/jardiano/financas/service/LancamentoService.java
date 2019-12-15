package com.jardiano.financas.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jardiano.financas.model.Lancamento;
import com.jardiano.financas.model.Pessoa;
import com.jardiano.financas.repository.LancamentoRepository;
import com.jardiano.financas.repository.PessoaRepository;
import com.jardiano.financas.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
//		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).orElse(null);

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}
	
}