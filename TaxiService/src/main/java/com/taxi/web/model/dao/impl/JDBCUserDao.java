package com.taxi.web.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.taxi.web.model.dao.UserDao;
import com.taxi.web.model.entity.User;
import com.taxi.web.model.entity.UserInfo;
import com.taxi.web.model.entity.User.UserBuilder;

public class JDBCUserDao implements UserDao{
	private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    
	public boolean regNewUser(User us, UserInfo usInfo) throws SQLException {
		try (PreparedStatement preStatementUS = connection.prepareStatement(
						"INSERT INTO user (login, password, salt) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				PreparedStatement preStatementUSInfo = connection
						.prepareStatement("INSERT INTO user_info (user_id, first, last) VALUES(?, ?, ?)")) {
			
			connection.setAutoCommit(false);

			preStatementUS.setString(1, us.getLogin());
			preStatementUS.setString(2, us.getPassword());
			preStatementUS.setBytes(3, us.getSalt());
			preStatementUS.execute();
			ResultSet rs = preStatementUS.getGeneratedKeys();

			if (rs.next()) {
				preStatementUSInfo.setInt(1, rs.getInt(1));
			} else {
				connection.setAutoCommit(true);
				throw new SQLException();
			}

			preStatementUSInfo.setString(2, usInfo.getFirst());
			preStatementUSInfo.setString(3, usInfo.getLast());
			preStatementUSInfo.execute();

			connection.commit();
			rs.close();
			return true;
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		}finally {
			connection.setAutoCommit(true);
		}
	}
	
	
	public User findUserByLogin(String login) {
		try (PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM user WHERE login = ?")) {
			preStatement.setString(1, login);
			ResultSet rs = preStatement.executeQuery();
			User us = null;
			if (rs.next()) {
				us = new UserBuilder().setId(rs.getInt(1)).setLogin(rs.getString(2)).setPassword(rs.getString(3))
						.setRole(rs.getString(4)).setSalt(rs.getBytes(5)).build();
			}
			rs.close();
			return us;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	
	public String findFirstNameByUserId(int id) {
		try (PreparedStatement preStatement = connection
						.prepareStatement("SELECT first FROM user_info WHERE user_id = ?")) {
			preStatement.setInt(1, id);
			ResultSet rs = preStatement.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			rs.close();
			return null;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}
	
	
	public UserInfo findUserInfoByUserId(int id) {
		try (PreparedStatement preStatement = connection
						.prepareStatement("SELECT first, last, total_spent FROM user_info WHERE user_id = ?")) {
			UserInfo usInfo = new UserInfo();

			preStatement.setInt(1, id);
			ResultSet rs = preStatement.executeQuery();
			if (rs.next()) {
				usInfo.setFirst(rs.getString(1));
				usInfo.setLast(rs.getString(2));
				usInfo.setTotalSpent(Integer.parseInt(rs.getString(3)));
			}
			rs.close();
			return usInfo;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}
	
	public int findTotalSpentByUserId(int id) {
		try (PreparedStatement preStatement = connection
						.prepareStatement("SELECT total_spent FROM user_info WHERE user_id = ?")) {
			int res = 0;
			preStatement.setInt(1, id);
			ResultSet rs = preStatement.executeQuery();
			if (rs.next()) {
				res = rs.getInt(1);
			}
			rs.close();
			return res;
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
	}

	@Override
	public void close() {
		try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
}