package com.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.model.Person;

@Repository
public class JdbcPersonRepository implements PersonRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    private static class PersonRowMapper implements RowMapper<Person> {
//        @Override
//        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Person person = new Person();
//            person.setId(rs.getLong("id"));
//            person.setName(rs.getString("name"));
//            person.setMobileNumber(rs.getString("mobile_number"));
//            person.setAddress(rs.getString("address"));
//            person.setCity(rs.getString("city"));
//            person.setCountry(rs.getString("country"));
//            return person;
//        }
//    }
    
    @Override
    public int save(Person person) {
//        return jdbcTemplate.update(
//            "INSERT INTO persons (name, mobile_number, address, city, country) VALUES (?, ?, ?, ?, ?)",
//            person.getName(), person.getMobileNumber(), person.getAddress(), person.getCity(), person.getCountry());
    	String query = "INSERT INTO persons (name, mobile_number, address, city, country) VALUES (?, ?, ?, ?, ?)";
    	Object [] args = {person.getName(), person.getMobileNumber(), person.getAddress(), person.getCity(), person.getCountry()};
    	return jdbcTemplate.update(query, args);
    }

    @Override
    public int update(Person person) {
//        return jdbcTemplate.update(
//            "UPDATE persons SET name = ?, mobile_number = ?, address = ?, city = ?, country = ? WHERE id = ?",
//            person.getName(), person.getMobileNumber(), person.getAddress(), person.getCity(), person.getCountry(), person.getId());
    	String query = "UPDATE persons SET name = ?, mobile_number = ?, address = ?, city = ?, country = ? WHERE id = ?";
    	Object [] args = {person.getName(), person.getMobileNumber(), person.getAddress(), person.getCity(), person.getCountry(), person.getId()};
    	return jdbcTemplate.update(query, args);
    }

    @Override
    public Person findById(Long id) {
//    	
//        return jdbcTemplate.queryForObject(
//                "SELECT * FROM persons WHERE id = ?",
//                new PersonRowMapper(), id);
    	
    	String query = "SELECT * FROM persons WHERE id = ?";
        Object [] args = {id};
        return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Person.class),args);
    }

    @Override
    public int deleteById(Long id) {
    	
    	//return jdbcTemplate.update("DELETE FROM persons WHERE id = ?", id);
    	
    	String query = "DELETE FROM persons WHERE id = ?";
    	Object [] args = {id};
        return jdbcTemplate.update(query, args);
    }

    @Override
    public List<Person> findAll() {
    	
    	// return jdbcTemplate.query("SELECT * FROM persons", new PersonRowMapper());
    	
    	String query = "SELECT * FROM persons";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Person.class));
    }
}
