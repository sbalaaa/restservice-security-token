package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DepartmentService;
import com.example.demo.vo.DepartmentVO;
/*
 * http://localhost:8080/api/departments
 * 
 * 
 * 
 * 
 * 
 */


@RestController
@RequestMapping("/api")
@Validated
public class DepartmentController {
	
	private static final org.slf4j.Logger log = 
		    org.slf4j.LoggerFactory.getLogger(DepartmentController.class);

	
	@Autowired
	DepartmentService departmentService;
	
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @CrossOrigin(origins="*", maxAge = 3600)
    @GetMapping("/departments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<DepartmentVO>> getAllDepartments() {
		log.debug("Start - getAllDepartments");
		List<DepartmentVO> departments = departmentService.getAllDepartments();
		return new ResponseEntity<List<DepartmentVO>>(departments, HttpStatus.OK);
	}
	
    @CrossOrigin(origins="*")
    //@PostMapping("/departments")
    @PostMapping(value="/departments",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CREATE') or hasRole('UPDATE') or hasRole('ADMIN')")
    public DepartmentVO createDepartment(@Valid @RequestBody DepartmentVO department) {
    	log.debug("Start - createDepartment");
		return departmentService.createDepartment(department);
	}
	
    @CrossOrigin(origins="*", maxAge = 3600)
    @RequestMapping("/departments/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DepartmentVO> getDepartmentById(@Valid @Positive @PathVariable(value = "id") int departmentId) {
    	log.debug("Start - getDepartmentById --> {} " , departmentId);
		DepartmentVO department = departmentService.getDepartmentById(departmentId);
	    if(department == null) {
	    	log.info("Department Not Found");
	       return ResponseEntity.notFound().build();
	    } else {
	    	log.info("Department Found Name is: {} " , department.getName());
	    }
	    return ResponseEntity.ok(department);
	}
	
    @CrossOrigin(origins="*", maxAge = 3600)
    @PutMapping(value="/departments/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CREATE') or hasRole('UPDATE') or hasRole('ADMIN')")
    public ResponseEntity<DepartmentVO> updateDepartment(@PathVariable(value = "id") int departmentId, 
	                                       @Valid @RequestBody DepartmentVO departmentDetails) {
		log.debug("Start - updateDepartment with ID --> {} " , departmentId);
		DepartmentVO department = departmentService.updateDepartment(departmentId,departmentDetails);
	    if(department == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(department);
	}
    

    @CrossOrigin(origins="*", maxAge = 3600)
    @DeleteMapping("/departments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentVO> deleteDepartment(@PathVariable(value = "id") int departmentId) {
    	log.debug("Start - deleteDepartment with ID --> {}" + departmentId );
		DepartmentVO department = departmentService.deleteDepartment(departmentId);
	    if(department == null) {
	        return ResponseEntity.notFound().build();
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
