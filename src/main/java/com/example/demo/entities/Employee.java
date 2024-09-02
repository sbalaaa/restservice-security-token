package com.example.demo.entities;



import java.time.LocalDate;

import com.example.demo.vo.DepartmentVO;
import com.example.demo.vo.EmployeeVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="EMPLOYEES")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee {

	@Id
	@Column(name="EMPLOYEE_ID")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int employeeId;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="AGE")
	private int age;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name="HIRE_DATE")
	private LocalDate hireDate;
	
	@Column(name="SALARY")
	private Integer salary;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID_FK", referencedColumnName="DEPARTMENT_ID", nullable = false)
	private Department departmentId;
	
	public Employee(EmployeeVO vo) {
		this.employeeId = vo.getEmployeeId();
		this.firstName = vo.getFirstName();
		this.lastName = vo.getLastName();
		this.gender = vo.getGender();
		this.age = vo.getAge();
		this.email = vo.getEmail();
		this.hireDate = vo.getHireDate();
		this.salary = vo.getSalary();
		this.createdBy = vo.getCreatedBy();
		Department department = new Department(vo.getDepartmentId().getNumber(),vo.getDepartmentId().getName(),vo.getDepartmentId().getCreatorName());
		this.departmentId = department;
	}

	
	
	public EmployeeVO getEmployeeVO() {
		EmployeeVO vo = new EmployeeVO();
		vo.setEmployeeId(employeeId);
		vo.setFirstName(firstName);
		vo.setLastName(lastName);
		vo.setGender(gender);
		vo.setAge(age);
		vo.setEmail(email);
		vo.setPhoneNumber(phoneNumber);
		vo.setHireDate(hireDate);
		vo.setSalary(salary);
		
		DepartmentVO departmentVO = new DepartmentVO(this.getDepartmentId());
		vo.setDepartmentId(departmentVO);
		return vo;
	}



}
