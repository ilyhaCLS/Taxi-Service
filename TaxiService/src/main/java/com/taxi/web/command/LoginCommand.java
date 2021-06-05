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
import com.taxi.web.model.service.LoginService;

/**
 * Logs in client and directs to index page
 *
 */
public class LoginCommand extends Command {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(LoginCommand.class);
	
	private static final String LOGIN_REGEX = "^[a-zA-Z0-9_].{1,15}$";
	private static final String PASSWORD_REGEX = "^[a-zA-Z0-9_!@#&()–[{}]:;',?/*~$^+=<>].{1,15}$";  
	
	
	/**
	 * Takes parameters from request and looks for same credentials in db.
	 * In case of success adds user_id to ServletContext
	 * @return Index page if success, else login page
	 * @param req
	 * @param res
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res){
		log.info("LoginCommand started");
		log.info("request parameter login -->" + req.getParameter("login"));
		log.info("request parameter password -->" + req.getParameter("password"));
		if(!Pattern.matches(LOGIN_REGEX, req.getParameter("login"))||
			!Pattern.matches(PASSWORD_REGEX, req.getParameter("password"))) {
			
			res.setHeader("message_info", "wrong_login_form");
			return Path.PAGE_LOGIN;
		}
			
		HttpSession session = req.getSession();
		
		Optional<Cookie> maybeCookie = Arrays.stream(req.getCookies()).filter(e->e.getName().equals("lang")).findFirst();
		if(maybeCookie.isEmpty()) {
			res.addCookie(new Cookie("lang", "ru"));
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", "ru");
		}else {
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", maybeCookie.get().getValue());
		}
		
		log.info("LoginCommand finished");
		return new LoginService().loginOperation(req, res);
	}
}