package com.mgl.social.server.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.constant.CTime;
import com.mgl.social.server.database.constant.CType;
import com.mgl.social.server.database.constant.CUpdateType;
import com.mgl.social.server.database.dao.UpdateDAO;
import com.mgl.social.server.message.MsgRequestRanking;
import com.mgl.social.server.message.send.SendRankingPersone;
import com.mgl.social.server.message.send.SendRankingUpdate;
import com.mgl.social.server.message.send.obj.ObjRankingPerson;
import com.mgl.social.server.message.send.obj.ObjUpdate;
import com.mgl.social.server.message.send.obj.ObjViewUserGeneral;
import com.mgl.social.server.util.ManageDate;

public class ControllerRanking extends BaseController {

	private javax.websocket.Session session;

	public ControllerRanking(javax.websocket.Session session) {
		this.session = session;
	}

	public ControllerRanking() {

	}

	public String getRankingRequest(String messageComplete, Long idUser,
			Long myId, Long limit, Long multiplierTimeline) {
		try {
			System.out.println("message complete " + messageComplete);
			MsgRequestRanking msg = new ObjectMapper().readValue(
					messageComplete, MsgRequestRanking.class);
			// System.out.println("idcate " + msg.getIdCategory());
			// System.out.println("idcon " + msg.getIdCountry());
			// System.out.println("idtim " + msg.getIdTime());
			// System.out.println("idtip " + msg.getIdType());
			if (msg.getIdType().equals(CType.PERSONS)) {

				ArrayList<ObjRankingPerson> personList = loadPersonRanking(msg,
						idUser, limit, multiplierTimeline);
				SendRankingPersone msgAux = new SendRankingPersone(personList);
				JSONObject json = new JSONObject(msgAux);
				return json.toString();

			} else if (msg.getIdType().equals(CType.UPDATES)) {
				ArrayList<ObjUpdate> updateList = loadUpdatesRanking(msg,
						idUser, myId, limit, multiplierTimeline);
				SendRankingUpdate msgAux = new SendRankingUpdate(updateList);
				JSONObject json = new JSONObject(msgAux);
				return json.toString();
			}
			return "nothing found";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private ArrayList<ObjRankingPerson> loadPersonRanking(
			MsgRequestRanking msg, Long idUser, Long limit,
			Long multiplierTimeline) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();
			Date dateStart = loadDateStart(msg.getIdTime());
			Date dateEnd = loadDateEnd(msg.getIdTime());

			return dao.loadPersonsRanking(msg.getIdCountry(),
					msg.getIdCategory(), dateStart, dateEnd, idUser,
					msg.getIdUpdateType(), limit, multiplierTimeline);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	private ArrayList<ObjUpdate> loadUpdatesRanking(MsgRequestRanking msg,
			Long idUser, Long myId, Long limit, Long multiplierTimeline)
			throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();
		System.out.println("load updates ranking");
		try {
			UpdateDAO dao = new UpdateDAO();
			Date dateStart = loadDateStart(msg.getIdTime());
			Date dateEnd = loadDateEnd(msg.getIdTime());

			return dao.loadUpdatesRanking(msg.getIdCountry(),
					msg.getIdCategory(), dateStart, dateEnd, idUser,
					msg.getIdUpdateType(), limit, myId, multiplierTimeline);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	private Date loadDateEnd(Long idTime) throws Exception {
		try {

			String todayStr = ManageDate
					.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS);
			Date today = ManageDate.formatDate(todayStr,
					ManageDate.YYYY_MM_DD_HH_MM_SS);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ManageDate.getCurrentTimestamp());

			calendar.setTime(today);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			today = ManageDate.formatDate(ManageDate.formatDate(
					calendar.getTime(), ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS);

			if (idTime == null) {
				return null;
			}

			if (idTime.equals(CTime.TODAY)) {

				return today;
			}
			if (idTime.equals(CTime.YESTERDAY)) {
				calendar.setTime(today);
				calendar.add(Calendar.DAY_OF_YEAR, -1);
				return ManageDate.formatDate(ManageDate.formatDate(
						calendar.getTime(), ManageDate.YYYY_MM_DD_HH_MM_SS),
						ManageDate.YYYY_MM_DD_HH_MM_SS);

			}
			if (idTime.equals(CTime.LAST_EIGHT_DAYS)) {

				return today;
			}
			if (idTime.equals(CTime.THIS_MONTH)) {
				// 2014/07/02
				return today;
			}
			if (idTime.equals(CTime.LAST_MONTH)) {
				// 2014/07/02
				calendar.add(Calendar.MONTH, -1);
				String beginMonth = ManageDate.formatDate(calendar.getTime(),
						ManageDate.YYYY_MM_DD_HH_MM_SS);
				beginMonth = todayStr.substring(0, 8);
				beginMonth = beginMonth + "01";
				return ManageDate.formatDate(beginMonth, ManageDate.YYYY_MM_DD);
			}
			if (idTime.equals(CTime.THIS_YEAR)) {
				// 2014/07/02
				return today;
			}
			if (idTime.equals(CTime.LAST_YEAR)) {

				String beginMonth = todayStr.substring(0, 5);
				beginMonth = beginMonth + "01/01";
				calendar.setTime(ManageDate.formatDate(beginMonth,
						ManageDate.YYYY_MM_DD));
				// calendar.add(Calendar.YEAR, -1);

				return calendar.getTime();
			}
			return today;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private Date loadDateStart(Long idTime) throws Exception {
		try {

			String todayStr = ManageDate
					.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS);
			Date today = ManageDate.formatDate(todayStr,
					ManageDate.YYYY_MM_DD_HH_MM_SS);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ManageDate.getCurrentTimestamp());
			System.out.println("todayStr " + todayStr);
			System.out.println("today: " + calendar.getTime());
			if (idTime == null) {
				return null;
			}
			if (idTime.equals(CTime.TODAY)) {
				return today;
			}
			if (idTime.equals(CTime.YESTERDAY)) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
				return ManageDate.formatDate(ManageDate.formatDate(
						calendar.getTime(), ManageDate.YYYY_MM_DD),
						ManageDate.YYYY_MM_DD);
			}
			if (idTime.equals(CTime.LAST_EIGHT_DAYS)) {
				calendar.add(Calendar.DAY_OF_YEAR, -7);
				return ManageDate.formatDate(ManageDate.formatDate(
						calendar.getTime(), ManageDate.YYYY_MM_DD),
						ManageDate.YYYY_MM_DD);
			}
			if (idTime.equals(CTime.THIS_MONTH)) {
				// 2014/07/02
				String beginMonth = todayStr.substring(0, 8);
				beginMonth = beginMonth + "01";
				return ManageDate.formatDate(beginMonth, ManageDate.YYYY_MM_DD);
			}
			if (idTime.equals(CTime.LAST_MONTH)) {
				// 2014/07/02
				calendar.add(Calendar.MONTH, -1);
				System.out.println("time " + calendar.getTime());
				String beginMonth = ManageDate.formatDate(calendar.getTime(),
						ManageDate.YYYY_MM_DD);
				System.out.println("begin month " + beginMonth);
				beginMonth = beginMonth.substring(0, 8);
				beginMonth = beginMonth + "01";
				return ManageDate.formatDate(beginMonth, ManageDate.YYYY_MM_DD);
			}
			if (idTime.equals(CTime.THIS_YEAR)) {
				// 2014/07/02
				String beginMonth = todayStr.substring(0, 5);
				beginMonth = beginMonth + "01/01";
				return ManageDate.formatDate(beginMonth, ManageDate.YYYY_MM_DD);
			}
			if (idTime.equals(CTime.LAST_YEAR)) {
				String beginMonth = todayStr.substring(0, 5);
				beginMonth = beginMonth + "01/01";
				calendar.setTime(ManageDate.formatDate(beginMonth,
						ManageDate.YYYY_MM_DD));
				calendar.add(Calendar.YEAR, -1);

				return calendar.getTime();

			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ArrayList<ObjUpdate> loadUpdateListWeb(Long idCategory,
			Long idCountry, Long idDate, Long idUser, Long idUpdateType,
			Long limit, Long multiplier) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();
		System.out.println("load updates ranking");
		try {
			UpdateDAO dao = new UpdateDAO();
			Date dateStart = loadDateStart(idDate);
			Date dateEnd = loadDateEnd(idDate);

			ArrayList<ObjUpdate> objList = dao.loadUpdatesRanking(idCountry,
					idCategory, dateStart, dateEnd, idUser, idUpdateType,
					limit, idUser, multiplier);
			
			for (ObjUpdate obj : objList) {
				if (obj.getType().equals(CUpdateType.PHOTO_S)) {
					verifyPhotoInServer(obj.getContent());
				}
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

	public ArrayList<ObjRankingPerson> loadPersonListWeb(Long idCategory,
			Long idCountry, Long idDate, Long idUser, Long idUpdateType,
			Long limit, Long multiplier) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();
		System.out.println("load updates ranking");
		try {
			UpdateDAO dao = new UpdateDAO();
			Date dateStart = loadDateStart(idDate);
			Date dateEnd = loadDateEnd(idDate);

			ArrayList<ObjRankingPerson> objList = dao.loadPersonsRanking(idCountry, idCategory, dateStart,
					dateEnd, idUser, idUpdateType, limit, multiplier); 
			
			for(ObjRankingPerson obj : objList){
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

}
