package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.EmployeeVO;

public interface EmployeeService {
	public List<EmployeeVO> getAllEmployees();
	public EmployeeVO createEmployee(EmployeeVO employee);
	public EmployeeVO getEmployeeById(int employeeId);
	public EmployeeVO updateEmployee(int employeeId,EmployeeVO employeeDetails);
	public EmployeeVO deleteEmployee(int employeeId);
}
