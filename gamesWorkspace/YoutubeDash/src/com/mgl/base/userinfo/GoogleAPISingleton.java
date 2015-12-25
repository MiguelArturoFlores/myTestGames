package com.mgl.base.userinfo;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class GoogleAPISingleton {

	
	private static GoogleAPISingleton instance =null;
	
	
	private final static String YOUTUBE = "https://www.googleapis.com/auth/youtube";
	private final static String YOUTUBE_UPLOAD= "https://www.googleapis.com/auth/youtube.upload";
	private final static String YOUTUBE_PARTNER= "https://www.googleapis.com/auth/youtubepartner"; 
	
	private GoogleAPISingleton(){
		
	}


	public static GoogleAPISingleton getInstance() {
		try {
			if(instance ==null){
				instance = new GoogleAPISingleton();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return instance;
	}


	public static void setInstance(GoogleAPISingleton instance) {
		GoogleAPISingleton.instance = instance;
	}
	
	public void loginToYoutube(){
		try {
			
			if(UserInfoSingleton.getInstance().getEmail()==null || UserInfoSingleton.getInstance().getEmail().isEmpty() || !UserInfoSingleton.getInstance().isHasSendMail()){
				SceneManagerSingleton.getInstance().getActivity().requestEmailAccountToSend();
				return;
			}
			
			//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("requesting youtube"), true);
			
			String scope = "oauth2:" +YOUTUBE+" "+YOUTUBE_PARTNER+" "+YOUTUBE_UPLOAD;
			
			GetUserNameTask userNameTask = new GetUserNameTask(scope, UserInfoSingleton.getInstance().getEmail());
			userNameTask.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class GetUserNameTask extends AsyncTask{
		
		Activity mActivity;
		String mScope;
	    String mEmail;

	    public GetUserNameTask(String scope, String email){
	    	
	    	  this.mActivity = SceneManagerSingleton.getInstance().getActivity();
		      this.mScope = scope;
		      this.mEmail = email;
	    	
	    }
	    /**
	     * Executes the asynchronous job. This runs when you call execute()
	     * on the AsyncTask instance.
	     */
	    @Override
		protected Object doInBackground(Object... params) {
	        try {
	            String token = fetchToken();
	            if (token != null) {
	            	HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(""+token), true);
	                // **Insert the good stuff here.**
	                // Use the token to access the user's Google data.
	            }
	        } catch (IOException e) {
	            // The fetchToken() method handles Google-specific exceptions,
	            // so this indicates something went wrong at a higher level.
	            // TIP: Check for network connectivity before starting the AsyncTask.
	        	//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("problem3"), true);
	        }
	        return null;
	    }

	    /**
	     * Gets an authentication token from Google and handles any
	     * GoogleAuthException that may occur.
	     */
	    protected String fetchToken() throws IOException {
	        try {
	        	
	            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
	            
	        } catch (UserRecoverableAuthException userRecoverableException) {
	            // GooglePlayServices.apk is either old, disabled, or not present
	            // so we need to show the user some UI in the activity to recover.
	        	handleException(userRecoverableException);
	        	//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("problem1"), true);
	        } catch (GoogleAuthException fatalException) {
	            // Some other type of unrecoverable exception has occurred.
	            // Report and log the error as appropriate for your app.
	        	//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("problem1"), true);
	        }
	        return null;
	    }

	    
	    /**
		 * This method is a hook for background threads and async tasks that need to
		 * provide the user a response UI when an exception occurs.
		 */
	    
	    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1254;
	    
		public void handleException(final Exception e) {
		    // Because this call comes from the AsyncTask, we must ensure that the following
		    // code instead executes on the UI thread.
		   SceneManagerSingleton.getInstance().getActivity().runOnUiThread(new Runnable() {
		        @Override
		        public void run() {
		            if (e instanceof GooglePlayServicesAvailabilityException) {
		                // The Google Play services APK is old, disabled, or not present.
		                // Show a dialog created by Google Play services that allows
		                // the user to update the APK
		                int statusCode = ((GooglePlayServicesAvailabilityException)e)
		                        .getConnectionStatusCode();
		                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
		                        mActivity,
		                        REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
		                dialog.show();
		            } else if (e instanceof UserRecoverableAuthException) {
		                // Unable to authenticate, such as when the user has not yet granted
		                // the app access to the account, but the user can fix this.
		                // Forward the user to an activity in Google Play services.
		                Intent intent = ((UserRecoverableAuthException)e).getIntent();
		                mActivity.startActivityForResult(intent,
		                        REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
		            }
		        }
		    });
		}
		
	}
	
	
	
	
}
