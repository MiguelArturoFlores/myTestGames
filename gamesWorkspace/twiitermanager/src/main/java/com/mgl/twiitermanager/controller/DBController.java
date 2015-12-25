package com.mgl.twiitermanager.controller;

import java.util.ArrayList;
import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mgl.twiitermanager.database.UserDAO;
import com.mgl.twiitermanager.hibernate.SessionHibernate;
import com.mgl.twiitermanager.model.Follower;
import com.mgl.twiitermanager.model.User;
import com.mgl.twiitermanager.model.UserInfo;
import com.mgl.twiitermanager.model.UserMessaged;

public class DBController {

	public void insertUser(User user) {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			dao.getSession().save(user);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public com.mgl.twiitermanager.model.User loadUser(long id) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			User user = dao.loadUserById(id);

			return user;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public void insertFollower(Follower follower) {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			dao.getSession().save(follower);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public ArrayList<Follower> loadUserIfollow() {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			return dao.loadUserIfollow();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public ArrayList<Follower> loadIdUserIFollowWithinDaysInDB(int number) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, number * -1);
			return dao.loadUserIfollow(cal.getTime());

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public void updateFollowers(ArrayList<Long> updateUser) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			String str = new String();
			for (Long l : updateUser) {
				str = str + l + ",";
			}
			str = str + "0";
			dao.updateFollowers(str, 1);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public void updateFollowerBlock(Long id) {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			String str = new String(id+"");
			dao.updateFollowers(str, 2);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public ArrayList<Follower> loadUserFollow(long idUser, long myId) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			 return dao.loadUserFollowById(idUser,myId);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public ArrayList<UserMessaged> loadUserIMessageFollow(Long idUser,Long messageConstant) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			 return dao.loadUserImessage(idUser,messageConstant);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public ArrayList<UserMessaged> loadUserMessaged(Long idUser, Long messageConstant) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			 return dao.loadUserImessage(idUser,messageConstant);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public void insertUserMessaged(UserMessaged userMessaged) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			dao.getSession().save(userMessaged);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public com.mgl.twiitermanager.model.User loadUser(String accountName) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			User user = dao.loadUserByName(accountName);

			return user;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public ArrayList<UserInfo> loadYoutuberDashList() {
		
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			

			return dao.loadYoutuberDashList();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		
	}
	
}
