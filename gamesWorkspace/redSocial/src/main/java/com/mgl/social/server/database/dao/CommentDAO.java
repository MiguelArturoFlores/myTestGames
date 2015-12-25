package com.mgl.social.server.database.dao;

import org.hibernate.Query;

import com.mgl.social.server.database.Comment;

public class CommentDAO extends BaseDAO {
	
	public Comment loadById(Long id) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT lk ");
			hql.append(" FROM  Comment lk ");
			hql.append(" WHERE lk.id=:id ");

			qo = getSession().createQuery(hql.toString());
		
			qo.setParameter("id", id);

			qo.setMaxResults(1);
			
			return (Comment) qo.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}


}
