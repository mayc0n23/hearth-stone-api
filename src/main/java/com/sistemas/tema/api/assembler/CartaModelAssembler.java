package com.sistemas.tema.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistemas.tema.api.dto.CartaModel;
import com.sistemas.tema.domain.model.Carta;

@Component
public class CartaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CartaModel toModel(Carta carta) {
		return modelMapper.map(carta, CartaModel.class);
	}
	
	public List<CartaModel> toCollectionModel(List<Carta> cartas) {
		return cartas.stream()
				.map(carta -> toModel(carta))
				.collect(Collectors.toList());
	}
	
}