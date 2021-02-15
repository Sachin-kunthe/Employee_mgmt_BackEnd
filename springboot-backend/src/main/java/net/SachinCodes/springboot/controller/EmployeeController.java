package net.SachinCodes.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import net.SachinCodes.springboot.exception.ResourceNotFoundException;
import net.SachinCodes.springboot.model.Employee;
import net.SachinCodes.springboot.repository.EmployeeRepository;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees 
	@GetMapping("/employees")
	public List<Employee> getEmployees()
	{
		return employeeRepository.findAll();
	}
	
	//create employee rest api
	@PostMapping ("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		System.out.print(employee.getFirstName() +" " + employee.getLastName()+ " "+ employee.getEmailID());
		return employeeRepository.save(employee);
		
	}
	
	// get employee by ID rest API 
	@GetMapping("/employees/{id}")
	public ResponseEntity <Employee> getEmployeeById(@PathVariable Long id )
	{
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No match found with ID :" + id));
		
		
		return ResponseEntity.ok(employee);
	}
	
	// Update employee rest API 
	@PutMapping("/employees/{id}") 
	public ResponseEntity <Employee> updateEmployee( @PathVariable Long id, @RequestBody Employee employeeDetails)
	{
      Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No match found with ID :" + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailID(employeeDetails.getEmailID());
		
		Employee updatedEmployee =employeeRepository.save(employee);
		
		System.out.print(employee.getFirstName() +" " + employee.getLastName()+ " "+ employee.getEmailID());
		
		return ResponseEntity.ok(updatedEmployee);
		
		
		
	}
	// delete employee rest API  
			@DeleteMapping("/employees/{id}")
			public ResponseEntity< Map<String, Boolean>> deleteEmployee(@PathVariable Long id)
			{
				Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No match found with ID :" + id));
				
				employeeRepository.delete(employee);
				Map<String, Boolean> response = new HashMap<>();
				response.put("deleted", Boolean.TRUE);
				return ResponseEntity.ok(response);
			}
}
