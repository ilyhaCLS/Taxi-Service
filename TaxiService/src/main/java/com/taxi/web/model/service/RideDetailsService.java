package com.taxi.web.model.service;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import com.taxi.web.Path;
import com.taxi.web.model.entity.CarClass;
import com.taxi.web.model.entity.Ride;
import com.taxi.web.model.entity.Ride.RideBuilder;

public class RideDetailsService {
	public String arrangeRide(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String forward = Path.PAGE_ERROR;

		HttpSession session = req.getSession();

		GeoApiContext sc = new GeoApiContext.Builder().apiKey("securekey").build();
		DistanceMatrix matrix = null;
		try {
			matrix = DistanceMatrixApi.newRequest(sc).origins(req.getParameter("posFrom").replace(' ', '+'))
					.destinations(req.getParameter("posTo").replace(' ', '+')).mode(TravelMode.DRIVING).await();
		} catch (ApiException | InterruptedException | IOException e1) {
			e1.printStackTrace();
		} finally {
			sc.close();
		}

		String dist = matrix.rows[0].elements[0].distance.toString();

		int discount = (new UserService().getTotalSpent((int) session.getAttribute("user_id")) / 100);

		if (Integer.parseInt((String) req.getParameter("numOfPass")) <= CarClass.valueOf(req.getParameter("carClass"))
				.getNumOfSeats()) {

			Ride r = new RideBuilder().setPosFrom(req.getParameter("posFrom")).setPosTo(req.getParameter("posTo"))
					.setPrice(Math.round(Float.parseFloat(dist.substring(0, dist.indexOf(" ")))
							* (CarClass.valueOf(req.getParameter("carClass")).getPricePerKm())) - discount)
					.setUserId((int) req.getSession().getAttribute("user_id")).build();

			req.setAttribute("distance", dist);
			req.setAttribute("price", r.getPrice());
			req.setAttribute("time", matrix.rows[0].elements[0].duration);
			req.setAttribute("disc", discount);

			session.setAttribute("carClass", req.getParameter("carClass"));
			session.setAttribute("ride", r);

			forward = Path.PAGE_RIDE_DETAILS;

		} else {
			double numOfPass = Integer.parseInt((String) req.getParameter("numOfPass"));
			double opt1Seat = CarClass.valueOf(req.getParameter("carClass")).getNumOfSeats();
			int opt1numOfCars = (int) Math.ceil(numOfPass / opt1Seat);
			double newDist = Double.parseDouble(dist.substring(0, dist.indexOf(" ")));

			CarClass newClass = req.getParameter("carClass").equals("ECONOM") ? CarClass.valueOf("ECONOMXL")
					: CarClass.valueOf("VAN");

			int seat2 = newClass.getNumOfSeats();
			int opt2numOfCars = (int) Math.ceil(numOfPass / seat2);
			int opt1price = (int) (((CarClass.valueOf(req.getParameter("carClass")).getPricePerKm() * newDist)
					- discount) * opt1numOfCars);
			int opt2price = (int) (((newClass.getPricePerKm() * newDist) - discount) * opt2numOfCars);

			HashMap<String, String> opt1 = new HashMap<>();
			opt1.put("carClass", req.getParameter("carClass"));
			opt1.put("price", String.valueOf(opt1price));
			opt1.put("numOfCars", String.valueOf(opt1numOfCars));

			HashMap<String, String> opt2 = new HashMap<>();
			opt2.put("carClass", newClass.toString());
			opt2.put("price", String.valueOf(opt2price));
			opt2.put("numOfCars", String.valueOf(opt2numOfCars));

			session.setAttribute("option1", opt1);
			session.setAttribute("option2", opt2);
			session.setAttribute("dist", dist);
			session.setAttribute("time", matrix.rows[0].elements[0].duration);
			session.setAttribute("disc", discount);

			Ride r = new RideBuilder().setPosFrom(req.getParameter("posFrom")).setPosTo(req.getParameter("posTo"))
					.setUserId((int) req.getSession().getAttribute("user_id")).build();

			session.setAttribute("ride", r);

			forward = Path.PAGE_RIDE_DETAILS;
		}

		return forward;
	}
}
