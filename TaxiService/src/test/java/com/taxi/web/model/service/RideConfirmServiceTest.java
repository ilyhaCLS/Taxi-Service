package com.taxi.web.model.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.taxi.web.Path;
import com.taxi.web.model.dao.DaoFactory;
import com.taxi.web.model.dao.RideDao;
import com.taxi.web.model.entity.Ride;
import com.taxi.web.model.entity.Ride.RideBuilder;

public class RideConfirmServiceTest {
	DaoFactory daoFactory = DaoFactory.getInstance();
	
	
	HttpServletRequest mockRequest = mock(HttpServletRequest.class);
	HttpServletResponse mockResponse = mock(HttpServletResponse.class);
	HttpSession mockSession = mock(HttpSession.class);
	

	@Test
	public void testConfirmRide() throws SQLException {
		HashMap<String, String> opt1 = new HashMap<>();
		opt1.put("carClass", "ECONOM");
		opt1.put("price", "320");
		opt1.put("numOfCars", "2");
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockRequest.getParameter("opt")).thenReturn("1");
		when(mockSession.getAttribute("option1")).thenReturn(opt1);
		when(mockSession.getAttribute("carClass")).thenReturn("ECONOM");
		when(mockSession.getAttribute("dist")).thenReturn("125 km");
		when(mockSession.getAttribute("disc")).thenReturn(30);
		Ride r = new RideBuilder().setPosFrom("площа Перемоги, 7, Київ").setPosTo("проспект Победы, 26, Киев, Украина")
				.setUserId(19).build();
		when(mockSession.getAttribute("ride")).thenReturn(r);
		
		int ridesBefore = new RideService().getNumOfRides(19);	
		
		assertEquals(Path.PAGE_RIDE_CONFIRMED, new RideConfirmService().confirmRide(mockRequest, mockResponse));
		
		assertEquals(ridesBefore + 2, new RideService().getNumOfRides(19));
	}
}