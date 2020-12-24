package com.sistemas.tema.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemas.tema.domain.exception.CartaNaoEncontradaException;
import com.sistemas.tema.domain.exception.ParametrosNaoPassadosException;
import com.sistemas.tema.domain.model.Carta;
import com.sistemas.tema.domain.model.Classe;
import com.sistemas.tema.domain.model.Tipo;
import com.sistemas.tema.domain.repository.CartaRepository;

@Service
public class CartaService {
	
	@Autowired
	private CartaRepository cartaRepository;
	
	@Transactional
	public Carta salvar(Carta carta) {
		return cartaRepository.save(carta);
	}
	
	@Transactional
	public void delete(Carta carta) {
		cartaRepository.delete(carta);
	}
	
	public List<Carta> listarTodos() {
		return cartaRepository.findAll();
	}
	
	public Carta buscar(Integer cartaId) {
		return cartaRepository.findById(cartaId)
				.orElseThrow(() -> new CartaNaoEncontradaException(cartaId));
	}
	
	public List<Carta> buscarPorNome(String nome) {
		return cartaRepository.findByNome(nome);
	}
	
	public List<Carta> buscarPorTipo(String tipoString) {
		try {
			Tipo tipo = Tipo.valueOf(tipoString);
			return cartaRepository.findByTipo(tipo);
		} catch (IllegalArgumentException e) {
			throw new ParametrosNaoPassadosException(String.format("O tipo '%s' não existe", tipoString));
		}
	}
	
	public List<Carta> buscarPorClasse(String classeString) {
		try {
			Classe classe = Classe.valueOf(classeString);
			return cartaRepository.findByClasse(classe);
		} catch (IllegalArgumentException e) {
			throw new ParametrosNaoPassadosException(String.format("A classe '%s' não existe", classeString));
		}
	}
	
}