package com.mgl.social.server.controller;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mgl.social.server.database.Message;
import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.constant.CMessage;
import com.mgl.social.server.database.dao.MessageDAO;
import com.mgl.social.server.database.dao.UpdateDAO;
import com.mgl.social.server.util.ManageDate;

public class ControllerMessageWeb extends BaseController {

	public void sendMessage(Long idUser, Long idReciever, String messageToSend,
			boolean isNew, Long idConversation) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			MessageDAO dao = new MessageDAO();
			Message message = new Message();
			message.setDate(ManageDate.formatDate(ManageDate
					.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS), ManageDate.YYYY_MM_DD_HH_MM_SS));
			message.setIdConversation(idConversation);
			message.setIdReciever(idReciever);
			message.setIdSendUser(idUser);
			message.setMessage(messageToSend);
			message.setActive(CMessage.ACTIVE);
			dao.getSession().save(message);
			if(message.getIdConversation()==null){
				message.setIdConversation(message.getId());
			}
			dao.getSession().update(message);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public ArrayList<Message> loadMyMessages(Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			MessageDAO dao = new MessageDAO();
			ArrayList<Message> objList =dao.loadMyMessages(idUser);
			for(Message m : objList){
				
				verifyPhotoInServer(m.getPhoto());
				
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

	public ArrayList<Message> loadConversationList(Long idConversation) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			MessageDAO dao = new MessageDAO();
			return dao.loadConversationList(idConversation);
			

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public void deleteMessage(Message message, Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			MessageDAO dao = new MessageDAO();
			dao.deleteMessage(message.getIdConversation(),idUser);
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
