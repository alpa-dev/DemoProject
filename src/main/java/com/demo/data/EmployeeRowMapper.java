package com.demo.data;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.demo.entity.Employee;
public class EmployeeRowMapper implements RowMapper<Employee> {
@Override
public Employee mapRow(ResultSet rs, int arg1) throws SQLException {
Employee emp = new Employee();
emp.setId(rs.getString("employeeId"));
emp.setName(rs.getString("employeeName"));
emp.setEmail(rs.getString("employeeEmail"));
        return emp;
}
}

