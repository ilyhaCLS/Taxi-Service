package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;


/**
 * Directs to ride page
 *
 */
public class RideCommand extends Command {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(RideCommand.class);

	/**
	 * Deletes all possible data about previous ride order,
	 * then directs to ride page
	 * 
	 * @return Ride page
	 * @param req
	 * @param res
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		log.info("RideCommand started");
		
		HttpSession session = req.getSession();
		
		session.removeAttribute("option1");
		session.removeAttribute("option2");
		session.removeAttribute("dist");
		session.removeAttribute("time");
		session.removeAttribute("ride");
		session.removeAttribute("carClass");
		
		log.info("RideCommand finished");
		return Path.PAGE_RIDE;
	}
}