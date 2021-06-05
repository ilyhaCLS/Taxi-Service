package com.taxi.web.model.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.taxi.web.model.entity.Car;
import com.taxi.web.model.entity.Ride;

public interface RideDao extends GenericDao<Ride> {
	
	public List<Ride> findRidesByUserId(int id, int page);
	
	public List<Integer> infoForADay(LocalDateTime timeFrom, LocalDateTime timeTo);
	
	public List<Ride> findAllRides(String column, String order, int page);
	
	public List<Ride> findAllRidesInPeriod(LocalDateTime timeFrom, LocalDateTime timeTo, int page);
	
	public int findNumOfRides();

	public int findNumOfRides(int user_id);
	
	public int findNumOfRides(LocalDateTime timeFrom, LocalDateTime timeTo);
	
	public Car findCarAndAddNewRide(Ride ride, String carClass)throws SQLException;
}