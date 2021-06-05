package com.taxi.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxi.web.Path;
import com.taxi.web.model.entity.Role;

public class AccessFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(AccessFilter.class);
	

	private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private static List<String> commons = new ArrayList<String>();
	private static List<String> outOfControl = new ArrayList<String>();

	public void init(FilterConfig fConfig) throws ServletException {
		// roles
		accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("ADMIN")));
		accessMap.put(Role.CLIENT, asList(fConfig.getInitParameter("CLIENT")));

		// commons
		commons = asList(fConfig.getInitParameter("common"));

		// out of control
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		
		log.info("AccessFilter initialized!");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		if (accessAllowed(req)) {
			log.info("Access allowed");
			chain.doFilter(req, res);
		} else {
			log.info("Access denied");
			String errorMessage = "no_access";

			((HttpServletResponse)res).setHeader("message_info", errorMessage);
			req.getRequestDispatcher(Path.PAGE_ERROR).forward(req, res);
		}
	}

	private boolean accessAllowed(ServletRequest req) {
		log.info("request parameter command -->" + req.getParameter("command"));
				
		HttpSession session = ((HttpServletRequest)req).getSession(false);
		
		String commandName = req.getParameter("command");
		if (commandName == null || commandName.isEmpty())
			return false;
		
		if(session == null) return false;
		Role userRole = (Role)session.getAttribute("role");
		
		if(userRole != null) {
			return accessMap.get(userRole).contains(commandName) ||commons.contains(commandName);
		}else if(outOfControl.contains(commandName)) {
			return true;
		}
		
		return false;	
	}

	private List<String> asList(String str) {
		List<String> res = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(str);
		while (token.hasMoreElements())
			res.add(token.nextToken());
		return res;
	}
}