package com.taxi.web.model.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.taxi.web.Path;

public class RideDetailsServiceTest {
	
	HttpServletRequest mockRequest = mock(HttpServletRequest.class);
	HttpServletResponse mockResponse = mock(HttpServletResponse.class);
	HttpSession mockSession = mock(HttpSession.class);
	
	

	@Test
	public void testArrangeOneRide() throws IOException {
		when(mockRequest.getParameter("posFrom")).thenReturn("площа Перемоги, 7, Київ");
		when(mockRequest.getParameter("posTo")).thenReturn("проспект Победы, 26, Киев, Украина");
		when(mockRequest.getParameter("numOfPass")).thenReturn("3");
		when(mockRequest.getParameter("carClass")).thenReturn("COMFORT");
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user_id")).thenReturn(19);
		
		assertEquals(Path.PAGE_RIDE_DETAILS, new RideDetailsService().arrangeRide(mockRequest, mockResponse));
	}

	
	@Test
	public void testArrangeCoupleRides() throws IOException {
		when(mockRequest.getParameter("posFrom")).thenReturn("площа Перемоги, 7, Київ");
		when(mockRequest.getParameter("posTo")).thenReturn("проспект Победы, 26, Киев, Украина");
		when(mockRequest.getParameter("numOfPass")).thenReturn("8");
		when(mockRequest.getParameter("carClass")).thenReturn("COMFORT");
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute("user_id")).thenReturn(19);
		
		assertEquals(Path.PAGE_RIDE_DETAILS, new RideDetailsService().arrangeRide(mockRequest, mockResponse));
	}
}