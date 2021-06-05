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


public class LoginServiceTest {
	HttpServletRequest mockRequest = mock(HttpServletRequest.class);
	HttpServletResponse mockResponse = mock(HttpServletResponse.class);
	HttpSession mockSession = mock(HttpSession.class);
	ServletContext mockContext = mock(ServletContext.class);

	@Test
	public void testLoginServiceAllowedClient() {
		when(mockRequest.getParameter("login")).thenReturn("ff");
		when(mockRequest.getParameter("password")).thenReturn("ff");
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getServletContext()).thenReturn(mockContext);
		when(mockContext.getAttribute("loggedUsers")).thenReturn(new HashSet<Integer>());
		
		assertEquals("redirect:"+Path.PAGE_INDEX, new LoginService().loginOperation(mockRequest, mockResponse));
	}
	
	@Test
	public void testLoginServiceAllowedAdmin() {
		when(mockRequest.getParameter("login")).thenReturn("zz");
		when(mockRequest.getParameter("password")).thenReturn("zz");
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getServletContext()).thenReturn(mockContext);
		when(mockContext.getAttribute("loggedUsers")).thenReturn(new HashSet<Integer>());
		
		assertEquals("redirect:"+"/controller?command=adminPage", new LoginService().loginOperation(mockRequest, mockResponse));
	}
	
	@Test
	public void testLoginServiceDenied() {
		when(mockRequest.getParameter("login")).thenReturn("ff23");
		when(mockRequest.getParameter("password")).thenReturn("ff23");
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getServletContext()).thenReturn(mockContext);
		when(mockContext.getAttribute("loggedUsers")).thenReturn(new HashSet<Integer>());
		
		assertEquals(Path.PAGE_LOGIN, new LoginService().loginOperation(mockRequest, mockResponse));
	}
}
