package com.taxi.web.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
import com.taxi.web.model.entity.Ride;
import com.taxi.web.model.service.RideService;


/**
 * Creates set of rides and redirects to orders page
 *
 */
public class ShowRidesCommand extends Command {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(ShowRidesCommand.class);
	
	
	/**
	 * Adds rides based on parameters into request object and displays them on orders page
	 * 
	 * @return Ride details page if succeeded, else error page
	 * @param req
	 * @param res
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		log.info("ShowRideCommand started");
		
		log.info("request parameter q -->" + req.getParameter("q"));
		log.info("request parameter order -->" + req.getParameter("order"));

		HttpSession session  = req.getSession();
		
		RideService rideService = new RideService();

		List<Ride> rides = null;

		if (req.getParameter("q").equals("all")) {
			switch (String.valueOf(req.getParameter("order"))) {
			case "desc":
				rides = rideService.getSortedRides("creation_time", "DESC", Integer.parseInt(req.getParameter("p")));
				break;
			case "asc":
				rides = rideService.getSortedRides("creation_time", "ASC", Integer.parseInt(req.getParameter("p")));
				break;
			case "exp":
				rides = rideService.getSortedRides("price", "DESC", Integer.parseInt(req.getParameter("p")));
				break;
			case "cheap":
				rides = rideService.getSortedRides("price", "ASC", Integer.parseInt(req.getParameter("p")));
				break;
			default:
				return Path.PAGE_ERROR;
			}
			req.setAttribute("numOfPages", (int)Math.ceil(new RideService().getNumOfRides() /10.0));
		}
		
		if(req.getParameter("q").equals("date")){
			if(req.getParameter("from") != null) {
				session.setAttribute("from", req.getParameter("from"));
				session.setAttribute("until", req.getParameter("until"));
			}
			
			rides = rideService.getRidesByPeriod(LocalDateTime.parse((CharSequence) session.getAttribute("from")),
					LocalDateTime.parse((CharSequence) session.getAttribute("until")), Integer.parseInt(req.getParameter("p")));
			
			req.setAttribute("numOfPages", (int)Math.ceil(new RideService().getNumOfRides(LocalDateTime.parse((CharSequence) session.getAttribute("from")),
					LocalDateTime.parse((CharSequence) session.getAttribute("until"))) /10.0));
		}

		if (req.getParameter("q").equals("us")) {
			if(req.getParameter("user_id") != null) {
				session.setAttribute("user_id", Integer.parseInt(req.getParameter("user_id")));
			}
			
			rides = rideService.getRides((Integer)session.getAttribute("user_id"), Integer.parseInt(req.getParameter("p")));
			req.setAttribute("numOfPages", (int)Math.ceil(new RideService().getNumOfRides((Integer)session.getAttribute("user_id")) /10.0));
		}

		req.setAttribute("rides", rides);

		log.info("ShowRideCommand finished");
		return Path.PAGE_ORDERS;
	}
}