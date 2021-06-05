package com.taxi.web.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;

import com.taxi.web.model.entity.Ride;
import com.taxi.web.model.service.RideConfirmService;


/**
 * Directs to ride confirm page
 */

public class RideConfirmCommand extends Command {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(RideConfirmCommand.class);

	
	/**
	 * Takes info about ride from session object and tries to add it to db.
	 * 
	 * @return Ride confirm page if succeeded, else error page
	 * @param req
	 * @param res
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res){
		log.info("RideConfirmCommand started");
		HttpSession session = req.getSession(); 
		
		Ride r = (Ride)session.getAttribute("ride");
		if(r == null) {
			log.info("ride attribute is null");
			return Path.PAGE_ERROR;
		}
		
		try {
			log.info("RideConfirmCommand finished");
			return new RideConfirmService().confirmRide(req, res);
		} catch (SQLException e) {
			e.printStackTrace();
			return Path.PAGE_ERROR;
		}
	}
}