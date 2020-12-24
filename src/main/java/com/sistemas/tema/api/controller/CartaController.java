package com.sistemas.tema.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistemas.tema.api.assembler.CartaInputModelDisassembler;
import com.sistemas.tema.api.assembler.CartaModelAssembler;
import com.sistemas.tema.api.dto.CartaInputModel;
import com.sistemas.tema.api.dto.CartaModel;
import com.sistemas.tema.domain.exception.ParametrosNaoPassadosException;
import com.sistemas.tema.domain.model.Carta;
import com.sistemas.tema.domain.service.CartaService;

@RestController
@RequestMapping("/carta")
public class CartaController {
	
	@Autowired
	private CartaService cartaService;
	
	@Autowired
	private CartaModelAssembler cartaModelAssembler;
	
	@Autowired
	private CartaInputModelDisassembler cartaInputModelDisassembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CartaModel adicionar(@RequestBody @Valid CartaInputModel cartaInput) {
		return cartaModelAssembler.toModel(cartaService.salvar(cartaInputModelDisassembler.toDomainObject(cartaInput)));
	}
	
	@DeleteMapping("/{cartaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer cartaId) {
		Carta carta = cartaService.buscar(cartaId);
		cartaService.delete(carta);
	}
	
	@GetMapping
	public List<CartaModel> listar() {
		return cartaModelAssembler.toCollectionModel(cartaService.listarTodos());
	}
	
	@GetMapping("/buscar-por-nome-ou-id")
	public ResponseEntity<?> buscarPorNomeOuId(@RequestParam(value = "nome", required = false) String nome, 
			@RequestParam(value = "id", required = false) Integer id) {
		
		if (nome != null) {
			List<CartaModel> cartas = cartaModelAssembler.toCollectionModel(cartaService.buscarPorNome(nome));
			return ResponseEntity.ok(cartas);
		} else if (id != null) {
			CartaModel carta = cartaModelAssembler.toModel(cartaService.buscar(id));
			return ResponseEntity.ok(carta);
		} else {
			throw new ParametrosNaoPassadosException("Você deve informar pelo menos um parametro");
		}
		
	}
	
	@GetMapping("/buscar-por-tipo-ou-classe")
	public List<CartaModel> buscarPorTipoOuClasse(@RequestParam(value = "tipo", required = false) String tipo, 
			@RequestParam(value = "classe", required = false) String classe) {
		
		if (tipo != null) {
			return cartaModelAssembler.toCollectionModel(cartaService.buscarPorTipo(tipo));
		} else if (classe != null) {
			return cartaModelAssembler.toCollectionModel(cartaService.buscarPorClasse(classe));
		} else {
			throw new ParametrosNaoPassadosException("Você deve informar pelo menos um parametro");
		}
		
	}
	
}