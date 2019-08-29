package com.demo.entity;


public class Employee implements Comparable<Employee> {

	private String id;
	private String name;
	private String email;


	public String getId() {
		return id;
	}

	public void setId(String employeeId) {
		this.id = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String employeeName) {
		this.name = employeeName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String employeeEmail) {
		this.email = employeeEmail;
	}

	@Override
	public int compareTo(Employee ob) {
		return name.compareTo(ob.getName());
	}

}
