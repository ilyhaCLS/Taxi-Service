package com.taxi.web.model.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.taxi.web.Path;

public class RegisterServiceTest {
	
	HttpServletRequest mockRequest = mock(HttpServletRequest.class);
	HttpServletResponse mockResponse = mock(HttpServletResponse.class);
	HttpSession mockSession = mock(HttpSession.class);
	ServletContext mockContext = mock(ServletContext.class);
	

	@Test
	public void testRegisterServiceSuccess() {
		when(mockRequest.getParameter("login")).thenReturn("fff");
		when(mockRequest.getParameter("password")).thenReturn("fff");
		when(mockRequest.getParameter("first")).thenReturn("Ivanov");
		when(mockRequest.getParameter("last")).thenReturn("Ivan");
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getServletContext()).thenReturn(mockContext);
		when(mockContext.getAttribute("loggedUsers")).thenReturn(new HashSet<Integer>());
		
		assertEquals(Path.PAGE_LOGIN, new RegisterService().registerOperation(mockRequest, mockResponse));
	}
	
	@Test
	public void testRegisterServiceFail() {
		when(mockRequest.getParameter("login")).thenReturn("ff");
		when(mockRequest.getParameter("password")).thenReturn("ff");
		when(mockRequest.getParameter("first")).thenReturn("Ivanov");
		when(mockRequest.getParameter("last")).thenReturn("Ivan");
		
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getServletContext()).thenReturn(mockContext);
		when(mockContext.getAttribute("loggedUsers")).thenReturn(new HashSet<Integer>());
		
		assertEquals(Path.PAGE_REGISTER, new RegisterService().registerOperation(mockRequest, mockResponse));
	}
	
}