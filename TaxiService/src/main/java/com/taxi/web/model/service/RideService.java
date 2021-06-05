package com.taxi.web.model.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.taxi.web.model.dao.DaoFactory;
import com.taxi.web.model.dao.RideDao;
import com.taxi.web.model.entity.Car;
import com.taxi.web.model.entity.Ride;

public class RideService {
	
	DaoFactory daoFactory = DaoFactory.getInstance();
	
	
	public int getNumOfRides() {
		try(RideDao dao = daoFactory.createRideDao()){
			return dao.findNumOfRides();
		}
	}
	
	public int getNumOfRides(int user_id) {
		try(RideDao dao = daoFactory.createRideDao()){
			return dao.findNumOfRides(user_id);
		}
	}
	
	public List<Ride> getRides(int userId, int page){
		try(RideDao dao = daoFactory.createRideDao()){
			return dao.findRidesByUserId(userId, page);
		}
	}
	
	public int getNumOfRides(LocalDateTime timeFrom, LocalDateTime timeTo){
		try(RideDao dao = daoFactory.createRideDao()){
			return dao.findNumOfRides(timeFrom, timeTo);
		}
	}
	
	public List<Integer> getDayInfo(LocalDateTime timeFrom, LocalDateTime timeTo){
		try(RideDao dao = daoFactory.createRideDao()){
			return dao.infoForADay(timeFrom, timeTo);
		}
	}
	
	public List<Ride> getSortedRides(String column, String order, int page){
		try(RideDao dao = daoFactory.createRideDao()){
			return dao.findAllRides(column, order, page);
		}
	}
	
	public List<Ride> getRidesByPeriod(LocalDateTime timeFrom, LocalDateTime timeTo, int page){
		try(RideDao dao = daoFactory.createRideDao()){
			return dao.findAllRidesInPeriod(timeFrom, timeTo, page);
		}
	}
	
	public Car addRide(Ride ride, String carClass) throws SQLException {
		try(RideDao dao = daoFactory.createRideDao()){
			return dao.findCarAndAddNewRide(ride, carClass);
		}
	}
	
	
}