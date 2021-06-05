package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.model.service.RideDetailsService;

/**
 * Creates ride object and redirects to rideConfirm page
 *
 */
public class RideDetailsCommand extends Command {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(RideDetailsCommand.class);
	

	
	/**
	 * Creates ride object and adds it to the session for further actions
	 * 
	 * @return Ride details page if succeeded, else error page
	 * @param req
	 * @param res
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		log.info("RideDetailsCommand started");
		
		String forward = new RideDetailsService().arrangeRide(req, res);
		
		log.info("RideDetailsCommand finished");
		return forward;
	}
}