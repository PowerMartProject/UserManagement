package com.powermart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.powermart.dto.AddressInputDto;
import com.powermart.service.impl.AddressServiceImpl;


@RestController
public class AddressController {
	
	@Autowired
	AddressServiceImpl addressService;
	
	@PostMapping("/addAddress")
	public ResponseEntity<AddressInputDto> addAddress(AddressInputDto addressDto){
		return null;
	}

}
