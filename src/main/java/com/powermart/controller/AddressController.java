package com.powermart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.powermart.dto.AddressInputDto;
import com.powermart.dto.AddressOutputDto;
import com.powermart.service.impl.AddressServiceImpl;


@RestController("/user/address")
public class AddressController {
	
	@Autowired
	AddressServiceImpl addressService;
	
	@PostMapping("/addAddress")
	public ResponseEntity<AddressOutputDto> addAddress(@RequestHeader(name = "Authorization") String jwt, AddressInputDto addressInputDto){
		return addressService.addAddress(jwt,addressInputDto);
	}
	
	

}
