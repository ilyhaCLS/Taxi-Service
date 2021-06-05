package com.taxi.web.model.service;

import com.taxi.web.model.dao.CarDao;
import com.taxi.web.model.dao.DaoFactory;
import com.taxi.web.model.entity.Car;

public class CarService {
	
	DaoFactory daoFactory = DaoFactory.getInstance();
	
	public Car getActiveCar(String carClass){
		try(CarDao dao = daoFactory.createCarDao()){
			Car c = dao.findActiveCarByClass(carClass);
			return c;
		}
	}
}