package com.mgl.social.server.database.dao;

import org.hibernate.Query;

import com.mgl.social.server.database.Post;

public class PostDAO extends BaseDAO{

	public Post loadPostById(Long idType) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append("Select p from Post p where id=:idType");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idType", idType);

			qo.setMaxResults(1);
			
			return (Post) qo.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}
	
}
