package com.example.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kids {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long KidsId;
	
	private String firstName;
	
	private String lastName;
	
	private LocalDate Dob;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_deptId")
	private Department department;
	
	

}
