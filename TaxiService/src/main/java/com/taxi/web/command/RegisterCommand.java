package com.taxi.web.command;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
import com.taxi.web.model.service.RegisterService;


/**
 * Registers client and directs to index page
 *
 */
public class RegisterCommand extends Command {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(RegisterCommand.class);
	
	private static final String LOGIN_REGEX = "^[a-zA-Z0-9_].{1,15}$";
    
    private static final String NAME_REGEX = "^[a-zA-Zа-яА-ЯёЁІіЇїЄє].{1,31}$";
    
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9_!@#&()–[{}]:;',?/*~$^+=<>].{1,15}$";

    
    /**
     * Takes parameters from request and tries to add new client into db,
	 * in case of success directs to login page, else to register page again.
     * 
     * @return Login page or Register page
	 * @param req
	 * @param res
     */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res){
		log.info("RegisterCommand started");
		
		log.info("request parameter login -->" + req.getParameter("login"));
		log.info("request parameter first -->" + req.getParameter("first"));
		log.info("request parameter last -->" + req.getParameter("last"));
		log.info("request parameter password -->" + req.getParameter("password"));
		
		
		if(!Pattern.matches(LOGIN_REGEX, req.getParameter("login"))||
				!Pattern.matches(NAME_REGEX, req.getParameter("first"))||
				!Pattern.matches(NAME_REGEX, req.getParameter("last"))||
				!Pattern.matches(PASSWORD_REGEX, req.getParameter("password"))) {
			
			res.setHeader("message_info", "wrong_reg_form");
			return Path.PAGE_REGISTER;
		}
		

		HttpSession session = req.getSession();

		Optional<Cookie> maybeCookie = Arrays.stream(req.getCookies())
				.filter(e -> e.getName().equals("lang"))
				.findFirst();
		if (maybeCookie.isEmpty()) {
			res.addCookie(new Cookie("lang", "ru"));
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", "ru");
		}else {
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", maybeCookie.get().getValue());
		}

		log.info("LogoutCommand finished");
		return new RegisterService().registerOperation(req, res);
	}
}