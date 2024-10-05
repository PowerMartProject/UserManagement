package com.powermart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressOutputDto {
	
	private long addressId;
	
	private String StreetName;
	
	private String pinCode;
	
	private String city;
	
	private String district;
	
	private String state;
	
	private String country;

}
