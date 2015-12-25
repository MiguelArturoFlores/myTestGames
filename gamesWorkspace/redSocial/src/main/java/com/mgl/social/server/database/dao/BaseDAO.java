package com.mgl.social.server.database.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import com.mgl.social.server.database.Category;
import com.mgl.social.server.database.Country;
import com.mgl.social.server.database.DateSearch;
import com.mgl.social.server.database.Like;
import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.UpdateType;

public class BaseDAO {

	public Session getSession() {
		try {
			if (SessionHibernate.getSession() != null
					&& SessionHibernate.getSession().isOpen()) {
				return SessionHibernate.getSession();
			}
			return SessionHibernate.getSessionFactory().openSession();

		} catch (Exception e) {
			e.printStackTrace();
			return SessionHibernate.getSessionFactory().openSession();
		}
	}

	public String loadPhotoFromDisk(String path) {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(path));

			String photo = new String();

			while ((sCurrentLine = br.readLine()) != null) {
				photo = photo + sCurrentLine;
			}
			return photo;

		} catch (IOException e) {
			e.printStackTrace();
			return "";

		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public ArrayList<Category> loadCategoryList() {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT c ");
			hql.append(" FROM Category c ");
			
			qo = getSession().createQuery(hql.toString());
			
			return (ArrayList<Category>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public ArrayList<UpdateType> loadUpdateTypeList() {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT ut ");
			hql.append(" FROM UpdateType ut ");
			
			qo = getSession().createQuery(hql.toString());
			
			return (ArrayList<UpdateType>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public ArrayList<Country> loadCountryList() {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT c ");
			hql.append(" FROM Country c ");
			
			qo = getSession().createQuery(hql.toString());
			
			return (ArrayList<Country>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public ArrayList<DateSearch> loadDateList() {
		StringBuilder hql = null;
		Query qo = null;
		try {

			hql = new StringBuilder();
			hql.append(" SELECT c ");
			hql.append(" FROM DateSearch c ");
			
			qo = getSession().createQuery(hql.toString());
			
			return (ArrayList<DateSearch>) qo.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

}
