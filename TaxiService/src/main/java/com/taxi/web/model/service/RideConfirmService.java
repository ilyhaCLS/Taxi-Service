package com.taxi.web.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
import com.taxi.web.model.entity.Car;
import com.taxi.web.model.entity.CarClass;
import com.taxi.web.model.entity.Ride;

public class RideConfirmService {
	private static final Logger log = LoggerFactory.getLogger(RideConfirmService.class);
	
	
	private RideService rideService = new RideService();
	
	private void addRidesForOption(String option, List<Car> resCars, Ride r, HttpSession session) throws SQLException {
		HashMap<String, String> optionMap = (HashMap<String, String>) session.getAttribute(option);
		for(int i = 0; i < Integer.parseInt(optionMap.get("numOfCars")); i++) {
			r.setPrice(Math.round(Float.parseFloat(((String) session.getAttribute("dist")).substring(0, ((String) session.getAttribute("dist")).indexOf(" ")))
					* (CarClass.valueOf((String) optionMap.get("carClass")).getPricePerKm()) - (int)session.getAttribute("disc")));
			
			Car c =  rideService.addRide(r, String.valueOf(optionMap.get("carClass")));
			resCars.add(c);
			log.info("added new ride for: "+c);
		}
	}
	
	public String confirmRide(HttpServletRequest req, HttpServletResponse res) throws SQLException {
		HttpSession session = req.getSession();
		RideService rideService = new RideService();
		
		Ride r = (Ride)session.getAttribute("ride");
		
		List<Car> resCars = new ArrayList<Car>();
		
		switch(req.getParameter("opt")) {
		case "1":
			addRidesForOption("option1", resCars, r, session);
			break;
		case "2":
			addRidesForOption("option2", resCars, r, session);
			break;
		default:
			Car c =  rideService.addRide(r, String.valueOf(session.getAttribute("carClass")));
			resCars.add(c);
			log.info("added new ride for: "+c);
			break;
		}
			
		req.setAttribute("cars", resCars);
		
		session.removeAttribute("option1");
		session.removeAttribute("option2");
		session.removeAttribute("dist");
		session.removeAttribute("time");
		session.removeAttribute("ride");
		session.removeAttribute("carClass");
		
		return Path.PAGE_RIDE_CONFIRMED;
	}
}