
package com.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.data.EmployeeDao;
import com.demo.entity.Employee;

@Component
public class EmployeeService {
	@Autowired
	EmployeeDao employeeDao;

	public List<Employee> findAll(boolean sort) {
		if (sort != true) {
			return employeeDao.findAll();
		}

		else {
			return findAllSorted();
		}
	}
	
	public Employee findById(String id) {
		return employeeDao.findById(id);

	}

	public List<Employee> findByName(String name) {
		List<Employee> searchedEmployee = employeeDao.findAll().stream().filter(x -> x.getName().contains(name))
				.sorted(Comparator.comparing(Employee::getName)).collect(Collectors.toList());
		return searchedEmployee;
	}

	public List<Employee> findAllSorted() {
		List<Employee> sortedEmployee = employeeDao.findAll().stream().sorted(Comparator.comparing(Employee::getName))
				.collect(Collectors.toList());
		return sortedEmployee;
	}

	public void insert(Employee emp) {
		employeeDao.insert(emp);

	}

	public void update(Employee emp) {
		employeeDao.update(emp);

	}

	public void executeUpdate(Employee emp) {
		employeeDao.executeUpdate(emp);

	}

	public void delete(Employee emp) {
		employeeDao.delete(emp);

	}
}
