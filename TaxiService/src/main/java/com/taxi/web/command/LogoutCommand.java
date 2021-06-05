package com.taxi.web.command;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;

/**
 * Logs out client and directs to index page
 *
 */
public class LogoutCommand extends Command {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(LogoutCommand.class);
	
	
	/**
	 * Logs out client, invalidates session, deletes user_id from ServletContext
	 * @return Index page
	 * @param req
	 * @param res
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		log.info("LogoutCommand started");
		
		HttpSession session = req.getSession();
		HashSet<Integer> loggedUsers =  (HashSet<Integer>)session.getServletContext().getAttribute("loggedUsers");
		loggedUsers.remove(session.getAttribute("user_id"));
		log.info("deleted user " + session.getAttribute("user_id")+ " from logged users");
		session.invalidate();

		log.info("LogoutCommand finished");
		return "redirect:"+Path.PAGE_INDEX;
	}
}