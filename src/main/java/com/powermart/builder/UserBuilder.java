package com.powermart.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.powermart.dao.AuthorityRepository;
import com.powermart.dto.UserSignUpDto;
import com.powermart.model.Authority;
import com.powermart.model.PersonalDetails;
import com.powermart.model.User;

@Component
public class UserBuilder {
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	 public User buildUserFromSignUpDto(UserSignUpDto userSignUpDto) {
		 
	        User user = new User();
	        user.setEmailId(userSignUpDto.getEmailId());
	        user.setPassword(userSignUpDto.getPassword());
	        user.setPersonalDetails(new PersonalDetails());
	        user.getPersonalDetails().setFirstName(userSignUpDto.getFirstName());
	        user.getPersonalDetails().setLastName(userSignUpDto.getLastName());
	        user.setSignedUpDate(LocalDate.now()); 
	        
	        List<Authority> authorities = new ArrayList<>();
	        for(String role: userSignUpDto.getRoles()) {
	        	
	        	Authority authority = authorityRepository.findAuthorityByRole(role);
	        	authority.getUsers().add(user);
	        	authorities.add(authority);
	        }
	        
	        user.setAuthorities(authorities);
	        
//	        Address address = new Address();
//	        address.setStreetName(userSignUpDto.getStreetName());
//	        address.setPinCode(userSignUpDto.getPinCode());
//	        address.setCity(userSignUpDto.getCity());
//	        address.setDistrict(userSignUpDto.getDistrict());
//	        address.setState(userSignUpDto.getState());
//	        address.setCountry(userSignUpDto.getCountry());
//	        address.setUser(user);
//	        
//	        List<Address> addresses = new ArrayList<>();
//	        addresses.add(address);
//
//	        user.setAddress(addresses);

	        return user;
	 }
}
