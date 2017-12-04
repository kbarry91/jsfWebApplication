package com.geog.DAO;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.geog.Model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;

public class SQLDAO {
	private DataSource mysqlDS;

	public SQLDAO() throws Exception {
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/geography";
		mysqlDS = (DataSource) context.lookup(jndiName);

	}//public SQLDAO() throws Exception {
	

	public ArrayList<Country> loadCountries() throws SQLException {
		Connection conn = mysqlDS.getConnection();
		final Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery("SELECT * FROM COUNTRY");
		final ArrayList<Country> countries = new ArrayList<Country>();
	
		while (rs.next()) {
			final Country country = new Country();
			country.setCode(rs.getString("co_code"));
			country.setName(rs.getString("co_name"));
			country.setDetails(rs.getString("co_details"));
			countries.add(country);
		}
		return countries;
	}//load countries
	public void addCountry(Country country)throws Exception{
		Connection conn = mysqlDS.getConnection();
		PreparedStatement stmt = null;
		
		String sql = "insert into country values (?, ?, ?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, country.getCode());
		stmt.setString(2, country.getName());
		stmt.setString(3, country.getDetails());

		stmt.execute();	
	}
}//SQLDAO
