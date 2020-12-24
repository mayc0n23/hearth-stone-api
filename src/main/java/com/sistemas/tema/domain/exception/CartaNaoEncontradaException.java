package com.sistemas.tema.domain.exception;

public class CartaNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CartaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CartaNaoEncontradaException(Integer cartaId) {
		this(String.format("Carta de código '%d' não encontrada", cartaId));
	}

}