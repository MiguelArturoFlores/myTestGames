package com.mgl.social.server.database.dao;

import org.hibernate.Query;

import com.mgl.social.server.database.User;

public class LoginDAO extends BaseDAO{

	public User getUserLogin(String loginName, String password) {
		StringBuilder hql = null;
		Query qo = null;
		try {
			hql = new StringBuilder();
			hql.append("Select u from User u where u.email =:loginName and u.password =:password");
			
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("loginName", loginName);
			qo.setParameter("password", password);	
			
			qo.setMaxResults(1);
			return (User) qo.uniqueResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			hql=null;
			qo=null;
		}
	}
	
	

}
