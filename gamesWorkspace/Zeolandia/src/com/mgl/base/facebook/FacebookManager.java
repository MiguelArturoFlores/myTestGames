package com.mgl.base.facebook;

import java.util.Arrays;


import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.mgl.base.analitycs.TrackerName;
import com.mgl.drop.MainDropActivity;
import com.mgl.zeolandia.*;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.ShareHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public final class FacebookManager {

	
	private FacebookManager instance  = null;
	
	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");
	
	private static final String PERMISSION = "publish_actions";
	
	public static final String SHARE_VOLUNTARY = "SHARE_VOLUNTARY";

	public static final String SHARE_TO_UNLOCK = "SHARE_TO_UNLOCK";
	

	private static String sFirstName;
	private static boolean sUserLoggedIn;

	
	private FacebookManager(){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void facebookLoginAux(Activity act, final String action, final Level level) {
		try {
			Session ses = Session.openActiveSession(act, true, new Session.StatusCallback() {
				
				
				
				@Override
				public void call(Session session, SessionState state, Exception exception) {
					try {
						
						//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("beginig "+state.name() ),true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			if(ses ==null){
				HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Cannot get facebook session "),true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void facebookLogin(Activity act, final String action, final Level level) {
		
		Session ses = Session.openActiveSession(act, true, new Session.StatusCallback() {
			@SuppressWarnings("deprecation")
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				try {
					HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("beginig" ),true);
				if (!session.isClosed() ) {
					HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("oppened" ),true);
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() {
								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("completed" ),true);
									
									if (user != null) {
										Log.d("Loged in facebook", " LOGGED IN");
										// CALLBACK: USER IS LOGGED IN
										// DO YOUR STUFF HERE
										// e.g. fbUsername =
										// user.getFirstName();
										// this will save the users first name
										// to a public variable fbUsername
										HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("performing" ),true);
										performAction(action,level);
									}
									else{
										//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("user null" ),true);
									}
									
								}

								
							});
				}else {
					HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("NOT oppened "+state.name() ),true);
				}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		if(ses ==null){
			HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Cannot get facebook session "),true);
		}
	}

	public static void performAction(String action, Level level) {
		try {
	
			if(!hasPublishPermission()){
				
				HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("No tienes permisos de publicar" ),true);
				Session session = Session.getActiveSession();
				session.requestNewPublishPermissions(new com.facebook.Session.NewPermissionsRequest(SceneManagerSingleton.getInstance().getActivity(), PERMISSION));
				//session.requestNewPublishPermissions(new Session.NewPermissionsRequest(SceneManagerSingleton.getInstance().getActivity(), PERMISSION));
				
			}
			HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Si tengo permiso" ),true);
			
			if(action.equals(SHARE_VOLUNTARY)){
				shareVoluntaryAction();
				

			}else if(action.equals(SHARE_TO_UNLOCK)){
				
				shareToUnlock(level);
			}
							
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void shareToUnlock(Level level) {
		
		try {
			
			post("Crappy Pigeon ",SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.shareToUnlockFacebook), SceneManagerSingleton.getInstance().getActivity());
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance().getActivity(), DatabaseDrop.DB_NAME, null, MainDropActivity.DB_VERSION);
			dao.unlockLevel(level);
			
			SceneManagerSingleton manager = SceneManagerSingleton.getInstance();
			manager.setCurrentScene(AllScenes.SELECT_LEVEL);

			ShareHud hud = new ShareHud();
			HUDManagerSingleton.getInstance().addHUD(hud,true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void shareVoluntaryAction() {
		try {
			
			post("Crappy Pigeon", SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.shareVoluntaryFacebook), SceneManagerSingleton.getInstance().getActivity());
			
			
			ShareHud hud = new ShareHud();
			HUDManagerSingleton.getInstance().addHUD(hud,true);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static FacebookDialog.ShareDialogBuilder createShareDialogBuilderForLink() {
        return new FacebookDialog.ShareDialogBuilder(SceneManagerSingleton.getInstance().getActivity())
                .setName("Hello Facebook")
                .setDescription("The 'Hello Facebook' sample application showcases simple Facebook integration")
                .setLink("http://developers.facebook.com/android");
    }

	 private static boolean hasPublishPermission() {
	        Session session = Session.getActiveSession();
	        return session != null && session.getPermissions().contains("publish_actions");
	 }
	
	public static boolean isLoggedIn(Activity activity) {
		try {

			Session session = Session.getActiveSession();
			if (session != null) {
				if(session.isOpened()){
					return true;
				}else{
					return false;
				}
				
			} else {

			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*public static void checkUserLoggedIn(final MainDropActivity mainDropActivity) {
		mainDropActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				openActiveSession(mainDropActivity, false,
						new Session.StatusCallback() {
							@Override
							public void call(Session session,
									SessionState state, Exception exception) {
								if (session.isOpened()) {

									com.facebook.Request
											.executeMeRequestAsync(
													session,
													new com.facebook.Request.GraphUserCallback() {

														@Override
														public void onCompleted(
																com.facebook.model.GraphUser user,
																com.facebook.Response response) {
															if (user != null) {
																sFirstName = user
																		.getFirstName();
																FacebookManager.sUserLoggedIn = true;
															} else {
																FacebookManager.sUserLoggedIn = false;
															}
														}
													});
								}
							}
						}, PERMISSIONS);
			}
		});
	}
*/
	
	/*
	 * private static void loginAndPost(final String pData, final
	 * MainDropActivity yourActivity) { yourActivity.runOnUiThread(new
	 * Runnable() {
	 * 
	 * @Override public void run() { openActiveSession(yourActivity, true, new
	 * Session.StatusCallback() {
	 * 
	 * @Override public void call(Session session, SessionState state, Exception
	 * exception) { if (session.isOpened()) {
	 * Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	 * 
	 * @Override public void onCompleted( GraphUser user, Response response) {
	 * if (user != null) { sFirstName = user .getFirstName(); final
	 * Session.OpenRequest openRequest; // openRequest = new
	 * Session.OpenRequest( // yourActivity); openRequest
	 * .setPermissions(PERMISSIONS); sUserLoggedIn = true; post(user
	 * .getFirstName(), pData); } else { sUserLoggedIn = false; } } }); } } },
	 * PERMISSIONS); } }); }
	 */

	public static void post(final String pFirstName, final String pData,
			Activity yourActivity) {
		yourActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Bundle params = new Bundle();
				params.putString("name", pFirstName + " " +pData);
				
				params.putString("caption", "Crappy Pigeon " + SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.getItFree));
				params.putString("description",
						SceneManagerSingleton.getInstance().getActivity().getResources().getString(R.string.getItOnGet));
				params.putString(
						"link",
						"https://play.google.com/store/apps/details?id=com.mgl.sortproject.probabilitysorting");
				params.putString(
						"picture",
						"http://2.bp.blogspot.com/-t_FsPv4JPAQ/VB9ZDxn88GI/AAAAAAAAAEw/UF1Qv9F2RZg/s1600/PORTADA%2BCON%2BLOGO%2BGOOGLE.png");
						
				JSONObject actions = new JSONObject();
				try {
					actions.put("name", "DOWNLOAD");
					actions.put(
							"link",
							"https://play.google.com/store/apps/details?id=com.mgl.sortproject.probabilitysorting");
				} catch (Exception e) {
				}
				;
				params.putString("actions", actions.toString());
				Request.Callback callback = new Request.Callback() {
					@Override
					public void onCompleted(Response response) {
						try {
							JSONObject graphResponse = response
									.getGraphObject().getInnerJSONObject();
							@SuppressWarnings("unused")
							String postID = null;
							try {
								postID = graphResponse.getString("id");
							} catch (JSONException e) {
							}
						} catch (NullPointerException e) {
						}
					}
				};
				Request request = new Request(Session.getActiveSession(),
						"me/feed", params, HttpMethod.POST, callback);
				RequestAsyncTask task = new RequestAsyncTask(request);
				task.execute();
			}
		});
	}

	

}
