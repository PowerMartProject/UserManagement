package com.powermart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.powermart.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	
	public Authority findAuthorityByRole(String role);

}
