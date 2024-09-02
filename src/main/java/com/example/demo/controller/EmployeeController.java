package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.EmployeeService;
import com.example.demo.vo.EmployeeVO;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	private static final org.slf4j.Logger log = 
		    org.slf4j.LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;
	
    @CrossOrigin(origins="*", maxAge = 3600)
    @GetMapping("/employees")
	public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
		System.out.println("Start - getAllEmployees");
		List<EmployeeVO> employees = employeeService.getAllEmployees();
		return new ResponseEntity(employees, HttpStatus.OK);
	}
	
    @CrossOrigin(origins="*")
    @PostMapping("/employees")
	public EmployeeVO createDepartment(@Valid @RequestBody EmployeeVO employee) {
		System.out.println("Start - createEmployee");
		return employeeService.createEmployee(employee);
	}
	
    @CrossOrigin(origins="*", maxAge = 3600)
    @RequestMapping("/employees/{id}")
	public ResponseEntity<EmployeeVO> getEmployeeById(@PathVariable(value = "id") int employeeId) {
		System.out.println("Start - getEmployeeById --> " + employeeId);
		EmployeeVO employee = employeeService.getEmployeeById(employeeId);
	    if(employee == null) {
	    	System.out.println("Employee Not Found");
	       return ResponseEntity.notFound().build();
	    } else {
	    	System.out.println("Employee Found Name is:" + employee.getFirstName());
	    }
	    return ResponseEntity.ok(employee);
	}
	
    @CrossOrigin(origins="*", maxAge = 3600)
    @PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeVO> updateEmployee(@PathVariable(value = "id") int employeeId, 
	                                       @Valid @RequestBody EmployeeVO employeeDetails) {
		System.out.println("Start - updateEmployee with id--> " + employeeId);
		EmployeeVO employee = employeeService.updateEmployee(employeeId,employeeDetails);
	    if(employee == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(employee);
	}
    
    @CrossOrigin(origins="*", maxAge = 3600)
    @DeleteMapping("/employees/{id}")
	public ResponseEntity<EmployeeVO> deleteDepartment(@PathVariable(value = "id") int employeeId) {
		System.out.println("Start - deleteEmployee with Id --> " + employeeId );
		EmployeeVO employee = employeeService.deleteEmployee(employeeId);
	    try {
			if(employee == null) {
		        return ResponseEntity.notFound().build();
		    }
	    } catch(Exception e) {
	    	log.error("Exception occured {}" , e.toString());
	    }
	    return ResponseEntity.ok().build();
	}
    
    /*@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoResource() {
    	ErrorResponse errorResponse = new ErrorResponse();
    	errorResponse.setCode("400");
    	errorResponse.setStatus("Request Failed");
    	errorResponse.setReason("Requested Resource is not available");
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }*/

}
