package com.taxi.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogRegPagesAccess implements Filter {
	private static final Logger log = LoggerFactory.getLogger(LogRegPagesAccess.class);
	

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest)req).getSession();
		
		if(session.getAttribute("role") == null) {
			log.info("role is absent, access allowed");
			chain.doFilter(req, res);
		}else {
			log.info("role is present, access denied");
			((HttpServletResponse)res).sendRedirect("/");
		}
	}
}