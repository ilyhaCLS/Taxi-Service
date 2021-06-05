package com.taxi.web.model.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.taxi.web.model.dao.CarDao;
import com.taxi.web.model.dao.DaoFactory;
import com.taxi.web.model.entity.Car;

public class CarServiceTest {
	DaoFactory daoFactory = DaoFactory.getInstance();

	@Test
	public void testGetActiveCar() {
		try (CarDao dao = daoFactory.createCarDao()) {
			Car c = dao.findActiveCarByClass("ELITE");
			assertEquals("AA0770AA", c.getLicPlate());
		}
	}
}