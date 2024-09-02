package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.DepartmentVO;

public interface DepartmentService {
	public List<DepartmentVO> getAllDepartments();
	public DepartmentVO createDepartment(DepartmentVO department);
	public DepartmentVO getDepartmentById(int departmentId);
	public DepartmentVO updateDepartment(int departmentId,DepartmentVO departmentDetails);
	public DepartmentVO deleteDepartment(int departmentId);
}
