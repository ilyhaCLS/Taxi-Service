package com.taxi.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
import com.taxi.web.model.entity.Ride;
import com.taxi.web.model.entity.UserInfo;
import com.taxi.web.model.service.RideService;
import com.taxi.web.model.service.UserService;

/**
 * Directs to account information page
 *
 */

public class AccountCommand extends Command {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(AccountCommand.class);

	/**
	 * Gathers information about authenticated user from db
	 * and stores it into {@code request} object, then forwards to account page;
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		log.info("AccountCommand starts");
		HttpSession session = req.getSession();

		UserInfo usInfo = new UserService().getUserInfo((Integer) session.getAttribute("user_id"));
		log.info("found in db usInfo:"+usInfo);
		List<Ride> rides = new RideService().getRides((Integer) session.getAttribute("user_id"), Integer.parseInt(req.getParameter("p")));
		log.info("found in db rides:" + rides);
		
		req.setAttribute("numOfPages", (int)Math.ceil(new RideService().getNumOfRides((Integer)session.getAttribute("user_id")) /10.0));
		req.setAttribute("first", usInfo.getFirst());
		req.setAttribute("last", usInfo.getLast());
		req.setAttribute("totalSpent", usInfo.getTotalSpent());
		req.setAttribute("rides", rides);

		return Path.PAGE_ACCOUNT;
	}
}