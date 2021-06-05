package com.taxi.web.model.dao.impl;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taxi.web.model.dao.CarDao;
import com.taxi.web.model.entity.Car;

public class JDBCCarDao implements CarDao {
	
	private Connection connection;


    public JDBCCarDao(Connection connection) {
        this.connection = connection;
    }
    

	@Override
	public void close() {
		try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}


	@Override
	public Car findActiveCarByClass(String carClass) {
		try (PreparedStatement preStatement = connection.prepareStatement(
						"SELECT id, lic_plate, name FROM car WHERE class = ? AND status = 'ACTIVE'")) {

			preStatement.setString(1, carClass);
			List<Car> cars = new ArrayList<>();
			Car c = null;

			ResultSet rs = preStatement.executeQuery();
			while (rs.next()) {
				c = new Car();
				c.setId(rs.getInt("id"));
				c.setLicPlate(rs.getString("lic_plate"));
				c.setName(rs.getString("name"));
				cars.add(c);
			}
			
			rs.close();

			c = cars.get(cars.size() > 1 ? new SecureRandom().nextInt(cars.size()) : 0);

			return c;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}
}