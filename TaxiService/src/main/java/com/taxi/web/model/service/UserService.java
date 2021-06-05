package com.taxi.web.model.service;

import java.sql.SQLException;

import com.taxi.web.model.dao.DaoFactory;
import com.taxi.web.model.dao.UserDao;
import com.taxi.web.model.entity.User;
import com.taxi.web.model.entity.UserInfo;

public class UserService {
	
	DaoFactory daoFactory = DaoFactory.getInstance();
	
	public int getTotalSpent(int userId){
		try(UserDao dao = daoFactory.createUserDao()){
			return dao.findTotalSpentByUserId(userId);
		}
	}
	
	public User getUser(String login) {
		try(UserDao dao = daoFactory.createUserDao()){
			return dao.findUserByLogin(login);
		}
	}
	
	public UserInfo getUserInfo(int userId) {
		try(UserDao dao = daoFactory.createUserDao()){
			return dao.findUserInfoByUserId(userId);
		}
	}
	
	public String getFirstName(int userId) {
		try(UserDao dao = daoFactory.createUserDao()){
			return dao.findFirstNameByUserId(userId);
		}
	}
	
	public void addUser(User us, UserInfo usInfo)throws SQLException {
		try(UserDao dao = daoFactory.createUserDao()){
			try {
				dao.regNewUser(us, usInfo);
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
	
}