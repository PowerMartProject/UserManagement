package com.powermart.service;

import org.springframework.http.ResponseEntity;

import com.powermart.dto.AddressInputDto;
import com.powermart.dto.AddressOutputDto;

public interface AddressService {
	
	public ResponseEntity<AddressOutputDto> addAddress(String jwt, AddressInputDto addressDto);

}
