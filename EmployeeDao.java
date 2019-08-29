package com.demo.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.demo.entity.Employee;

@Repository
public class EmployeeDao {
	public EmployeeDao(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	NamedParameterJdbcTemplate template;

	public List<Employee> findAll() {
		return template.query("select * from employee", new EmployeeRowMapper());
	}
	
	public Employee findById(String id) {
		final String sql = "select * from employee where employeeId=:employeeId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("employeeId", id);
		return template.queryForObject(sql, parameters, new EmployeeRowMapper());
	}

	public void insert(Employee emp) {
		final String sql = "insert into employee(employeeId, employeeName ,employeeEmail) values(:employeeId,:employeeName,:employeeEmail)";
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource().addValue("employeeId", emp.getId())
				.addValue("employeeName", emp.getName()).addValue("employeeEmail", emp.getEmail());
		template.update(sql, param, holder);
	}

	public void update(Employee emp) {
		final String sql = "update employee set employeeName=:employeeName, employeeEmail=:employeeEmail where employeeId=:employeeId";
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource().addValue("employeeId", emp.getId())
				.addValue("employeeName", emp.getName()).addValue("employeeEmail", emp.getEmail());

		template.update(sql, param, holder);
	}

	public void executeUpdate(Employee emp) {
		final String sql = "update employee set employeeName=:employeeName, employeeEmail=:employeeEmail where employeeId=:employeeId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("employeeId", emp.getId());
		map.put("employeeName", emp.getName());
		map.put("employeeEmail", emp.getEmail());
		template.execute(sql, map, new PreparedStatementCallback<Object>() {

			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}

	public void delete(Employee emp) {
		final String sql = "delete from employee where employeeId=:employeeId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("employeeId", emp.getId());
		template.execute(sql, map, new PreparedStatementCallback<Object>() {

			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
	}
}
