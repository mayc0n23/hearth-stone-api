package com.sistemas.tema.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos"),
	
	PARAMETRO_INVALIDO("Parametro inválido", "/parametro-invalido"),
	
	RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado");
	
	private String title;

	private String uri;

	ProblemType(String title, String path) {
		this.uri = "https://localhost:8080" + path;
		this.title = title;
	}
	
}