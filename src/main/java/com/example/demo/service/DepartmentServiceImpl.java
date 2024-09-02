package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.entities.Department;
import com.example.demo.exceptions.ClientErrorException;
import com.example.demo.vo.DepartmentVO;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	DepartmentRepository departmentRepository;
	
	
	@Override
	public List<DepartmentVO> getAllDepartments() {
		List<Department> departments = departmentRepository.findAll();
		List<DepartmentVO> departmentVOList = departments.stream()
                .map(department -> {
                	DepartmentVO vo = new DepartmentVO();
                	vo.setNumber(department.getDepartmentId());
                	vo.setName(department.getDepartmentName());
                	vo.setCreatorName(department.getCreatedBy());
                	return vo;
                })
                .collect(Collectors.toList());
		return departmentVOList;
	}

	@Override
	public DepartmentVO createDepartment(DepartmentVO departmentVO) {
		
		Department insertDepartment = new Department();
		insertDepartment.setDepartmentName(departmentVO.getName());
		insertDepartment.setCreatedBy(departmentVO.getCreatorName());
		
		
		Department department = departmentRepository.save(insertDepartment);
		
		DepartmentVO vo = new DepartmentVO();
    	vo.setNumber(department.getDepartmentId());
    	vo.setName(department.getDepartmentName());
    	vo.setCreatorName(department.getCreatedBy());
		
		return vo;
	}

	@Override
	public DepartmentVO getDepartmentById(int departmentId) {
		Optional<Department> department = departmentRepository.findById(departmentId);
		
		DepartmentVO vo = new DepartmentVO();
    	vo.setNumber(department.get().getDepartmentId());
    	vo.setName(department.get().getDepartmentName());
    	vo.setCreatorName(department.get().getCreatedBy());
		
    	return vo;
	}

	@Override
	public DepartmentVO updateDepartment(int departmentId,DepartmentVO departmentDetails) {
		Optional<Department> department = departmentRepository.findById(departmentId);
		Department modifiedDepartment = null;
		try {
			if(department.get() != null) {
				modifiedDepartment = department.get();
				modifiedDepartment.setDepartmentName(departmentDetails.getName());
				modifiedDepartment.setCreatedBy(departmentDetails.getCreatorName());
				modifiedDepartment = departmentRepository.save(modifiedDepartment);
			}
		} catch (NoSuchElementException e) {
			throw new ClientErrorException(String.format("Department Id not found %d",departmentId));
		}
		
		DepartmentVO vo = new DepartmentVO();
    	vo.setNumber(modifiedDepartment.getDepartmentId());
    	vo.setName(modifiedDepartment.getDepartmentName());
    	vo.setCreatorName(modifiedDepartment.getCreatedBy());
		
		return vo;
	}

	
	@Override
	public DepartmentVO deleteDepartment(int departmentId) {
		Optional<Department> department = departmentRepository.findById(departmentId);
		if(department.get() != null) {
			 departmentRepository.delete(department.get());
	    }
		
		DepartmentVO vo = new DepartmentVO();
    	vo.setNumber(department.get().getDepartmentId());
    	vo.setName(department.get().getDepartmentName());
    	vo.setCreatorName(department.get().getCreatedBy());
		
		return vo;
	}
	
	
}
