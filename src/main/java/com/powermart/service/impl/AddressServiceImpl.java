package com.powermart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.powermart.builder.AddressBuilder;
import com.powermart.builder.AddressOutputDtoBuilder;
import com.powermart.dao.AddressRepository;
import com.powermart.dao.UserRepository;
import com.powermart.dto.AddressInputDto;
import com.powermart.dto.AddressOutputDto;
import com.powermart.exceptions.BuilderException;
import com.powermart.exceptions.EmailNotFoundException;
import com.powermart.exceptions.NullValueException;
import com.powermart.model.Address;
import com.powermart.model.User;
import com.powermart.service.AddressService;
import com.powermart.service.JwtService;
@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressBuilder addressBuilder;
	
	@Autowired
	AddressOutputDtoBuilder addressOutputDtoBuilder;
	
	@Autowired
	JwtService jwtService;
	
	private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Override
	public ResponseEntity<AddressOutputDto> addAddress(String jwt, AddressInputDto addressDto) {
		if(addressDto==null) {
			logger.error("Received Null values");
			throw new NullValueException("Received null Values");
		}
		Address address = null;
		Optional<User> user = Optional.empty();
		String emailId = extractEmail(jwt);
		try {
			
			user = userRepository.findUserByEmailId(emailId);
		}catch(Exception ex) {
			logger.error("Failed to Get User Details From DB");
			return new ResponseEntity<AddressOutputDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(user.isEmpty()) {
			logger.info("No User Found with email id : "+ emailId);
			throw new EmailNotFoundException("No User Found With id : "+emailId);
		}
		try {
			address = addressBuilder.buildAddressFromAddressInputDto(user.get(),addressDto);
			logger.info("Address Object Built from Address Dto");
		}catch(Exception ex) {
			logger.error("Error While converting dto to entity");
			throw new BuilderException("Error While converting dto to entity");
		}
		Address savedAddress =null;
		try {
			savedAddress = addressRepository.save(address);
			logger.info("Address Saved successfully with Id: "+savedAddress.getAddressId()+" for user : "+savedAddress.getUser().getEmailId());
			List<Address> addressList = user.get().getAddress();
			if(addressList!=null) {
				addressList.add(savedAddress);
			}else {
				addressList=new ArrayList<>();
				addressList.add(savedAddress);
			}
			user.get().setAddress(addressList);
			userRepository.save(user.get());
		}catch(Exception ex) {
			logger.error("Failed to Save in DB");
			return new ResponseEntity<AddressOutputDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			AddressOutputDto addressOutputDto = addressOutputDtoBuilder.addressOutputDtoBuilderFromAddress(savedAddress);
			return new ResponseEntity<AddressOutputDto>(addressOutputDto,HttpStatus.CREATED);
		}catch(Exception ex) {
			logger.error("Error While converting Entity to Dto");
			throw new BuilderException("Error While converting Entity to Dto");
		}
	}
	
	public String extractEmail(String jwt) {
		if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            System.out.println(jwt);
        }
		return jwtService.extractUsername(jwt);
	}

}
