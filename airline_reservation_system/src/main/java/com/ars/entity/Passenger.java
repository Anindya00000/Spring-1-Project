package com.ars.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint
		(columnNames = {		
          "email"})})
public class Passenger extends User{
	
	@Builder
	public Passenger(int id, String userName, String password, String role, String name, String phno, String email) {
		super(id, userName, password, role);
		this.name = name;
		this.phno = phno;
		this.email = email;
	}
	@Column(length = 50)
	private String name;
	@Column(length = 10)
	private String phno;
	//@Column(length = 50,unique = true,nullable = false)
	private String email;

}
