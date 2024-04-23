package com.example.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{
	

}
