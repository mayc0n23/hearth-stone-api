package com.sistemas.tema.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sistemas.tema.domain.model.Classe;
import com.sistemas.tema.domain.model.Tipo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartaInputModel {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
	
	@Min(0)
	@Max(10)
	private int ataque;
	
	@Min(0)
	@Max(10)
	private int defesa;
	
	@NotNull
	private Tipo tipo;
	
	@NotNull
	private Classe classe;
	
}