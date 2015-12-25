package com.mgl.social.server.controller;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mgl.social.core.controller.MglController;
import com.mgl.social.server.database.Message;
import com.mgl.social.server.database.Notification;
import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.constant.CMessage;
import com.mgl.social.server.database.dao.MessageDAO;
import com.mgl.social.server.database.dao.NotificationDAO;
import com.mgl.social.server.util.ManageDate;

public class ControllerNotification extends BaseController{

	public ArrayList<Notification> loadNotificationList(Long idUser, Long limit, Long multiplier) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			NotificationDAO dao = new NotificationDAO();
			ArrayList<Notification> objList = dao.loadNotificationList(idUser,limit, multiplier);
			for(Notification obj :objList){
				
				verifyPhotoInServer(obj.getPhoto());
			}
			return objList;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
		
	}

	public void updateNotificationList(ArrayList<Notification> notificationList) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			NotificationDAO dao = new NotificationDAO();
			
			for(Notification n : notificationList){
				
				dao.getSession().update(n);
				
			}
			
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
