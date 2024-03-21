package com.example.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DepartmentTable")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long deptId;
	
	private String deptName;
	
	private String deptAdmin;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "DeptCousre",
			joinColumns = @JoinColumn(name="fk_deptId"),
			inverseJoinColumns = @JoinColumn(name="fk_courseId"))
	private List<Course> course;
	

}
