package com.taxi.web.model.service;

import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
import com.taxi.web.model.entity.User;
import com.taxi.web.model.entity.UserInfo;
import com.taxi.web.model.entity.User.UserBuilder;
import com.taxi.web.security.PasswordEncoder;

public class RegisterService {
	private static final Logger log = LoggerFactory.getLogger(RegisterService.class);
	
	
	private PasswordEncoder encoder = PasswordEncoder.getInstance();
	
	public String registerOperation(HttpServletRequest req, HttpServletResponse res) {
		String forward = Path.PAGE_ERROR;
		
		byte[] salt = new byte[32];
		new SecureRandom().nextBytes(salt);
		User us = null;
		try {
			us = new UserBuilder().setLogin(req.getParameter("login"))
					.setPassword(encoder.encode(req.getParameter("password"), salt)).setSalt(salt).build();

		} catch (InvalidKeySpecException e1) {
			e1.printStackTrace();
		}

		UserInfo usInfo = new UserInfo();
		usInfo.setFirst(req.getParameter("first"));
		usInfo.setLast(req.getParameter("last"));

		try {
			new UserService().addUser(us, usInfo);
			res.setHeader("message_info", "successfully_registered");
			log.info("New user registered : "+us.getLogin());
			forward = Path.PAGE_LOGIN;
		} catch (SQLException e1) {
			res.setHeader("message_info", "already_registered");
			log.info("attempted to register, but login " +us.getLogin()+ " is already present");
			forward = Path.PAGE_REGISTER;
		}

		return forward;
	}
}