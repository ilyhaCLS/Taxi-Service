package com.taxi.web.listener;

import java.util.HashSet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.sjavac.Log;
import com.taxi.web.command.AdminPageCommand;

public class SessionListener implements HttpSessionListener {
	
	private static final Logger log = LoggerFactory.getLogger(SessionListener.class);
	
	@Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<Integer> loggedUsers = (HashSet<Integer>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        
        Integer userId = (Integer)httpSessionEvent.getSession().getAttribute("user_id");
        loggedUsers.remove(userId);
        httpSessionEvent.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        log.info("session destroyed for user id: "+ userId);
    }
}