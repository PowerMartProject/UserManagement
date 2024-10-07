package com.powermart.builder;

import org.springframework.stereotype.Component;

import com.powermart.dto.AddressInputDto;
import com.powermart.model.Address;
import com.powermart.model.User;

@Component
public class AddressBuilder {
	
	public Address buildAddressFromAddressInputDto(User user, AddressInputDto addressInputDto) {
		
		Address address = new Address();
		address.setName(addressInputDto.getName());
		address.setCity(addressInputDto.getCity());
		address.setPinCode(addressInputDto.getPinCode());
		address.setStreetName(addressInputDto.getStreetName());
		address.setDistrict(addressInputDto.getDistrict());
		address.setState(addressInputDto.getState());
		address.setCountry(addressInputDto.getCountry());
		address.setMobileNumber(addressInputDto.getMobileNumber());
		address.setAlternateMobileNumber(addressInputDto.getAlternateMobileNumber());
		address.setUser(user);
		return address;
	}
	
	

}
