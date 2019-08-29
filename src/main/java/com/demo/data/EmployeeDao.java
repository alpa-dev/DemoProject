package com.demo.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(EmployeeDao.class);

	public List<Employee> findAll() {
		log.info("Listing all Employees");
		return template.query("select * from employee", (ResultSet rs, int rowNumber) -> {
			Employee emp = new Employee();
			emp.setId(rs.getString("employeeId"));
			emp.setName(rs.getString("employeeName"));
			emp.setEmail(rs.getString("employeeEmail"));
			return emp;
		});
	}

	public Employee findById(String id) {
		log.info("Finding Employees by id =" + id);
		final String sql = "select * from employee where employeeId=:employeeId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("employeeId", id);
		return template.queryForObject(sql, parameters, (ResultSet rs, int rowNumber) -> {
			Employee emp = new Employee();
			emp.setId(rs.getString("employeeId"));
			emp.setName(rs.getString("employeeName"));
			emp.setEmail(rs.getString("employeeEmail"));
			return emp;
		});
	}

	public void insert(Employee emp) {
		log.info("Inserting new Employee with employee id =" + emp.getId());
		final String sql = "insert into employee(employeeId, employeeName ,employeeEmail) values(:employeeId,:employeeName,:employeeEmail)";
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource().addValue("employeeId", emp.getId())
				.addValue("employeeName", emp.getName()).addValue("employeeEmail", emp.getEmail());
		template.update(sql, param, holder);
	}

	public void update(Employee emp) {
		log.info("Updating Employee with employee id =" + emp.getId());
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
