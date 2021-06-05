package com.taxi.web.model.dao.impl;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.taxi.web.model.dao.RideDao;
import com.taxi.web.model.entity.Car;
import com.taxi.web.model.entity.Ride;
import com.taxi.web.model.entity.Ride.RideBuilder;

public class JDBCRideDao implements RideDao {

	private Connection connection;

	public JDBCRideDao(Connection connection) {
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
	public int findNumOfRides() {
		int res = 0;
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT COUNT(id) FROM ride");

			if (rs.next()) {
				res = rs.getInt(1);
			}
			rs.close();
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			return res;
		}
	}

	@Override
	public int findNumOfRides(int user_id) {
		int res = 0;
		try (PreparedStatement preStatement = connection
				.prepareStatement("SELECT COUNT(id) FROM ride WHERE user_id = ?")) {
			preStatement.setInt(1, user_id);

			ResultSet rs = preStatement.executeQuery();

			if (rs.next()) {
				res = rs.getInt(1);
			}
			rs.close();
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			return res;
		}
	}

	@Override
	public int findNumOfRides(LocalDateTime timeFrom, LocalDateTime timeTo) {
		int res = 0;
		try (PreparedStatement preStatement = connection
				.prepareStatement("SELECT COUNT(id) FROM ride WHERE creation_time >= ? AND creation_time <= ?")) {
			preStatement.setString(1, timeFrom.toString());
			preStatement.setString(2, timeTo.toString());
			ResultSet rs = preStatement.executeQuery();

			if (rs.next()) {
				res = rs.getInt(1);
			}
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			return res;
		}
	}

	@Override
	public List<Ride> findRidesByUserId(int id, int page) {
		try (PreparedStatement preStatement = connection.prepareStatement(
				"SELECT * FROM ride r JOIN car c ON r.car_id = c.id WHERE r.user_id = ? ORDER BY creation_time DESC LIMIT ? , 10")) {

			List<Ride> rides = new ArrayList<>();
			Ride ride = null;
			preStatement.setInt(1, id);
			preStatement.setInt(2, (page * 10) - 10);
			ResultSet rs = preStatement.executeQuery();
			while (rs.next()) {
				ride = new RideBuilder().setId(rs.getInt(1)).setPosFrom(rs.getString(2)).setPosTo(rs.getString(3))
						.setPrice(rs.getInt(4))
						.setCreationTime(LocalDateTime.parse(rs.getString(5),
								DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.setUserId(rs.getInt("user_id"))
						.setCar(new Car(rs.getString("lic_plate"), rs.getString("class"), rs.getString("name")))
						.build();
				rides.add(ride);
			}
			rs.close();
			return rides;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public List<Integer> infoForADay(LocalDateTime timeFrom, LocalDateTime timeTo) {
		List<Integer> res = new ArrayList<>();
		try (PreparedStatement preStatement = connection
				.prepareStatement("SELECT price FROM ride WHERE creation_time >= ? AND creation_time <= ?")) {
			preStatement.setString(1, timeFrom.toString());
			preStatement.setString(2, timeTo.toString());
			ResultSet rs = preStatement.executeQuery();

			while (rs.next()) {
				res.add(rs.getInt(1));
			}
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			return res;
		}
	}

	@Override
	public List<Ride> findAllRides(String column, String order, int page) {
		try (PreparedStatement preStatement = connection
				.prepareStatement("SELECT * FROM ride r JOIN car c ON r.car_id = c.id ORDER BY " + column + " " + order
						+ " LIMIT ? , 10")) {

			List<Ride> rides = new ArrayList<>();
			Ride ride = null;
			preStatement.setInt(1, (page * 10) - 10);
			ResultSet rs = preStatement.executeQuery();
			while (rs.next()) {
				ride = new RideBuilder().setId(rs.getInt(1)).setPosFrom(rs.getString(2)).setPosTo(rs.getString(3))
						.setPrice(rs.getInt(4))
						.setCreationTime(LocalDateTime.parse(rs.getString(5),
								DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.setUserId(rs.getInt("user_id"))
						.setCar(new Car(rs.getString("lic_plate"), rs.getString("class"), rs.getString("name")))
						.build();

				rides.add(ride);
			}
			rs.close();
			return rides;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public List<Ride> findAllRidesInPeriod(LocalDateTime timeFrom, LocalDateTime timeTo, int page) {
		try (PreparedStatement preStatement = connection.prepareStatement(
				"SELECT * FROM ride r JOIN car c ON r.car_id = c.id WHERE creation_time >= ? AND creation_time <= ?"
						+ " ORDER BY creation_time DESC LIMIT ? , 10")) {

			System.out.println(timeFrom);
			System.out.println(timeTo);

			preStatement.setString(1, timeFrom.toString());
			preStatement.setString(2, timeTo.toString());
			preStatement.setInt(3, (page * 10) - 10);
			ResultSet rs = preStatement.executeQuery();

			List<Ride> rides = new ArrayList<>();

			Ride ride = null;
			while (rs.next()) {
				ride = new RideBuilder().setId(rs.getInt(1)).setPosFrom(rs.getString(2)).setPosTo(rs.getString(3))
						.setPrice(rs.getInt(4))
						.setCreationTime(LocalDateTime.parse(rs.getString(5),
								DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
						.setUserId(rs.getInt("user_id"))
						.setCar(new Car(rs.getString("lic_plate"), rs.getString("class"), rs.getString("name")))
						.build();
				rides.add(ride);
			}
			rs.close();
			return rides;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public Car findCarAndAddNewRide(Ride ride, String carClass) throws SQLException {
		try (PreparedStatement preStatementCar = connection
				.prepareStatement("SELECT id, lic_plate, name FROM car WHERE class = ? AND status = 'ACTIVE'");
			 PreparedStatement preStatementRide = connection
				.prepareStatement("INSERT INTO ride (pos_from, pos_to, price, creation_time, car_id, user_id)"
							+ " VALUES (?, ?, ?, ?, ?, ?)")) {

			preStatementCar.setString(1, carClass);
			
			connection.setAutoCommit(false);
			
			ResultSet rs = preStatementCar.executeQuery();
			
			List<Car> cars = new ArrayList<>();
			Car c = null;
			while (rs.next()) {
				c = new Car();
				c.setId(rs.getInt("id"));
				c.setLicPlate(rs.getString("lic_plate"));
				c.setName(rs.getString("name"));
				cars.add(c);
			}
			rs.close();
			c = cars.get(cars.size() > 1 ? new SecureRandom().nextInt(cars.size()) : 0);

			preStatementRide.setString(1, ride.getPosFrom());
			preStatementRide.setString(2, ride.getPosTo());
			preStatementRide.setInt(3, ride.getPrice());
			preStatementRide.setString(4, LocalDateTime.now().toString());
			preStatementRide.setInt(5, c.getId());
			preStatementRide.setInt(6, ride.getUserId());

			preStatementRide.execute();
			connection.commit();
			return c;
		} catch (SQLException e) {
			connection.rollback();
			System.out.println(e);
			return null;
		}finally {
			connection.setAutoCommit(true);
		}
	}
}