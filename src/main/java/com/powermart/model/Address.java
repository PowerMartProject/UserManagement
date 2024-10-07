
package com.powermart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "address_id")
	private long addressId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(name="street_name",nullable = false)
	private String StreetName;
	
	@Column(nullable = false)
	private String pinCode;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String district;
	
	@Column(nullable = false)
	private String state;
	
	@Column(nullable = false)
	private String country;
	
	@Column(name ="mobile_number",nullable = false)
	private String mobileNumber;
	
	@Column(name ="alternate_mobile_number")
	private String alternateMobileNumber;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;
	
}
