package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
/**
 * Changes language
 *
 */
public class LangCommand extends Command {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(LangCommand.class);
	
	
	/**
	 * Changes "lang" cookie thus when next page render will occur
	 * this cookie setting determines page language
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		log.info("LangCommand started");

		String newLang = (String) req.getParameter("newLang");
		HttpSession session = req.getSession();

		switch (newLang) {
		case "ru":
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", newLang);
			res.addCookie(new Cookie("lang", "ru"));
			log.info("New lang set as: " + newLang);
			break;
		case "en":
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", newLang);
			res.addCookie(new Cookie("lang", "en"));
			log.info("New lang set as: " + newLang);
			break;
		default:
			break;
		}
		log.info("LangCommand finished");
		return "redirect:"+Path.PAGE_INDEX;
	}
}