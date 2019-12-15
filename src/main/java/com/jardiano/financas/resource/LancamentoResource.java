package com.jardiano.financas.resource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.jardiano.financas.exceptionhandler.FinancasExceptionHandler.Erro;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jardiano.financas.event.RecursoCriadoEvent;
import com.jardiano.financas.model.Categoria;
import com.jardiano.financas.model.Lancamento;
import com.jardiano.financas.repository.CategoriaRepositoy;
import com.jardiano.financas.repository.LancamentoRepository;
import com.jardiano.financas.repository.filter.LancamentoFilter;
import com.jardiano.financas.service.LancamentoService;
import com.jardiano.financas.service.exception.PessoaInexistenteOuInativaException;


@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LancamentoService LancamentoService;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = LancamentoService.salvar(lancamento);
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
	
	@ExceptionHandler({ PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
	    this.lancamentoRepository.deleteById(codigo);
	}
	
}