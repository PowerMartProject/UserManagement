package com.powermart.builder;

import org.springframework.beans.BeanUtils;

import com.powermart.dto.AddressInputDto;
import com.powermart.model.Address;

public class AddressBuilder {
	
	public Address buildAddressFromAddressInputDto(AddressInputDto addressInputDto) {
		
		Address address = new Address();
		BeanUtils.copyProperties(addressInputDto, address);
		return address;
	}
	
	

}
