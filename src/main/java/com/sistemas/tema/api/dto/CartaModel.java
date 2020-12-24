package com.sistemas.tema.api.dto;

import com.sistemas.tema.domain.model.Classe;
import com.sistemas.tema.domain.model.Tipo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartaModel {
	
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private int ataque;
	
	private int defesa;
	
	private Tipo tipo;
	
	private Classe classe;
	
}