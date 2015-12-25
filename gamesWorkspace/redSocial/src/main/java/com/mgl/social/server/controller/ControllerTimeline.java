package com.mgl.social.server.controller;

import java.util.ArrayList;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.mgl.social.server.MyServerEndpoint;
import com.mgl.social.server.controller.mails.ThreadSendMail;
import com.mgl.social.server.database.Comment;
import com.mgl.social.server.database.HashTag;
import com.mgl.social.server.database.Like;
import com.mgl.social.server.database.Notification;
import com.mgl.social.server.database.Photo;
import com.mgl.social.server.database.Post;
import com.mgl.social.server.database.SessionHibernate;
import com.mgl.social.server.database.Share;
import com.mgl.social.server.database.Update;
import com.mgl.social.server.database.User;
import com.mgl.social.server.database.Video;
import com.mgl.social.server.database.constant.CLogic;
import com.mgl.social.server.database.constant.CMailMessage;
import com.mgl.social.server.database.constant.CNotificationType;
import com.mgl.social.server.database.constant.CRanking;
import com.mgl.social.server.database.constant.CType;
import com.mgl.social.server.database.constant.CUpdateType;
import com.mgl.social.server.database.dao.CommentDAO;
import com.mgl.social.server.database.dao.LikeDAO;
import com.mgl.social.server.database.dao.PhotoDAO;
import com.mgl.social.server.database.dao.PostDAO;
import com.mgl.social.server.database.dao.ShareDAO;
import com.mgl.social.server.database.dao.UpdateDAO;
import com.mgl.social.server.database.dao.UserDAO;
import com.mgl.social.server.database.dao.VideoDAO;
import com.mgl.social.server.message.MsgCommentUpdate;
import com.mgl.social.server.message.MsgDeleteLikeShareComment;
import com.mgl.social.server.message.MsgIdUpdate;
import com.mgl.social.server.message.MsgUserCategory;
import com.mgl.social.server.message.send.SendDeleteActualization;
import com.mgl.social.server.message.send.SendGeneralViewLike;
import com.mgl.social.server.message.send.SendGeneralViewShare;
import com.mgl.social.server.message.send.SendTimelineUpdate;
import com.mgl.social.server.message.send.SendTimelineUpdateActualization;
import com.mgl.social.server.message.send.obj.ObjUpdate;
import com.mgl.social.server.message.send.obj.ObjViewUserGeneral;
import com.mgl.social.server.util.ManageDate;

public class ControllerTimeline extends BaseController {

	private javax.websocket.Session sessionAux;

	public ControllerTimeline(javax.websocket.Session session) {
		this.sessionAux = session;
	}

	public ControllerTimeline() {

	}

	public String getUpdate(User user, String messageComplete, Long step, Date date) {
		try {

			MsgUserCategory msgAux = new ObjectMapper().readValue(
					messageComplete, MsgUserCategory.class);

			ArrayList<ObjUpdate> updateList = new ArrayList<ObjUpdate>();
			updateList = loadUpdateList(user.getId(), user.getId(),
					msgAux.getIdCategory(), msgAux.getIdUpdateType(), step,date
					);
			SendTimelineUpdate msg = new SendTimelineUpdate(updateList);
			JSONObject json = new JSONObject(msg);
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public ArrayList<ObjUpdate> loadUpdateList(Long idUser, Long myId,
			Long idCategory, Long idUpdateType, 
			Long step, Date date) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();

			ArrayList<ObjUpdate> objList = dao.loadUpdateList(idUser, false, null, idCategory,
					idUpdateType, myId, step, date );
			for(ObjUpdate obj : objList){
				verifyPhotoInServer(obj.getPhoto());
				if(obj.getType().equals(CUpdateType.VIDEO_S)){
					//TODO uncomment this when i load my own videos not from youtube
					//verifyVideoInServer(obj.getContent());
				}else if(obj.getType().equals(CUpdateType.PHOTO_S)){
					//TODO try to make a virtual folder instead ofcopy from c:socialFiles to server if doesn't exist in server 
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


	public String like(String messageComplete, Long idUser) {
		try {

			MsgIdUpdate msg = new ObjectMapper().readValue(messageComplete,
					MsgIdUpdate.class);
			ObjUpdate objUpdate = generateLike(msg.getIdUpdate(), idUser,
					msg.getUserName());
			SendTimelineUpdateActualization send = new SendTimelineUpdateActualization(
					objUpdate);
			JSONObject json = new JSONObject(send);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";

	}

	private ObjUpdate generateLike(String idUpdate, Long idUser, String userName)
			throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();
			Update upd = dao.loadUpdateById(Long.valueOf(idUpdate));

			LikeDAO lkDao = new LikeDAO();
			Like like = lkDao.loadUserLikeByIdUpdate(idUser,
					Long.valueOf(idUpdate));

			

			if (upd.getType().equals(CUpdateType.PHOTO)) {
				PhotoDAO photoDAO = new PhotoDAO();
				Photo photo = photoDAO.loadPhotoById(upd.getIdPhoto());
				if (like == null)
					photo.setRanking(photo.getRanking() + CRanking.VALUE_LIKE);
				else
					photo.setRanking(photo.getRanking() - CRanking.VALUE_LIKE);
				session.update(photo);

			} else if (upd.getType().equals(CUpdateType.POST)) {

				PostDAO postDAO = new PostDAO();
				Post post = postDAO.loadPostById(upd.getIdPost());
				if (like == null)
					post.setRanking(post.getRanking() + CRanking.VALUE_LIKE);
				else
					post.setRanking(post.getRanking() - CRanking.VALUE_LIKE);

				session.update(post);

			} else if (upd.getType().equals(CUpdateType.VIDEO)) {

				VideoDAO videoDAO = new VideoDAO();
				Video video = videoDAO.loadVideoById(upd.getIdVideo());
				if (like == null)
					video.setRanking(video.getRanking() + CRanking.VALUE_LIKE);
				else
					video.setRanking(video.getRanking() - CRanking.VALUE_LIKE);

				session.update(video);

			}

			boolean likeme;
			if (like == null) {

				like = new Like();
				like.setIdUser(idUser);
				like.setIdUpdate(upd.getId());
				like.setDate(ManageDate.formatDate(ManageDate
						.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
						ManageDate.YYYY_MM_DD_HH_MM_SS));
				session.save(like);
				
				if(!idUser.equals(upd.getIdUser())){
					Notification notification = new Notification();
					notification.setIdUpdate(upd.getId());
					notification.setIdUserGenerator(idUser);
					notification.setIdUser(upd.getIdUser());
					notification.setDate(ManageDate.formatDate(ManageDate
							.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
							ManageDate.YYYY_MM_DD_HH_MM_SS));
					notification.setType(CNotificationType.LIKE);
					notification.setViewed(CLogic.FALSE);
					session.save(notification);
				}
				
				likeme = true;
				if (upd.getSendMail().equals(CLogic.TRUE)) {
					ThreadSendMail thread = new ThreadSendMail(upd.getMail(),
							CMailMessage.LIKE, userName);
					thread.start();
				}

			} else {
				session.delete(like);
				likeme = false;

			}

			tx.commit();
			ObjUpdate obj = dao.loadUpdateActualization(idUser,
					Long.valueOf(idUpdate));
			obj.setLikeMe(likeme);
			return obj;

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public String share(String messageComplete, Long idUser) {
		try {

			MsgIdUpdate msg = new ObjectMapper().readValue(messageComplete,
					MsgIdUpdate.class);

			ObjUpdate objUpdate = generateShare(msg.getIdUpdate(), idUser,
					msg.getUserName());
			SendTimelineUpdateActualization send = new SendTimelineUpdateActualization(
					objUpdate);
			JSONObject json = new JSONObject(send);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";

	}

	private ObjUpdate generateShare(String idUpdate, Long idUser,
			String userName) throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();
			Update upd = dao.loadUpdateById(Long.valueOf(idUpdate));
			
			if(!idUser.equals(upd.getIdUser())){
				Notification notification = new Notification();
				notification.setIdUpdate(upd.getId());
				notification.setIdUserGenerator(idUser);
				notification.setIdUser(upd.getIdUser());
				notification.setDate(ManageDate.formatDate(ManageDate
						.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
						ManageDate.YYYY_MM_DD_HH_MM_SS));
				notification.setType(CNotificationType.SHARE);
				notification.setViewed(CLogic.FALSE);
				session.save(notification);
			}
			
			Share share = new Share();

			share.setIdUpdate(upd.getId());
			share.setIdUser(idUser);
			share.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			session.save(share);

			Update update = dao.loadUpdateById(Long.valueOf(idUpdate));
			update.setIdUser(idUser);

			update.setDate(ManageDate.formatDate(
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
					ManageDate.YYYY_MM_DD_HH_MM_SS));
			update.setType(CUpdateType.SHARE);
			session.save(update);

			if (upd.getIdPhoto() != null) {
				PhotoDAO photoDAO = new PhotoDAO();
				Photo photo = photoDAO.loadPhotoById(upd.getIdPhoto());
				photo.setRanking(photo.getRanking() + CRanking.VALUE_SHARE);
				session.update(photo);
				if (upd.getSendMail().equals(CLogic.TRUE)) {
					ThreadSendMail thread = new ThreadSendMail(upd.getMail(),
							CMailMessage.SHARE, userName);
					thread.start();
				}

			} else if (upd.getIdPost() != null) {

				PostDAO postDAO = new PostDAO();
				Post post = postDAO.loadPostById(upd.getIdPost());
				post.setRanking(post.getRanking() + CRanking.VALUE_SHARE);
				session.update(post);

				if (upd.getSendMail().equals(CLogic.TRUE)) {
					ThreadSendMail thread = new ThreadSendMail(upd.getMail(),
							CMailMessage.SHARE, userName);
					thread.start();
				}

			} else if (upd.getIdVideo() != null) {

				VideoDAO videoDAO = new VideoDAO();
				Video video = videoDAO.loadVideoById(upd.getIdVideo());
				video.setRanking(video.getRanking() + CRanking.VALUE_SHARE);
				session.update(video);
				if (upd.getSendMail().equals(CLogic.TRUE)) {
					ThreadSendMail thread = new ThreadSendMail(upd.getMail(),
							CMailMessage.SHARE, userName);
					thread.start();
				}

			}

			// session.save(share);

			tx.commit();

			// TODO enviar el share que genere solo si estoy en el timeline
			// general o en el timeline de mi perfil
			/*
			 * ObjUpdate shareObject = dao.loadUpdateActualization(idUser,
			 * update.getId());
			 * 
			 * SendTimelineUpdateActualization send = new
			 * SendTimelineUpdateActualization( shareObject); JSONObject json =
			 * new JSONObject(send); System.out.println("object update " +
			 * json.toString());
			 * MyServerEndpoint.sendTextMessage(json.toString(), sessionAux);
			 */
			ObjUpdate obj = dao.loadUpdateActualization(idUser,
					Long.valueOf(idUpdate));

			return obj;

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	public String comment(String messageComplete, Long idUser) {
		try {

			MsgCommentUpdate msg = new ObjectMapper().readValue(
					messageComplete, MsgCommentUpdate.class);

			ObjUpdate objUpdate = generateComment(msg.getIdUpdate(),
					msg.getComment(), idUser, msg.getUserName());
			SendTimelineUpdateActualization send = new SendTimelineUpdateActualization(
					objUpdate);
			JSONObject json = new JSONObject(send);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";

	}

	private ObjUpdate generateComment(String idUpdate, String comment,
			Long idUser, String userName) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();
			Update upd = dao.loadUpdateById(Long.valueOf(idUpdate));

			Comment cmt = new Comment();

			cmt.setIdUpdate(upd.getId());
			cmt.setIdUser(idUser);
			cmt.setComment(comment);
			session.save(cmt);
			
			if(!idUser.equals(upd.getIdUser())){
				Notification notification = new Notification();
				notification.setIdUpdate(upd.getId());
				notification.setIdUserGenerator(idUser);
				notification.setIdUser(upd.getIdUser());
				notification.setDate(ManageDate.formatDate(ManageDate
						.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS),
						ManageDate.YYYY_MM_DD_HH_MM_SS));
				notification.setType(CNotificationType.COMMENT);
				notification.setViewed(CLogic.FALSE);
				session.save(notification);
			}
			
			if (upd.getType().equals(CUpdateType.PHOTO)) {

				PhotoDAO photoDAO = new PhotoDAO();
				Photo photo = photoDAO.loadPhotoById(upd.getIdPhoto());
				photo.setRanking(photo.getRanking() + CRanking.VALUE_COMMENT);
				session.update(photo);
				if (upd.getSendMail().equals(CLogic.TRUE)) {
					ThreadSendMail thread = new ThreadSendMail(upd.getMail(),
							CMailMessage.COMMENT, userName);
					thread.start();
				}

			} else if (upd.getType().equals(CUpdateType.POST)) {

				PostDAO postDAO = new PostDAO();
				Post post = postDAO.loadPostById(upd.getIdPost());
				post.setRanking(post.getRanking() + CRanking.VALUE_COMMENT);
				session.update(post);
				if (upd.getSendMail().equals(CLogic.TRUE)) {
					ThreadSendMail thread = new ThreadSendMail(upd.getMail(),
							CMailMessage.COMMENT, userName);
					thread.start();
				}

			} else if (upd.getType().equals(CUpdateType.VIDEO)) {

				VideoDAO videoDAO = new VideoDAO();
				Video video = videoDAO.loadVideoById(upd.getIdVideo());
				video.setRanking(video.getRanking() + CRanking.VALUE_COMMENT);
				session.update(video);

				if (upd.getSendMail().equals(CLogic.TRUE)) {
					ThreadSendMail thread = new ThreadSendMail(upd.getMail(),
							CMailMessage.COMMENT, userName);
					thread.start();
				}

			}

			tx.commit();

			ObjUpdate obj = dao.loadUpdateActualization(idUser,
					Long.valueOf(idUpdate));

			return obj;

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public String viewLikeTimeline(String messageComplete, Long idUser,
			Long limit, Long multiplier) {
		try {

			MsgIdUpdate msg = new ObjectMapper().readValue(messageComplete,
					MsgIdUpdate.class);
			ArrayList<ObjViewUserGeneral> generalViewList = new ArrayList<ObjViewUserGeneral>();
			generalViewList = loadGeneralPerfilInfo(msg.getIdUpdate(),
					CUpdateType.UPDATE, CUpdateType.LIKE, idUser, limit,
					multiplier);
			SendGeneralViewLike send = new SendGeneralViewLike(generalViewList);
			JSONObject json = new JSONObject(send);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String viewShareTimeline(String messageComplete, Long idUser,
			Long limit, Long multiplier) {
		try {
			MsgIdUpdate msg = new ObjectMapper().readValue(messageComplete,
					MsgIdUpdate.class);
			ArrayList<ObjViewUserGeneral> generalViewList = new ArrayList<ObjViewUserGeneral>();
			generalViewList = loadGeneralPerfilInfo(msg.getIdUpdate(),
					CUpdateType.UPDATE, CUpdateType.SHARE, idUser, limit,
					multiplier);
			SendGeneralViewShare send = new SendGeneralViewShare(
					generalViewList);
			JSONObject json = new JSONObject(send);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String viewCommentTimeline(String messageComplete, Long idUser,
			Long limit, Long multiplier) {
		try {

			MsgIdUpdate msg = new ObjectMapper().readValue(messageComplete,
					MsgIdUpdate.class);
			ArrayList<ObjViewUserGeneral> generalViewList = new ArrayList<ObjViewUserGeneral>();
			generalViewList = loadGeneralPerfilInfo(msg.getIdUpdate(),
					CUpdateType.UPDATE, CUpdateType.COMMENT, idUser, limit,
					multiplier);
			SendGeneralViewLike send = new SendGeneralViewLike(generalViewList);
			JSONObject json = new JSONObject(send);
			System.out.println("Esto es lo que envio comment: "
					+ json.toString());
			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	public String deleteLikeShareComment(String messageComplete, Long idUser,
			Long limit, Long multiplier) {
		try {

			MsgDeleteLikeShareComment msg = new ObjectMapper().readValue(
					messageComplete, MsgDeleteLikeShareComment.class);

			deleteLikeShareComment(msg, idUser);

			ArrayList<ObjViewUserGeneral> generalViewList = new ArrayList<ObjViewUserGeneral>();

			generalViewList = loadGeneralPerfilInfo("" + msg.getId(),
					msg.getTypeUpdate(), msg.getType(), idUser, limit,
					multiplier);
			SendGeneralViewLike sendAux = new SendGeneralViewLike(
					generalViewList);
			JSONObject jsonAux = new JSONObject(sendAux);
			MyServerEndpoint.sendTextMessage(jsonAux.toString(), sessionAux);

			// return actualization

			ObjUpdate objUpdate = loadUpdateActualization(idUser, msg.getId());
			SendTimelineUpdateActualization send = new SendTimelineUpdateActualization(
					objUpdate);
			JSONObject json = new JSONObject(send);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private ObjUpdate loadUpdateActualization(Long idUser, Long idUpdate)
			throws Exception {

		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();
			ObjUpdate obj = dao.loadUpdateActualization(idUser,
					Long.valueOf(idUpdate));

			return obj;

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public void deleteLikeShareComment(MsgDeleteLikeShareComment msg,
			Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {

			System.out.println("DELETE");
			Long id = msg.getIdType();
			if (msg.getType().equals(CUpdateType.LIKE)) {
				LikeDAO likeDAO = new LikeDAO();
				Like lk = likeDAO.loadById(id);
				session.delete(lk);

			} else if (msg.getType().equals(CUpdateType.COMMENT)) {
				CommentDAO commentDAO = new CommentDAO();
				Comment lk = commentDAO.loadById(id);
				session.delete(lk);

			} else if (msg.getType().equals(CUpdateType.SHARE)) {
				ShareDAO shareDAO = new ShareDAO();
				Share lk = shareDAO.loadById(id);
				session.delete(lk);

			} else {
				System.out.println("nothing to delete");
				return;
			}
			// UpdateDAO dao = new UpdateDAO();
			// dao.deleteLikeShareComment(msg.getIdType(), msg.getType(),
			// msg.getTypeUpdate(), idUser);

			// search if i have to delete the original

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public String deleteUpdate(String messageComplete, Long idUser) {
		try {

			MsgIdUpdate msg = new ObjectMapper().readValue(messageComplete,
					MsgIdUpdate.class);

			deleteMyUpdate(msg.getIdUpdate(), idUser);

			SendDeleteActualization send = new SendDeleteActualization(
					Long.valueOf(msg.getIdUpdate()));

			JSONObject json = new JSONObject(send);

			return json.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private void deleteMyUpdate(String idUpdate, Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();
			Update upd = dao.loadUpdateById(Long.valueOf(idUpdate));

			if (!idUser.equals(upd.getIdUser())) {
				System.out.println("No era un update mio, usuario de id "
						+ idUser);
				return;
			}

			session.delete(upd);

			Long type = null;

			type = upd.getType();

			if (type.equals(CUpdateType.PHOTO)) {
				PhotoDAO photoDAO = new PhotoDAO();
				Photo photo = photoDAO.loadPhotoById(upd.getIdPhoto());
				if (photo != null) {
					if (idUser.equals(photo.getIdUser())) {
						session.delete(photo);
					}

				}

			} else if (type.equals(CUpdateType.POST)) {

				PostDAO postDAO = new PostDAO();
				Post post = postDAO.loadPostById(upd.getIdPost());
				if (post != null) {
					if (idUser.equals(post.getIdUser())) {
						session.delete(post);
					}

				}

			} else if (type.equals(CUpdateType.VIDEO)) {

				VideoDAO videoDAO = new VideoDAO();
				Video video = videoDAO.loadVideoById(upd.getIdVideo());

				if (video != null) {
					if (idUser.equals(video.getIdUser())) {
						session.delete(video);
					}

				}

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

	public void commentWeb(String comment, Long idUser, String idUpdate,
			String userName) {
		try {
			generateComment(idUpdate, comment, idUser, userName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void likeWeb(String idUpdate, Long idUser, String userName) {
		try {

			generateLike(idUpdate, idUser, userName);

		} catch (Exception e) {

		}

	}

	public void shareWeb(String idUpdate, Long idUser, String userName) {
		try {

			generateShare(idUpdate, idUser, userName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteUpdateWeb(String idUpdate, Long idUser) {
		try {

			deleteMyUpdate(idUpdate, idUser);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteLikeShareCommentWeb(ObjViewUserGeneral objGeneral,
			Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();
		try {
			System.out.println("DELETE");
			Long id = objGeneral.getIdType();
			if (objGeneral.getType().equals(CUpdateType.LIKE)) {
				LikeDAO likeDAO = new LikeDAO();
				Like lk = likeDAO.loadById(id);
				session.delete(lk);

			} else if (objGeneral.getType().equals(CUpdateType.COMMENT)) {
				CommentDAO commentDAO = new CommentDAO();
				Comment lk = commentDAO.loadById(id);
				session.delete(lk);

			} else if (objGeneral.getType().equals(CUpdateType.SHARE)) {
				ShareDAO shareDAO = new ShareDAO();
				Share lk = shareDAO.loadById(id);
				session.delete(lk);

			} else {
				System.out.println("nothing to delete");
				return;
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

	public String loadPhotoFromUser(Long idUser) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();
		try {
			UserDAO dao = new UserDAO();
			return dao.loadPhotoFromUser(idUser);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public ArrayList<ObjUpdate> loadUpdateListByHashtag(HashTag hash,
			Long myId, Long idCategory, Long idUpdateType,
			Long limit, Long multiplierTimeline) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();
			System.out.println("hash name:"+hash.getName()+":");
			return dao.loadUpdateListByHashtag(hash.getName(), false, null, idCategory,
					idUpdateType, limit, myId, multiplierTimeline);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}

	}

	public ArrayList<ObjUpdate> loadSingleUpdate(Long idUpdate, Long myId) throws Exception {
		Session session = SessionHibernate.getSession();
		Transaction tx = session.beginTransaction();

		try {
			UpdateDAO dao = new UpdateDAO();
			ArrayList<ObjUpdate> objList = dao.loadSingleUpdate(idUpdate,myId); 
			for(ObjUpdate obj : objList){
				verifyPhotoInServer(obj.getContent());
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
