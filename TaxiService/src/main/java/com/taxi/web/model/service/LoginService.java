package com.taxi.web.model.service;

import java.security.spec.InvalidKeySpecException;
import java.time.LocalTime;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
import com.taxi.web.model.entity.Role;
import com.taxi.web.model.entity.User;
import com.taxi.web.security.PasswordEncoder;

public class LoginService {
	private static final Logger log = LoggerFactory.getLogger(LoginService.class);
	
	private PasswordEncoder encoder = PasswordEncoder.getInstance();
	
	public String loginOperation(HttpServletRequest req, HttpServletResponse res) {
		String forward = Path.PAGE_ERROR;
		UserService userService = new UserService();
		HttpSession session = req.getSession();
		
		User us = userService.getUser(req.getParameter("login"));
		if(us == null) {
			log.info("user " + req.getParameter("login") + " not found!");
			res.setHeader("message_info", "wrong_cred");
			forward = Path.PAGE_LOGIN;
		}else {
			try {
				if(us.getPassword().equals(encoder.encode(req.getParameter("password"), us.getSalt()))) {
					HashSet<Integer> loggedUsers = (HashSet<Integer>)session.getServletContext().getAttribute("loggedUsers");
					
					if(loggedUsers.contains(us.getId())) {
						log.info(req.getParameter("login")+" already logged");
						res.setHeader("message_info", "already_logged");
						return Path.PAGE_LOGIN;
					}
					
					loggedUsers.add(us.getId());
					
					int hour = LocalTime.now().getHour();
					String greeting = null;
					if(hour < 6) {
						greeting = "header.night";
					}else if(hour > 5 && hour < 12) {
						greeting = "header.morning";
					}else if(hour > 11 & hour < 18) {
						greeting = "header.day";
					}else {
						greeting = "header.evening";
					}
					
					session.setAttribute("greeting", greeting);
					session.setAttribute("name", userService.getFirstName(us.getId()));
					session.setAttribute("role", Role.valueOf(us.getRole()));
					session.setAttribute("user_id", us.getId());
					
					log.info("new user logged in :" +us.getId());
					
					if(us.getRole().equals("ADMIN")) {
						log.info("new admin logged in :" +us.getId());
						forward = "redirect:"+"/controller?command=adminPage";
						return forward;
					}
					forward = "redirect:"+Path.PAGE_INDEX;
				}else {
					res.setHeader("message_info", "wrong_cred");
					forward = Path.PAGE_LOGIN;
				}
			}catch(InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
		return forward;
	}
}