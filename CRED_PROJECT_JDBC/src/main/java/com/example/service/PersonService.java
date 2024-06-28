package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Person;
import com.example.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public int save(Person person) {
        return personRepository.save(person);
    }

    public int update(Person person) {
        return personRepository.update(person);
    }

    public Person findById(Long id) {
        return personRepository.findById(id);
    }

    public int deleteById(Long id) {
        return personRepository.deleteById(id);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}

