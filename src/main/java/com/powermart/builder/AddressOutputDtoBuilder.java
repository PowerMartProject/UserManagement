package com.powermart.builder;

import org.springframework.stereotype.Component;

import com.powermart.dto.AddressOutputDto;
import com.powermart.model.Address;

@Component
public class AddressOutputDtoBuilder {
	
	public AddressOutputDto addressOutputDtoBuilderFromAddress(Address address) {
		AddressOutputDto addressOutputDto = new AddressOutputDto();
		
		addressOutputDto.setAddressId(address.getAddressId());
		addressOutputDto.setName(address.getName());
		addressOutputDto.setCity(address.getCity());
		addressOutputDto.setPinCode(address.getPinCode());
		addressOutputDto.setDistrict(address.getDistrict());
		addressOutputDto.setStreetName(address.getStreetName());
		addressOutputDto.setState(address.getState());
		addressOutputDto.setCountry(address.getCountry());
		addressOutputDto.setMobileNumber(address.getMobileNumber());
		addressOutputDto.setAlternateMobileNumber(address.getAlternateMobileNumber());
		
		return addressOutputDto;
	}

}
