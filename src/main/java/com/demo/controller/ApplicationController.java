package com.demo.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Employee;
import com.demo.service.EmployeeService;

@RestController
@RequestMapping("/EmployeeApp")
public class ApplicationController {
	@Resource
	EmployeeService employeeService;

	@GetMapping("/employeesList")
	public List<Employee> getEmployees(@RequestParam(defaultValue = "false") boolean sort) {
		return employeeService.findAll(sort);
	}
	
	@GetMapping("/employeesList/{id}")
	public Employee getEmployeesById(@PathVariable String id) {
		return employeeService.findById(id);
	}
	
	@GetMapping("/employee/search/{name}")
	public List<Employee> getEmployeeByName(@PathVariable String name) {
		return employeeService.findByName(name);
	}
	

	@PostMapping(value = "/createEmp")
	public void create(@RequestBody Employee emp) {
		employeeService.insert(emp);
	}

	@PutMapping(value = "/updateEmp")
	public void update(@RequestBody Employee emp) {
		employeeService.update(emp);
	}

	@PutMapping(value = "/executeUpdateEmp")
	public void executeUpdateEmployee(@RequestBody Employee emp) {
		employeeService.executeUpdate(emp);
	}

	@DeleteMapping(value = "/deleteEmpById")
	public void delete(@RequestBody Employee emp) {
		employeeService.delete(emp);
	}
}
