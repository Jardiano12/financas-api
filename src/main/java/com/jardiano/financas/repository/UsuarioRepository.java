package com.jardiano.financas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jardiano.financas.model.Pessoa;
import com.jardiano.financas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Optional<Usuario> findByEmail(String email);
	
	
}
