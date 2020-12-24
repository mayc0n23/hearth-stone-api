package com.sistemas.tema.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistemas.tema.api.dto.CartaInputModel;
import com.sistemas.tema.domain.model.Carta;

@Component
public class CartaInputModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Carta toDomainObject(CartaInputModel cartaInput) {
		return modelMapper.map(cartaInput, Carta.class);
	}
	
}