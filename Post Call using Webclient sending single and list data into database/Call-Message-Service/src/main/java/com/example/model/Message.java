package com.example.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor 
@Table(name = "MessageTable")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long caseId;
	
	private Long messageId;
	
	private String businessUnit;
	
	private Long systemId;
	
	private String unit;
	
	private LocalDate caseCreatedDate;

}
