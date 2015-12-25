package com.mgl.social.server.controller;

import java.util.ArrayList;

import javax.websocket.Session;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import com.mgl.social.server.database.User;
import com.mgl.social.server.message.MsgPrincipal;
import com.mgl.social.server.message.send.SendMore;

public class ControllerMessage {

	private String messageComplete = new String();
	private Session session = null;
	private User user = null;
	private ArrayList<Long> followerList;
	private ArrayList<Long> followingList;

	private Long limitTimeline = 2L;
	private Long multiplierTimeline = 1L;
	//general
	private Long limitGeneralView = 3L;
	private Long multiplierGeneralView = 1L;
	//search
	private Long limitSearchView = 1L;
	private Long multiplierSearchView= 1L;
	

	private LastMessage lastMessage = null;
	private boolean more;

	public ControllerMessage(Session session) {
		this.session = session;
	}

	public String recieveMessage(String message, Session sessionAux)
			throws Exception {
		MsgPrincipal myMessage = null;
		try {

			// reading Json object into a java object myMessage

			myMessage = new ObjectMapper().readValue(message,
					MsgPrincipal.class);
			// JSONObject jsonObj = new JSONObject(message);

			if (myMessage.isFragmented()) {
				messageComplete = messageComplete + myMessage.getMessage();
				SendMore msgAux = new SendMore();
				JSONObject json = new JSONObject(msgAux);
				return json.toString();

			}

			messageComplete = messageComplete + myMessage.getMessage();
			// System.out.println("id:" + myMessage.getId() + "MessageComplete:"
			// + messageComplete);
			System.out.println("llego un mensaje " + myMessage.getId()
					+ " idAux " + myMessage.getIdSecond());
			String retVal = manageMessageComplete(sessionAux, myMessage);

			more = false;

			if ((myMessage.getIdSecond().equals("getUpdate") || myMessage
					.getIdSecond().equals("REQUEST_RANKING"))
					&& !myMessage.getId().equals("MORE")) {
				lastMessage = new LastMessage();
				lastMessage.setMyMessage(myMessage);
				lastMessage.setMessageComplete(messageComplete);
				System.out.println("guardo el ultimo mensaje");
			}

			return retVal;

		} catch (Exception e) {
			throw e;
		} finally {
			if (myMessage != null) {
				if (!myMessage.isFragmented()) {
					messageComplete = new String();
				}
			}
		}
	}

	private String manageMessageComplete(Session sessionAux,
			MsgPrincipal myMessage) {
		try {

			if (myMessage.getId().equals("DECONEXION")) {
				sessionAux.close();
				return "close";
			}
			if (myMessage.getId().equals("MORE")) {
				if (!lastMessage.getMyMessage().getId().equals("MORE")) {
					messageComplete = lastMessage.getMessageComplete();
					myMessage = lastMessage.getMyMessage();
					more = true;
					return manageMessageComplete(sessionAux, myMessage);
				} else {
					return "error";
				}
			}
			if (myMessage.getId().equals("login")) {

				ControllerLogin controllerLogin = new ControllerLogin(
						sessionAux);
				String messageAux = controllerLogin.login(messageComplete);
				user = controllerLogin.getUser();
				try {

				} catch (Exception e) {
					followerList = controllerLogin.loadFollowerList(user
							.getId());
					followingList = controllerLogin.loadFollowingList(user
							.getId());
				}
				printList(followingList);
				return messageAux;
			}
			if (myMessage.getId().equals("register")) {
				ControllerRegister controllerRegister = new ControllerRegister(
						sessionAux);
				return controllerRegister.register(messageComplete);
			}
			if (myMessage.getId().equals("RANKING")) {
				ControllerRanking controllerRanking = new ControllerRanking(
						sessionAux);

				if (myMessage.getIdSecond().equals("REQUEST_RANKING")) {
					if (!more) {
						multiplierTimeline = 1L;
					} else {
						multiplierTimeline++;
					}
					System.out.println("REQUEST RANKING");
					return controllerRanking.getRankingRequest(messageComplete,
							user.getId(), user.getId(), limitTimeline,
							multiplierTimeline);

				}

			}
			if (myMessage.getId().equals("perfil")) {
				ControllerPerfil controllerPerfil = new ControllerPerfil(
						sessionAux);

				if (myMessage.getIdSecond().equals("perfil")) {

					return controllerPerfil.getPerfil(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("getUpdate")) {
					if (!more) {
						multiplierTimeline = 1L;
					} else {
						multiplierTimeline++;
					}
					return controllerPerfil.getPerfilUpdates(messageComplete,
							user.getId(), limitTimeline, multiplierTimeline);
				}
				if (myMessage.getIdSecond().equals("uploadPhoto")) {
					return controllerPerfil.uploadPhoto(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("perfil_upload_post")) {
					return controllerPerfil.uploadPost(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("PERFIL_CHANGE_PHOTO")) {
					System.out.println("user " + user.getId());
					return controllerPerfil.changePhoto(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("PERFIL_GET_UPDATES_POST")) {
					System.out.println("user " + user.getId());
					return controllerPerfil.getUpdatesPost(messageComplete,
							user.getId(), limitTimeline, user.getId(),
							multiplierTimeline);
				}
				if (myMessage.getIdSecond().equals("PERFIL_GET_UPDATES_PHOTO")) {
					System.out.println("user " + user.getId());
					return controllerPerfil.getUpdatesPhoto(messageComplete,
							user.getId(), limitTimeline, user.getId(),
							multiplierTimeline);
				}
				if (myMessage.getIdSecond().equals("getPhotoList")) {
					return controllerPerfil.getPhotoList(messageComplete);
				}
				if (myMessage.getIdSecond().equals("perfil_update_info")) {
					return controllerPerfil.updateInfo(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("perfil_follow")) {
					return controllerPerfil.follow(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("perfil_unfollow")) {
					return controllerPerfil.unfollow(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("perfil_following_list")) {
					if(!more){
						multiplierGeneralView= 1L;
					}else{
						multiplierGeneralView++;
					}
					return controllerPerfil.followingList(messageComplete,limitGeneralView,multiplierGeneralView);
				}
				if (myMessage.getIdSecond().equals("perfil_follow_list")) {
					if(!more){
						multiplierGeneralView= 1L;
					}else{
						multiplierGeneralView ++;
					}
					return controllerPerfil.followList(messageComplete,limitGeneralView,multiplierGeneralView);
				}

			}
			if (myMessage.getId().equals("search")) {
				ControllerSearch controllerSearch = new ControllerSearch(
						sessionAux);

				if (myMessage.getIdSecond().equals("search_text")) {
					if(!more){
						multiplierSearchView= 1L;
					}else{
						multiplierSearchView ++;
					}
					return controllerSearch.search(messageComplete,
							user.getId(),limitSearchView,multiplierSearchView);
				}
				if (myMessage.getIdSecond().equals("search_persone")) {
					return controllerSearch.searchPersone(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("search_update")) {
					return controllerSearch.searchUpdate(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("search_follow")) {
					return controllerSearch.follow(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("search_unfollow")) {
					return controllerSearch.unfollow(messageComplete,
							user.getId());
				}
			}
			if (myMessage.getId().equals("timeline")) {

				ControllerTimeline controllerTimeline = new ControllerTimeline(
						sessionAux);

				if (myMessage.getIdSecond().equals("getUpdate")) {
					if (!more) {
						multiplierTimeline = 1L;
					} else {
						multiplierTimeline++;
					}

					return controllerTimeline.getUpdate(user, messageComplete,
							limitTimeline, multiplierTimeline);
				}
				if (myMessage.getIdSecond().equals("like")) {
					return controllerTimeline.like(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("share")) {
					return controllerTimeline.share(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("comment")) {
					return controllerTimeline.comment(messageComplete,
							user.getId());
				}
				if (myMessage.getIdSecond().equals("view_like")) {
					if (!more) {
						multiplierGeneralView = 1L;
					} else {
						multiplierGeneralView++;
					}

					return controllerTimeline.viewLikeTimeline(messageComplete,
							user.getId(), limitGeneralView,
							multiplierGeneralView);
				}
				if (myMessage.getIdSecond().equals("view_share")) {
					if(!more){
						multiplierGeneralView= 1L;
					}else{
						multiplierGeneralView ++;
					}
					return controllerTimeline.viewShareTimeline(
							messageComplete, user.getId(), limitGeneralView,
							multiplierGeneralView);
				}
				if (myMessage.getIdSecond().equals("view_comment")) {
					if(!more){
						multiplierGeneralView= 1L;
					}else{
						multiplierGeneralView ++;
					}
					return controllerTimeline.viewCommentTimeline(
							messageComplete, user.getId(), limitGeneralView,
							multiplierGeneralView);
				}
				if (myMessage.getIdSecond().equals("delete_like_share_comment")) {
					if(!more){
						multiplierGeneralView= 1L;
					}else{
						multiplierGeneralView ++;
					}
					return controllerTimeline.deleteLikeShareComment(
							messageComplete, user.getId(), limitGeneralView,
							multiplierGeneralView);
				}
				if (myMessage.getIdSecond().equals("delete_update")) {
					return controllerTimeline.deleteUpdate(messageComplete,
							user.getId());
				}
				return "timeline_undefined";
			}
			if (myMessage.getId().equals("quit")) {
				try {
					// session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,
					// "Game ended"));
				} catch (Exception e) {
					throw e;
				}
			}

			return "error";

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	private void printList(ArrayList<Long> list) {
		try {
			for (Long l : list) {
				System.out.println("follower list " + l);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String getMessageComplete() {
		return messageComplete;
	}

	public void setMessageComplete(String messageComplete) {
		this.messageComplete = messageComplete;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
