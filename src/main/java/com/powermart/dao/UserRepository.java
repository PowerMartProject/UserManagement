package com.powermart.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.powermart.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findUserByEmailId(String emailId);
}
