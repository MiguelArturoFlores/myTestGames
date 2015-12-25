package com.mgl.social.server.database.dao;

import org.hibernate.Query;


import com.mgl.social.server.database.Like;
import com.mgl.social.server.database.Update;


public class LikeDAO extends BaseDAO {

	public Like loadUserLikeByIdUpdate(Long idUser, Long idUpdate) {

		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT  lk.id as id ");
			hql.append(" FROM Like lk, User usr ");
			hql.append(" WHERE lk.idUpdate =:idUpdate AND lk.idUser =:idUser AND usr.id =lk.idUser ");

			qo = getSession().createQuery(hql.toString()).setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(Like.class));
			qo.setParameter("idUpdate", idUpdate);
			qo.setParameter("idUser", idUser);

			qo.setMaxResults(1);
			return (Like) qo.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public Like loadById(Long id) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT lk ");
			hql.append(" FROM  Like lk ");
			hql.append(" WHERE lk.id=:id ");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("id", id);

			qo.setMaxResults(1);

			return (Like) qo.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

}
