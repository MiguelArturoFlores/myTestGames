package com.mgl.social.server.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.mgl.social.server.MyServerEndpoint;
import com.mgl.social.server.database.HashTag;
import com.mgl.social.server.database.Photo;
import com.mgl.social.server.database.Post;
import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.Update;
import com.mgl.social.server.database.User;
import com.mgl.social.server.database.Video;
import com.mgl.social.server.database.constant.CCategory;
import com.mgl.social.server.database.constant.CUpdateType;
import com.mgl.social.server.database.dao.PerfilDAO;
import com.mgl.social.server.database.dao.UpdateDAO;
import com.mgl.social.server.database.dao.UserDAO;
import com.mgl.social.server.database.dao.VideoDAO;
import com.mgl.social.server.message.MsgIdUpdate;
import com.mgl.social.server.message.MsgIdUser;
import com.mgl.social.server.message.MsgUpdateInfo;
import com.mgl.social.server.message.MsgUploadPhoto;
import com.mgl.social.server.message.MsgUploadPost;
import com.mgl.social.server.message.MsgUserCategory;
import com.mgl.social.server.message.MsgViewPerfil;
import com.mgl.social.server.message.send.SendGeneralViewLike;
import com.mgl.social.server.message.send.SendPerfilUpdate;
import com.mgl.social.server.message.send.SendPerfilUpdatePhoto;
import com.mgl.social.server.message.send.SendPerfilView;
import com.mgl.social.server.message.send.SendSusscefullUpdate;
import com.mgl.social.server.message.send.SendUnsusscefullUpdate;
import com.mgl.social.server.message.send.obj.ObjPerfil;
import com.mgl.social.server.message.send.obj.ObjUpdate;
import com.mgl.social.server.message.send.obj.ObjUpdatePhoto;
import com.mgl.social.server.message.send.obj.ObjViewUserGeneral;
import com.mgl.social.server.util.ManageDate;

public class ControllerPerfil extends BaseController {

	private javax.websocket.Session session;

	public ControllerPerfil(javax.websocket.Session session) {
		this.session = session;
	}

	public ControllerPerfil() {

	}

	public String getPerfil(String messageComplete, Long id) {
		try {
			System.out.println("getPerfil :" + messageComplete);
			MsgViewPerfil msg = new ObjectMapper().readValue(messageComplete,
					MsgViewPerfil.class);
			Long idUser;
			if (msg.getIdUser() == null || msg.getIdUser().isEmpty()) {
				// view my perfil from general Button home
				idUser = id;
			} else {
				// view other or maybe my
				idUser = Long.valueOf(msg.getIdUser());
			}
			ObjPerfil objPerfil = loadPerfilInfo(idUser, id);
			SendPerfilView msgSend = new SendPerfilView(objPerfil);
			JSONObject json = new JSONObject(msgSend);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private ObjPerfil loadPerfilInfo(Long idUser, Long myId) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			PerfilDAO dao = new PerfilDAO();

			return dao.loadPerfilInfo(idUser, myId);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public String getPerfilUpdates(String messageComplete, Long myId,
			Long step, Date date) {
		try {
			MsgUserCategory user = new ObjectMapper().readValue(
					messageComplete, MsgUserCategory.class);

			ArrayList<ObjUpdate> updateList = new ArrayList<ObjUpdate>();
			Long idAux = Long.valueOf(user.getIdUser());

			updateList = loadUpdate(idAux, myId, user.getIdCategory(),
					user.getIdUpdateType(), step, date);
			SendPerfilUpdate msg = new SendPerfilUpdate(updateList);
			JSONObject json = new JSONObject(msg);
			System.out.println("JSON MESSAGE TO SEND TIMELINE: "
					+ json.toString());
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private ArrayList<ObjUpdate> loadUpdate(Long idUser, Long myId,
			Long idCategory, Long idTypeUpdate, Long step, Date date) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			return dao.loadUpdateList(idUser, true, null, idCategory,
					idTypeUpdate, myId,step, date);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	private ArrayList<ObjUpdatePhoto> loadUpdatePhoto(Long idUser)
			throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			return dao.loadUpdatePhoto(idUser);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public String uploadPhoto(String messageComplete, Long idUser) {
		try {
			MsgUploadPhoto user = new ObjectMapper().readValue(messageComplete,
					MsgUploadPhoto.class);
			savePhoto(user, idUser);

			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private void savePhoto(MsgUploadPhoto photo64, Long idUser)
			throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			Photo photo = new Photo();
			photo.setIdUser(idUser);
			// photo.setPhoto(photo64.getPhoto());
			photo.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));

			photo.setRanking(0L);
			if (photo64.getIdCategory() == null || photo64.getIdCategory() <= 0) {
				photo64.setIdCategory(CCategory.ALL);
			}
			photo.setIdCategory(photo64.getIdCategory());
			session.save(photo);

			Update update = new Update();

			update.setIdCategory(photo64.getIdCategory());
			update.setIdUser(idUser);
			update.setDescription(photo64.getDescription());

			update.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			update.setType(CUpdateType.PHOTO);
			update.setIdPhoto(photo.getId());
			session.save(update);

			// save file whith photo base 64
			String path = savePhotoToDisk(idUser, photo.getId(),
					photo64.getPhoto());

			// put the photo direction to photo
			photo.setPhoto(path);
			session.update(photo);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public String getPhotoList(String messageComplete) {
		try {

			MsgIdUser user = new ObjectMapper().readValue(messageComplete,
					MsgIdUser.class);

			ArrayList<ObjUpdatePhoto> updateList = new ArrayList<ObjUpdatePhoto>();
			updateList = loadUpdatePhoto(Long.valueOf(user.getIdUser()));
			SendPerfilUpdatePhoto msg = new SendPerfilUpdatePhoto(updateList);
			JSONObject json = new JSONObject(msg);
			System.out.println("JSON MESSAGE TO SEND TIMELINE: "
					+ updateList.size() + " " + json.toString());
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String updateInfo(String messageComplete, Long idUser) {
		try {

			MsgUpdateInfo msg = new ObjectMapper().readValue(messageComplete,
					MsgUpdateInfo.class);

			boolean updated = updateMyPerfilInfo(msg, idUser);

			JSONObject json;
			if (updated) {
				SendSusscefullUpdate send = new SendSusscefullUpdate();
				json = new JSONObject(send);

				ObjPerfil objPerfil = loadPerfilInfo(idUser, idUser);
				SendPerfilView msgSend = new SendPerfilView(objPerfil);
				JSONObject jsonAux = new JSONObject(msgSend);
				MyServerEndpoint.sendTextMessage(jsonAux.toString(), session);

			} else {
				SendUnsusscefullUpdate send = new SendUnsusscefullUpdate();
				json = new JSONObject(send);
			}

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private boolean updateMyPerfilInfo(MsgUpdateInfo msg, Long idUser) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {

			UserDAO dao = new UserDAO();
			User user = dao.loadUserInfo(idUser);

			if (user == null) {
				return false;
			}
			if (!msg.getPassword().equals(user.getPassword())) {
				return false;
			}

			user.setName(msg.getName());
			user.setLastName(msg.getLastName());
			user.setEmail(msg.getEmail());
			user.setDescription(msg.getDescription());
			if (msg.getPasswordNew() != null && !msg.getPasswordNew().isEmpty()) {
				user.setPassword(msg.getPasswordNew());
			}

			session.update(user);

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

	public String follow(String messageComplete, Long myId) {
		try {

			MsgIdUser user = new ObjectMapper().readValue(messageComplete,
					MsgIdUser.class);

			followUser(myId, Long.valueOf(user.getIdUser()));

			ObjPerfil objPerfil = loadPerfilInfo(
					Long.valueOf(user.getIdUser()), myId);
			SendPerfilView msgSend = new SendPerfilView(objPerfil);
			JSONObject json = new JSONObject(msgSend);
			return json.toString();

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

			ObjPerfil objPerfil = loadPerfilInfo(
					Long.valueOf(user.getIdUser()), myId);
			SendPerfilView msgSend = new SendPerfilView(objPerfil);
			JSONObject json = new JSONObject(msgSend);
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String followingList(String messageComplete, Long limit,
			Long multiplier) {
		try {
			MsgIdUser user = new ObjectMapper().readValue(messageComplete,
					MsgIdUser.class);

			ArrayList<ObjViewUserGeneral> generalViewList = new ArrayList<ObjViewUserGeneral>();
			generalViewList = loadGenralFollowingList(
					Long.valueOf(user.getIdUser()), limit, multiplier);

			SendGeneralViewLike send = new SendGeneralViewLike(generalViewList);
			System.out.println("general list " + generalViewList.size());
			JSONObject json = new JSONObject(send);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	public String followList(String messageComplete, Long limit, Long multiplier) {
		try {
			MsgIdUser user = new ObjectMapper().readValue(messageComplete,
					MsgIdUser.class);

			ArrayList<ObjViewUserGeneral> generalViewList = new ArrayList<ObjViewUserGeneral>();
			generalViewList = loadGeneralFollowList(
					Long.valueOf(user.getIdUser()), limit, multiplier);

			SendGeneralViewLike send = new SendGeneralViewLike(generalViewList);
			System.out.println("general list " + generalViewList.size());
			JSONObject json = new JSONObject(send);

			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	public String uploadPost(String messageComplete, Long idUser) {

		try {
			MsgUploadPost user = new ObjectMapper().readValue(messageComplete,
					MsgUploadPost.class);
			savePost(user, idUser);

			return "";

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private void savePost(MsgUploadPost user, Long idUser) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			Post post = new Post();
			post.setIdUser(idUser);
			post.setText(user.getText());
			post.setRanking(0L);
			if (user.getIdCategory() == null || user.getIdCategory() <= 0) {
				user.setIdCategory(CCategory.ALL);
			}
			post.setIdCategory(user.getIdCategory());
			post.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			session.save(post);

			Update update = new Update();
			update.setIdCategory(user.getIdCategory());
			update.setIdUser(idUser);

			update.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			update.setType(CUpdateType.POST);
			update.setIdPost(post.getId());
			session.save(update);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public String getUpdatesPost(String messageComplete, Long id, 
			Long myId, Long step, Date date) {

		try {
			MsgUserCategory user = new ObjectMapper().readValue(
					messageComplete, MsgUserCategory.class);

			ArrayList<ObjUpdate> updateList = new ArrayList<ObjUpdate>();

			updateList = loadUpdatePost(Long.valueOf(user.getIdUser()),
					user.getIdCategory(), user.getIdUpdateType(), myId,
					step,date);
			SendPerfilUpdate msg = new SendPerfilUpdate(updateList);
			JSONObject json = new JSONObject(msg);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private ArrayList<ObjUpdate> loadUpdatePost(Long idUser, Long idCategory,
			Long idTypeUpdate,  Long myId,Long step, Date date)
			throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			return dao.loadUpdateList(idUser, true, CUpdateType.POST,
					idCategory, idTypeUpdate, myId,step,date);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public String getUpdatesPhoto(String messageComplete, Long id, 
			Long myId,Long step, Date date) {

		try {
			MsgUserCategory user = new ObjectMapper().readValue(
					messageComplete, MsgUserCategory.class);

			ArrayList<ObjUpdate> updateList = new ArrayList<ObjUpdate>();

			updateList = loadUpdatePhotos(Long.valueOf(user.getIdUser()),
					user.getIdCategory(), user.getIdUpdateType(), myId,
					step, date);

			SendPerfilUpdate msg = new SendPerfilUpdate(updateList);
			JSONObject json = new JSONObject(msg);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private ArrayList<ObjUpdate> loadUpdatePhotos(Long idUser, Long idCategory,
			Long idTypeUpdate, Long myId, Long step, Date date)
			throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			return dao.loadUpdateList(idUser, true, CUpdateType.PHOTO,
					idCategory, idTypeUpdate, myId, step, date);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public String changePhoto(String messageComplete, Long idUser) {
		try {
			MsgIdUpdate upd = new ObjectMapper().readValue(messageComplete,
					MsgIdUpdate.class);

			updateUserPhoto(upd.getIdUpdate(), idUser);

			ObjPerfil objPerfil = loadPerfilInfo(idUser, idUser);
			SendPerfilView msgSend = new SendPerfilView(objPerfil);
			JSONObject json = new JSONObject(msgSend);
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	private void updateUserPhoto(String idPhoto, Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UserDAO dao = new UserDAO();
			User user = dao.loadUserInfo(idUser);
			if (user == null) {
				return;
			}
			if (idPhoto.isEmpty()) {
				return;
			}
			user.setIdPhoto(Long.valueOf(idPhoto));
			session.update(user);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public ObjPerfil loadPerfilInfoWeb(Long idUser, Long myId) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			PerfilDAO dao = new PerfilDAO();
			ObjPerfil obj = dao.loadPerfilInfo(idUser, myId);
			verifyPhotoInServer(obj.getPhoto());
			return obj;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public ArrayList<ObjUpdate> loadUpdateListWeb(Long idUser, Long myId,
			Long idCategory, Long idTypeUpdate, Long step, Date date)
			throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			return dao.loadUpdateList(idUser, true, null, idCategory,
					idTypeUpdate, myId, step, date);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public void savePostWeb(Long idUser, Long idCategory, String postText)
			throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			Post post = new Post();
			post.setIdUser(idUser);
			post.setText(postText);
			post.setRanking(0L);

			post.setIdCategory(idCategory);
			post.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			session.save(post);

			Update update = new Update();
			update.setIdCategory(idCategory);
			update.setIdUser(idUser);

			update.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			update.setType(CUpdateType.POST);
			update.setIdPost(post.getId());
			session.save(update);

			ArrayList<String> hashtagList = searchHashtag(postText);

			for (String text : hashtagList) {
				HashTag hash = new HashTag();
				hash.setIdUpdate(update.getId());
				hash.setName(text);
				session.save(hash);
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

	public ObjUpdate savePhotoWeb(Long idUser, Long idCategory,
			String base64Image, String description) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			Photo photo = new Photo();
			photo.setIdUser(idUser);
			// photo.setPhoto(base64Image);
			photo.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			photo.setRanking(0L);

			photo.setIdCategory(idCategory);
			session.save(photo);

			Update update = new Update();

			update.setIdCategory(idCategory);
			update.setIdUser(idUser);
			update.setDescription(description);

			update.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			update.setType(CUpdateType.PHOTO);
			update.setIdPhoto(photo.getId());
			session.save(update);

			// save file whith photo base 64
			savePhotoToDisk(idUser, photo.getId(), base64Image);
			String path = savePhotoToDiskOriginal(idUser, photo.getId(),
					base64Image);

			// put the photo direction to photo
			photo.setPhoto(path);
			session.update(photo);

			ArrayList<String> hashtagList = searchHashtag(description);

			for (String text : hashtagList) {
				HashTag hash = new HashTag();
				hash.setIdUpdate(update.getId());
				hash.setName(text);
				session.save(hash);
			}

			tx.commit();

			ObjUpdate obj = new ObjUpdate();
			obj.setId(update.getId() + "");
			obj.setCommentNumber("0");
			obj.setFollowing(false);
			obj.setDescription(update.getDescription());
			obj.setIdUser(update.getIdUser() + "");
			obj.setLikeMe(false);
			obj.setLikeNumber(0 + "");
			obj.setMe(true);
			obj.setShareNumber(0 + "");
			obj.setType(update.getType() + "");
			return obj;

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public boolean updateMyPerfilInfoWeb(User userEdit, Long idUser) {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {

			UserDAO dao = new UserDAO();
			User user = dao.loadUserInfo(idUser);

			if (user == null) {
				return false;
			}
			if (!user.getPassword().equals(userEdit.getPassword())) {
				return false;
			}

			user.setName(userEdit.getName());
			user.setLastName(userEdit.getLastName());
			user.setEmail(userEdit.getEmail());
			user.setRecieveMail(userEdit.getRecieveMail());
			user.setDescription(userEdit.getDescription());
			if (userEdit.getPasswordNew() != null
					&& !userEdit.getPasswordNew().isEmpty()) {
				user.setPassword(userEdit.getPasswordNew());
			}

			session.update(user);

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

	public ArrayList<ObjViewUserGeneral> loadSuggestionList(Long idUser,
			Long limitSugesstion, Long multiplierSugesstion) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			PerfilDAO dao = new PerfilDAO();
			ArrayList<ObjViewUserGeneral> objList = dao.loadSuggestionList(
					idUser, limitSugesstion, multiplierSugesstion);
			for (ObjViewUserGeneral obj : objList) {
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

	public ArrayList<HashTag> loadHashTaglist(Long idUser, Long limitHashTag,
			Long multiplierHashTag, Date date) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			PerfilDAO dao = new PerfilDAO();
			return dao.loadHashTaglist(idUser, limitHashTag, multiplierHashTag,
					date);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public ArrayList<ObjUpdatePhoto> loadAllPhotoWeb(Long idUser,
			Long limitPhoto, Long multiplierPhoto) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			return dao.loadUpdatePhoto(idUser);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public void changePhotoWeb(ObjUpdatePhoto objUpdatePhoto, Long idUser) {
		try {
			updateUserPhoto(objUpdatePhoto.getIdPhoto(), idUser);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<HashTag> loadHashTaglistCountry(Long idCountry,
			Long limitHastag, Long multiplierHastag, Date date)
			throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			PerfilDAO dao = new PerfilDAO();
			return dao.loadHashTaglistCountry(idCountry, limitHastag,
					multiplierHastag, date);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public void saveVideoToDisk(InputStream inputstream, Long idUser,
			Long idCategory, String description, String extension)
			throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			VideoDAO dao = new VideoDAO();
			Video video = new Video();
			video.setIdCategory(idCategory);
			video.setIdUser(idUser);
			video.setRanking(0L);
			video.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));

			session.save(video);

			Update update = new Update();

			update.setIdCategory(video.getIdCategory());
			update.setIdUser(idUser);
			update.setDescription(description);

			update.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			update.setType(CUpdateType.VIDEO);
			update.setIdVideo(video.getId());
			session.save(update);

			// save file whith photo base 64
			String path = saveVideoToDisk(idUser, video.getId(), inputstream,
					extension);

			// put the photo direction to photo
			video.setVideo(path);
			session.update(video);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public void saveYoutubeVideo(String youtubeVideo, Long idUser,
			Long idCategory, String description) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			VideoDAO dao = new VideoDAO();
			Video video = new Video();
			video.setIdCategory(idCategory);
			video.setIdUser(idUser);
			video.setRanking(0L);
			video.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			video.setLink(youtubeVideo);
			video.setVideo(youtubeVideo);

			session.save(video);

			Update update = new Update();

			update.setIdCategory(video.getIdCategory());
			update.setIdUser(idUser);
			update.setDescription(description);

			update.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			update.setType(CUpdateType.VIDEO);
			update.setIdVideo(video.getId());
			session.save(update);

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
