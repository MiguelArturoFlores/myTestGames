package com.mgl.twiitermanager.hibernate;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;


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


}
