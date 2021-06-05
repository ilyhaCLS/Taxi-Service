package com.taxi.web.listener;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextListener implements ServletContextListener {
	
	private static final Logger log = LoggerFactory.getLogger(ContextListener.class);
	
	public void contextDestroyed(ServletContextEvent event) {
	
	}

	public void contextInitialized(ServletContextEvent event) {

		ServletContext servletContext = event.getServletContext();
		servletContext.setAttribute("loggedUsers", new HashSet<Integer>());
		log.info("initialized set of logged users ");
		
		try {
			Class.forName("com.taxi.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		
		log.info("context initialized !");
	}
}