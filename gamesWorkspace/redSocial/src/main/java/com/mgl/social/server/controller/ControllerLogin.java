package com.mgl.social.server.controller;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.User;
import com.mgl.social.server.database.dao.LoginDAO;
import com.mgl.social.server.database.dao.UserDAO;
import com.mgl.social.server.message.MsgLogin;
import com.mgl.social.server.message.send.SendLoginSusscesfull;
import com.mgl.social.server.message.send.SendLoginUnsusscesfull;
import com.mgl.social.server.util.ValidateUtil;

public class ControllerLogin extends BaseController {

	private User user = null;
	private javax.websocket.Session session;

	public ControllerLogin(javax.websocket.Session session) { //
		this.session = session;
	}

	public ControllerLogin(){}
	
	public String login(String message) {
		try {

			// decode message
			MsgLogin login = new ObjectMapper().readValue(message,
					MsgLogin.class);

			if (isValidMessage(login) && correctLogin(login.getLoginName(),login.getPassword())) {
				// return valid login
				JSONObject json = new JSONObject(new SendLoginSusscesfull());
				System.out.println("JSON MESSAGE TO SEND:" + json.toString());
				return json.toString();
			} else {
				JSONObject json = new JSONObject(new SendLoginUnsusscesfull());
				System.out.println("JSON MESSAGE TO SEND:" + json.toString());
				return json.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private boolean isValidMessage(MsgLogin login) {
		try {
			if (ValidateUtil.validateNotEmptyString(login.getLoginName())
					&& ValidateUtil.validateNotEmptyString(login.getPassword())) {
				System.out.println("Valid fields");
				return true;
			}
			System.out.println("Not valid Login");
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean correctLogin(String loginName, String password) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			LoginDAO loginDAO = new LoginDAO();

			user = loginDAO.getUserLogin(loginName,
					password);
			if (user == null) {
				return false;
			}
			return true;

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Long> loadFollowerList(Long idUser) throws Exception {
			Session session = SessionHibernate.getSession();
			Transaction tx = session.beginTransaction();

			try {
				UserDAO dao = new UserDAO();
				return dao.loadFollowerList(idUser);
			} catch (Exception e) {
				tx.rollback();
				e.printStackTrace();
				throw e;
			} finally {
				session.close();
			}
		}

	public ArrayList<Long> loadFollowingList(Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			return dao.loadFollowingList(idUser);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public User loginWeb(String email, String password) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			return dao.loadUserInfoByEmailPass(email,password);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		
	}

	public String loadPasswordByEmail(String mailRecovery) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			return dao.loadPasswordByEmail(mailRecovery);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		
	}

	public void updateUserLanguage(Long idLanguage, Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			
			UserDAO dao = new UserDAO();
			dao.updateUserLanguage(idLanguage,idUser);
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		
	}


}
