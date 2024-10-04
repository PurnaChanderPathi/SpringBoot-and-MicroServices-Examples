package com.purna.service;

import com.purna.entity.Department;
import com.purna.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department){
        Department saveDep = new Department();
        saveDep.setDepartmentId((long)0);
        saveDep.setDepartmentName(department.getDepartmentName());
        saveDep.setDepartmentCode(department.getDepartmentCode());
        return departmentRepository.save(saveDep);
    }
}
