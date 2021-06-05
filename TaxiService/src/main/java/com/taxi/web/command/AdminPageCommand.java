package com.taxi.web.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
import com.taxi.web.model.service.RideService;


/**
 * Directs to admin page
 *
 */
public class AdminPageCommand extends Command {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(AdminPageCommand.class);

	
	/**
	 * Gathers information about rides for the past 24 hours from db
	 * and stores it into {@code request} object, then forwards to account page;
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		log.info("AdminPageCommand starts");

		List<Integer> dayInfo =  new RideService().getDayInfo(LocalDateTime.now().minusDays(1), LocalDateTime.now());
		log.info("found in db dayInfo: "+dayInfo);
		int sum = dayInfo.stream().collect(Collectors.summingInt(Integer::intValue));
		
		req.setAttribute("ridesInADay", dayInfo.size());
		req.setAttribute("sumInADay", sum);
		log.info("AdminPageCommand finished");
		
		return Path.PAGE_ADMIN;
	}
}