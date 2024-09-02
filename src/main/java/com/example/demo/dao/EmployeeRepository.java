package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByGender(String value);
	List<Employee> findByGenderAndAge(String gender,int age);
	List<Employee> findByFirstNameAndLastName(String value,String value2);
	@Query("from Employee e where e.firstName=:firstName")
	List<Employee> getAllEmployeByFirstName(@Param("firstName") String firstName );
	@Query("from Employee where age>:min and age<:max") // JPQL
	List<Employee>  findEmployeesGivenAges(@Param("min") int min,@Param("max") int max);
	
	@Query(value="select * from Employee",nativeQuery=true)
	List<Employee> getAllEmployees();
	
}
