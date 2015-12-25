package com.mgl.twiitermanager.database;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Query;

import com.mgl.twiitermanager.hibernate.BaseDAO;
import com.mgl.twiitermanager.model.Follower;
import com.mgl.twiitermanager.model.User;
import com.mgl.twiitermanager.model.UserInfo;
import com.mgl.twiitermanager.model.UserMessaged;
import com.mgl.twiitermanager.util.ManageDate;


public class UserDAO extends BaseDAO{

	public User loadUserById(long id) {
		
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT lk ");
			hql.append(" FROM  User lk ");
			hql.append(" WHERE lk.idUser=:id ");

			qo = getSession().createQuery(hql.toString());
		
			qo.setParameter("id", id);

			qo.setMaxResults(1);
			
			return (User) qo.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
		
	}

	public ArrayList<Follower> loadUserIfollow() {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			
			hql.append(" FROM  Follower ");

			qo = getSession().createQuery(hql.toString());
		

			
			return (ArrayList<Follower>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
		
	}

	public ArrayList<Follower> loadUserIfollow(Date time) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			
			hql.append(" FROM  Follower f where f.followDate <= :time and (f.followMe = 0  or f.followMe is null ) ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("time", ManageDate.formatDate(time, ManageDate.YYYY_MM_DD));
			
			return (ArrayList<Follower>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public void updateFollowers(String str, int valToFollow) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			
			hql.append(" UPDATE Follower f set f.followMe = "+valToFollow+" where f.idFollower in ( "+str+") ");

			qo = getSession().createQuery(hql.toString());
			qo.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hql = null;
			qo = null;
		}
		
	}

	public ArrayList<Follower> loadUserFollowById(long id, long myId) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			
			hql.append(" FROM Follower f where f.idFollower =:id and f.idMyUser =:myId");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("id", id);
			qo.setParameter("myId",myId);
			
			return (ArrayList<Follower>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public ArrayList<UserMessaged> loadUserImessage(Long idUser, Long messageConstant) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			
			hql.append(" FROM UserMessaged um where um.idUser =:id and um.messageConstant = :messageConstant ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("id",idUser);
			qo.setParameter("messageConstant", messageConstant);
			
			return (ArrayList<UserMessaged>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public User loadUserByName(String accountName) {

		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT lk ");
			hql.append(" FROM  User lk ");
			hql.append(" WHERE lk.name=:accname ");

			qo = getSession().createQuery(hql.toString());
		
			qo.setParameter("accname", accountName);

			qo.setMaxResults(1);
			
			return (User) qo.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
		
		
	}

	public ArrayList<UserInfo> loadYoutuberDashList() {

		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			
			//hql.append(" select u.email, u.twitterAccessToken, u.twitterSecretToken, u.twitterUserName, u.contFacebook, u.contWhatsapp, u.contTwitter from userinfo u where u.twitterAccessToken!='' GROUP BY twitterAccessToken ");
			hql.append(" from UserInfo u where u.twitterAccessToken!='' GROUP BY twitterAccessToken ");

			qo = getSession().createQuery(hql.toString());
			
			return (ArrayList<UserInfo>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	

}
