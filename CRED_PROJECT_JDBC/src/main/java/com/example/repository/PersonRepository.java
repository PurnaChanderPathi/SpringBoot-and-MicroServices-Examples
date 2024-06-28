package com.example.repository;

import java.util.List;

import com.example.model.Person;

public interface PersonRepository {
    int save(Person person);
    int update(Person person);
    Person findById(Long id);
    int deleteById(Long id);
    List<Person> findAll();
}
