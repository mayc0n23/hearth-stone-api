package com.sistemas.tema.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;

	private String userMessage;
	private OffsetDateTime timestamp;

	private List<Object> objects;

	@Getter
	@Builder
	public static class Object { // Usado para mostrar os campos que estao com erros na validação
		private String name;
		private String userMessage;
	}
	
}