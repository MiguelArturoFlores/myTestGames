package com.mgl.social.server.database.dao;

import java.util.ArrayList;

import org.hibernate.Query;

import com.mgl.social.server.database.HashTag;
import com.mgl.social.server.database.Message;
import com.mgl.social.server.database.constant.CMessage;

public class MessageDAO extends BaseDAO {

	public ArrayList<Message> loadMyMessages(Long idUser) {

		StringBuilder hql = null;
		Query qo = null;
		try {
			hql = new StringBuilder();
			hql.append("select m.id, m.message , m.idSendUser, m.idConversation, m.date,photo.photo, usr.name, usr.lastName from t_message m, t_user usr LEFT JOIN t_photo photo ON usr.idPhoto = photo.id  where m.idReciever = :idUser and m.idSendUser=usr.id and m.active = :ACTIVE group by m.idConversation order by m.date desc");

			qo = getSession().createSQLQuery(hql.toString());

			qo.setParameter("idUser", idUser);
			qo.setParameter("ACTIVE", CMessage.ACTIVE);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();

			ArrayList<Message> returnList = new ArrayList<Message>();

			for (Object[] obj : objList) {
				Message message = new Message();
				message.setId(Long.valueOf(String.valueOf(obj[0])));
				message.setMessage(String.valueOf(obj[1]));
				message.setIdSendUser(Long.valueOf(String.valueOf(obj[2])));
				try {
					message.setIdConversation(Long.valueOf(String
							.valueOf(obj[3])));
				} catch (Exception e) {
					message.setIdConversation(null);
				}

				message.setDateStr(String.valueOf(obj[4]));

				//String photoStr = loadPhotoFromDisk(String.valueOf(obj[5]));
				message.setPhoto(String.valueOf(obj[5]));
				message.setUserName(String.valueOf(obj[6]) + " "
						+ String.valueOf(obj[7]));

				returnList.add(message);
			}
			System.out.println("el tam de la lista es de mensajes es: "
					+ returnList.size());
			return returnList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public ArrayList<Message> loadConversationList(Long idConversation) {

		StringBuilder hql = null;
		Query qo = null;
		try {
			hql = new StringBuilder();
			hql.append("select m.message, m.date, usr.name, usr.lastName from t_message m, t_user usr  where m.idConversation =:idConversation and m.idSendUser=usr.id ");

			qo = getSession().createSQLQuery(hql.toString());

			qo.setParameter("idConversation", idConversation);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();

			ArrayList<Message> returnList = new ArrayList<Message>();

			for (Object[] obj : objList) {
				Message message = new Message();
				message.setMessage(String.valueOf(obj[0]));
				message.setDateStr(String.valueOf(obj[1]));
				message.setUserName(String.valueOf(obj[2]) + " "
						+ String.valueOf(obj[3]));

				returnList.add(message);
			}
			System.out.println("el tam de la lista es de mensajes es: "
					+ returnList.size());
			return returnList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public void deleteMessage(Long idConversation, Long idUser) {
		StringBuilder hql = null;
		Query qo = null;
		try {
			hql = new StringBuilder();
			hql.append("update t_message m set m.active = :INACTIVE where m.idConversation=:idConversation and m.idReciever = :idUser");
			qo = getSession().createSQLQuery(hql.toString());

			qo.setParameter("idConversation", idConversation);
			qo.setParameter("idUser", idUser);
			qo.setParameter("INACTIVE", CMessage.INACTIVE);

			qo.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hql = null;
			qo = null;
		}

	}

}
