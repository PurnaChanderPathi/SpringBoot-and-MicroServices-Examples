package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.EmployeeDetails;


public interface EmployeeRepository extends JpaRepository<EmployeeDetails, Long> {

	  @Query("SELECT e FROM EmployeeDetails e WHERE " +
	            "(:employeeName IS NULL OR e.employeeName = :employeeName) " +
	            "AND (:employeeDept IS NULL OR e.employeeDept = :employeeDept) " +
	            "AND (:employeeStatus IS NULL OR e.employeeStatus = :employeeStatus) " +
	            "AND (:employeeLocation IS NULL OR e.employeeLocation = :employeeLocation) ")
	    List<EmployeeDetails> findEmployeesByCriteria(
	                                           @Param("employeeName") String employeeName,
	                                           @Param("employeeDept") String employeeDept,
	                                           @Param("employeeStatus") String employeeStatus,
	                                           @Param("employeeLocation") String employeeLocation);
	}
