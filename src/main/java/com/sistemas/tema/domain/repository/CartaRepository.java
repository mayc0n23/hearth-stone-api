package com.sistemas.tema.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemas.tema.domain.model.Carta;
import com.sistemas.tema.domain.model.Classe;
import com.sistemas.tema.domain.model.Tipo;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Integer> {
	
	List<Carta> findByNome(String nome);
	
	List<Carta> findByTipo(Tipo tipo);
	
	List<Carta> findByClasse(Classe classe);
	
}