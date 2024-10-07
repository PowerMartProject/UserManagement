package com.powermart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.powermart.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	
	
}
