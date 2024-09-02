package com.example.demo.vo;

import com.example.demo.entities.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVO  {

	private int number;

	@NotEmpty(message = "Department name is required")
	private String name;
	
	@NotEmpty(message = "Creator name is required")
	private String creatorName;

	public DepartmentVO(Department deparment) {
		this.number = deparment.getDepartmentId();
		this.name = deparment.getDepartmentName();
		this.creatorName = deparment.getCreatedBy();
	}
	
	@JsonIgnore
	public Department getDepartment() {
		Department department = new Department();
		department.setDepartmentId(this.number);
		department.setDepartmentName(this.name);
		department.setCreatedBy(this.creatorName);
		return department;
	}

}