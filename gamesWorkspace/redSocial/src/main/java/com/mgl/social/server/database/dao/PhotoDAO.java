package com.mgl.social.server.database.dao;

import org.hibernate.Query;

import com.mgl.social.server.database.Photo;

public class PhotoDAO extends BaseDAO {

	public Photo loadPhotoById(Long idType) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append("Select p from Photo p where id=:idType");
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idType", idType);

			qo.setMaxResults(1);
			
			return (Photo) qo.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

}
