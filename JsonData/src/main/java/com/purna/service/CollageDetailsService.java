package com.purna.service;

import com.purna.dto.StudentDepartmentDto;
import com.purna.entity.CollageDetails;
import com.purna.entity.Department;
import com.purna.entity.Student;
import com.purna.repository.CollageDetailsRepo;
import com.purna.repository.DepartmentRepository;
import com.purna.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollageDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CollageDetailsService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CollageDetailsRepo collageDetailsRepo;

    public CollageDetails saveCollageDetails(CollageDetails collageDetails){
        CollageDetails saveCollageDetails = new CollageDetails();
        saveCollageDetails.setId((long)0);
        saveCollageDetails.setStudentName(collageDetails.getStudentName());
        saveCollageDetails.setGender(collageDetails.getGender());
        saveCollageDetails.setDateOfBirth(collageDetails.getDateOfBirth());
        saveCollageDetails.setFatherName(collageDetails.getFatherName());
        saveCollageDetails.setMotherName(collageDetails.getMotherName());
        saveCollageDetails.setDepartmentName(collageDetails.getDepartmentName());
        saveCollageDetails.setDepartmentCode(collageDetails.getDepartmentCode());
        log.info("collageDetails : {}",collageDetails);
        log.info("saveCollageDetails : {}",saveCollageDetails);
        return collageDetailsRepo.save(saveCollageDetails);
    }

    public CollageDetails saveCollageDetails(StudentDepartmentDto dto){

        Student student = new Student();
        student.setStudentId((long)0);
        student.setStudentName(dto.getStudent().getStudentName());
        student.setGender(dto.getStudent().getGender());
        student.setDateOfBirth(dto.getStudent().getDateOfBirth());
        student.setFatherName(dto.getStudent().getFatherName());
        student.setMotherName(dto.getStudent().getMotherName());

        Student savedStudent = studentRepository.save(student);

        Department department = new Department();
        department.setDepartmentId((long)0);
        department.setDepartmentName(dto.getDepartment().getDepartmentName());
        department.setDepartmentCode(dto.getDepartment().getDepartmentCode());

        Department savedDepartment = departmentRepository.save(department);

        CollageDetails collageDetails = new CollageDetails();

        collageDetails.setId((long)0);
        collageDetails.setStudentName(dto.getStudent().getStudentName());
        collageDetails.setGender(dto.getStudent().getGender());
        collageDetails.setDateOfBirth(dto.getStudent().getDateOfBirth());
        collageDetails.setFatherName(dto.getStudent().getFatherName());
        collageDetails.setMotherName(dto.getStudent().getMotherName());
        collageDetails.setDepartmentName(dto.getDepartment().getDepartmentName());
        collageDetails.setDepartmentCode(dto.getDepartment().getDepartmentCode());

        return collageDetailsRepo.save(collageDetails);
    }

}
