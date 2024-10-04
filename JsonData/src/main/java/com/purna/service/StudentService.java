package com.purna.service;

import com.purna.entity.Student;
import com.purna.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student){
        Student saveStudent = new Student();
        saveStudent.setStudentId((long)0);
        saveStudent.setStudentName(student.getStudentName());
        saveStudent.setGender(student.getGender());
        saveStudent.setDateOfBirth(student.getDateOfBirth());
        saveStudent.setFatherName(student.getFatherName());
        saveStudent.setMotherName(student.getMotherName());
        return studentRepository.save(saveStudent);
    }
}
