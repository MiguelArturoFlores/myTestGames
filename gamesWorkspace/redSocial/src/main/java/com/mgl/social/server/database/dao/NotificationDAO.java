package com.mgl.social.server.database.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.mgl.social.server.database.Message;
import com.mgl.social.server.database.Notification;
import com.mgl.social.server.database.constant.CLogic;
import com.mgl.social.server.util.ManageDate;

public class NotificationDAO extends BaseDAO {

	public ArrayList<Notification> loadNotificationList(Long idUser,
			Long limit, Long multiplierTimeline) {

		StringBuilder hql = null;
		Query qo = null;
		try {

			Long limitMin = limit;
			limit = limit * multiplierTimeline;
			limitMin = limit - limitMin;
			// TODO Optimize this when i recive the top date
			limitMin = 0L;

			hql = new StringBuilder();
			hql.append(" select n.id as id, u.name as name , u.lastName as userName, n.idUserGenerator as idUserGenerator, n.date as date,n.idUpdate as idUpdate, n.type as type, n.idUser as idUser,p.photo as photo from t_notification n, t_user u left join t_photo p on (u.idPhoto=p.id) where n.idUser =:idUser ");
			hql.append(" and n.idUserGenerator = u.id ");
			hql.append(" order by n.date DESC LIMIT :MINLIM,:MAXLIM ");
			
			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("idUser", idUser);
			qo.setParameter("MAXLIM", limit);
			qo.setParameter("MINLIM", limitMin);
			
			
			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();

			ArrayList<Notification> returnList = new ArrayList<Notification>();

			for (Object[] obj : objList) {
				Notification notification = new Notification();
				notification.setId(Long.valueOf(String.valueOf(obj[0])));
				notification.setUserName(String.valueOf(obj[1])+" "+String.valueOf(obj[2]));
				notification.setIdUserGenerator(Long.valueOf(String
						.valueOf(obj[3])));
				String dateStr = String.valueOf(obj[4]);
				dateStr = dateStr.replace("-", "/");
				notification.setDate(ManageDate.formatDate(dateStr,
						ManageDate.YYYY_MM_DD_HH_MM_SS));
				notification.setIdUpdate(Long.valueOf(String.valueOf(obj[5])));
				notification.setType(Long.valueOf(String.valueOf(obj[6])));
				notification.setIdUser(Long.valueOf(String.valueOf(obj[7])));
				notification.setPhoto(String.valueOf(obj[8]));

				returnList.add(notification);
			}

			return returnList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

}
