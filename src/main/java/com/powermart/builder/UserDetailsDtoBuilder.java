package com.powermart.builder;

import org.springframework.stereotype.Component;

import com.powermart.dto.UserDetailsDto;
import com.powermart.model.User;

@Component
public class UserDetailsDtoBuilder {
	
	public UserDetailsDto buildUserDetailsDtoFromUser(User user) {
		
		UserDetailsDto userDetailsDto = new UserDetailsDto();
		
		userDetailsDto.setEmailId(user.getEmailId());
		userDetailsDto.setPassword(user.getPassword());
		userDetailsDto.setFirstName(user.getPersonalDetails().getFirstName());
		userDetailsDto.setLastName(user.getPersonalDetails().getLastName());
		userDetailsDto.setAddressList(user.getAddress());
		userDetailsDto.setAuthorities(user.getAuthorities());
		
		return userDetailsDto;
	}

}
