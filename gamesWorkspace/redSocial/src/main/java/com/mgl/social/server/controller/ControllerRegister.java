package com.mgl.social.server.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.mgl.social.server.database.Photo;
import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.Update;
import com.mgl.social.server.database.User;
import com.mgl.social.server.database.constant.CCategory;
import com.mgl.social.server.database.constant.CUpdateType;
import com.mgl.social.server.message.MsgRegister;
import com.mgl.social.server.message.send.SendRegisterSussceful;
import com.mgl.social.server.message.send.SendRegisterUnsusscesfull;
import com.mgl.social.server.util.ManageDate;
import com.mgl.social.server.util.ValidateUtil;

public class ControllerRegister extends BaseController {

	private User user;
	private javax.websocket.Session session;
	private String photo;

	public ControllerRegister(javax.websocket.Session session) {
		this.session = session;
	}
	public ControllerRegister(){
		
	}
	
	public String register(String message) {
		try {
			String returnVal = validateRegisterFields(message);
			if (returnVal.isEmpty()) {
				if (!saveUser()) {
					System.out.println("ERROR EN EL REGISTRO ");
					SendRegisterUnsusscesfull msg = new SendRegisterUnsusscesfull();
					JSONObject json = new JSONObject(msg);
					System.out.println("JSON MESSAGE TO SEND:"
							+ json.toString());
					return json.toString();
				}

				SendRegisterSussceful msg = new SendRegisterSussceful();
				JSONObject json = new JSONObject(msg);
				System.out.println("JSON MESSAGE TO SEND:" + json.toString());
				return json.toString();
			} else {
				SendRegisterUnsusscesfull msg = new SendRegisterUnsusscesfull();
				JSONObject json = new JSONObject(msg);
				System.out.println("JSON MESSAGE TO SEND:" + json.toString());
				return json.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private boolean saveUser() {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();
		try {

			user.setDate(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM));

			session.save(user);

			// generate update
			if (!this.photo.isEmpty()) {
				Photo photo = new Photo();
				photo.setIdUser(user.getId());
				// photo.setPhoto(this.photo);
				photo.setDate(ManageDate.formatDate(ManageDate
						.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS), ManageDate.YYYY_MM_DD_HH_MM_SS));
				photo.setIdCategory(CCategory.ALL);
				photo.setRanking(0L);
				session.save(photo);
				user.setIdPhoto(photo.getId());
				session.update(user);

				Update upd = new Update();
				
				upd.setDate(ManageDate.formatDate(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS), ManageDate.YYYY_MM_DD_HH_MM_SS));
						
				upd.setIdPhoto(photo.getId());
				upd.setIdCategory(CCategory.ALL);
				upd.setType(CUpdateType.PHOTO);
				upd.setIdUser(user.getId());
				session.save(upd);

				// save file whith photo base 64
				String path = savePhotoToDisk(user.getId(), photo.getId(),this.photo);

				// put the photo direction to photo
				photo.setPhoto(path);
				session.update(photo);
			}

			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}

	}

	

	private String validateRegisterFields(String message) {
		try {
			MsgRegister myUser = new ObjectMapper().readValue(message,
					MsgRegister.class);
			String returnVal = new String();
			if (ValidateUtil.validateEmptyString(myUser.getName())) {
				// send message to say user name cannot be empty
				returnVal = "emptyName";

			}
			if (ValidateUtil.validateEmptyString(myUser.getLastName())) {
				returnVal = "emptyLastName";

			}
			if (ValidateUtil.validateEmptyString(myUser.getEmail())) {
				returnVal = "emptyEmail";
			}
			if (ValidateUtil.validateEmptyString(myUser.getEmail())) {
				returnVal = "emptyEmail";
			}
			// quiere decir que los campos estan correctos entonces creo el
			// usuario
			if (returnVal.isEmpty()) {
				user = new User();
				user.setEmail(myUser.getEmail());
				user.setLastName(myUser.getLastName());
				user.setName(myUser.getName());
				user.setPassword(myUser.getPassword());
				if (myUser.getIdCountry() <= 0) {
					return "error";
				}
				user.setIdCountry(myUser.getIdCountry());
				user.setDescription(myUser.getDescription());

			}
			if (ValidateUtil.validateEmptyString(myUser.getPhoto())) {
				return "error";
			} else {
				photo = ((myUser.getPhoto()));
			}

			return returnVal;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
	public boolean registerWeb(User user, String imageStr) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();
		try {

			user.setDate(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM));

			session.save(user);

			// generate update
			
			if (!imageStr.isEmpty()) {
				Photo photo = new Photo();
				photo.setIdUser(user.getId());
				// photo.setPhoto(this.photo);
				photo.setDate(ManageDate.formatDate(ManageDate
						.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS), ManageDate.YYYY_MM_DD_HH_MM_SS));
				photo.setIdCategory(CCategory.ALL);
				photo.setRanking(0L);
				session.save(photo);
				user.setIdPhoto(photo.getId());
				session.update(user);

				Update upd = new Update();
				
				upd.setDate(ManageDate.formatDate(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS), ManageDate.YYYY_MM_DD_HH_MM_SS));		
				upd.setIdPhoto(photo.getId());
				upd.setIdCategory(CCategory.ALL);
				upd.setType(CUpdateType.PHOTO);
				upd.setIdUser(user.getId());
				session.save(upd);

				// save file whith photo base 64
				String path = savePhotoToDiskOriginal(user.getId(), photo.getId(),imageStr);

				// put the photo direction to photo
				photo.setPhoto(path);
				session.update(photo);
			}

			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}

	}

}
