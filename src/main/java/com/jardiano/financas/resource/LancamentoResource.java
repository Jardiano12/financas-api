package com.jardiano.financas.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.jardiano.financas.event.RecursoCriadoEvent;
import com.jardiano.financas.model.Categoria;
import com.jardiano.financas.model.Lancamento;
import com.jardiano.financas.repository.CategoriaRepositoy;
import com.jardiano.financas.repository.LancamentoRepository;


@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@GetMapping
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity buscarPeloCodigo(@PathVariable Long codigo) {
		
		Optional lancamento = this.lancamentoRepository.findById(codigo);
	    return lancamento != null ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	            
	//	Lancamento lancamento = lancamentoRepository.findOne(codigo);
	//	return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
}