package com.mgl.social.server.controller;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.dao.SearchDAO;
import com.mgl.social.server.message.MsgIdUser;
import com.mgl.social.server.message.MsgSearchPersone;
import com.mgl.social.server.message.MsgText;
import com.mgl.social.server.message.send.SendSearchView;
import com.mgl.social.server.message.send.obj.ObjSearch;

public class ControllerSearch extends BaseController {

	private javax.websocket.Session session;

	public ControllerSearch(javax.websocket.Session session) { //
		this.session = session;
	}

	public ControllerSearch(){
		
	}
	public String search(String messageComplete, Long id,Long limit,Long multiplier) {
		try {

			MsgText msg = new ObjectMapper().readValue(messageComplete,
					MsgText.class);
			ArrayList<ObjSearch> sendList = loadGeneralSearch(msg, id,limit,multiplier);
			SendSearchView send = new SendSearchView(sendList);
			JSONObject json = new JSONObject(send);
			System.out.println("Return Search " + json.toString());
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private ArrayList<ObjSearch> loadGeneralSearch(MsgText msg, Long idUser,Long limit, Long multiplier) {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {

			SearchDAO dao = new SearchDAO();
			return dao.loadGeneralSearch(msg.getText(), idUser,limit,multiplier);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public String follow(String messageComplete, Long myId) {
		try {

			MsgIdUser user = new ObjectMapper().readValue(messageComplete,
					MsgIdUser.class);

			followUser(myId, Long.valueOf(user.getIdUser()));
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	public String unfollow(String messageComplete, Long myId) {

		try {

			MsgIdUser user = new ObjectMapper().readValue(messageComplete,
					MsgIdUser.class);

			unfollowUser(myId, Long.valueOf(user.getIdUser()));
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String searchPersone(String messageComplete, Long id) {
		try {

			MsgSearchPersone msg = new ObjectMapper().readValue(messageComplete,
					MsgSearchPersone.class);
			ArrayList<ObjSearch> sendList = loadPersoneSearch(msg, id);
			SendSearchView send = new SendSearchView(sendList);
			JSONObject json = new JSONObject(send);
			System.out.println("Return Search " + json.toString());
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private ArrayList<ObjSearch> loadPersoneSearch(MsgSearchPersone msg, Long idUser) {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {

			SearchDAO dao = new SearchDAO();
			return dao.loadPersoneSerach(msg.getName(),msg.getLastName(),msg.getMail(), idUser);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public String searchUpdate(String messageComplete, Long id) {
		try {

			MsgText msg = new ObjectMapper().readValue(messageComplete,
					MsgText.class);
			ArrayList<ObjSearch> sendList = loadUpdateSearch(msg, id);
			SendSearchView send = new SendSearchView(sendList);
			JSONObject json = new JSONObject(send);
			System.out.println("Return Search " + json.toString());
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private ArrayList<ObjSearch> loadUpdateSearch(MsgText msg, Long idUser) {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {

			SearchDAO dao = new SearchDAO();
			return dao.loadUpdateSerach(msg.getText(), idUser);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public ArrayList<ObjSearch> loadPersonSearchWeb(String text, Long idUser, Long limit, Long multiplier){
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {

			SearchDAO dao = new SearchDAO();
			ArrayList<ObjSearch> objList =dao.loadGeneralSearch(text, idUser,limit,multiplier);
			for(ObjSearch obj : objList){
				
				verifyPhotoInServer(obj.getPhoto());
			}
			return objList;

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}		
	}
	
}
