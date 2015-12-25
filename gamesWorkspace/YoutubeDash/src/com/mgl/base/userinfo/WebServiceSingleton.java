package com.mgl.base.userinfo;

import java.util.Locale;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

import com.mgl.twitter.TwitterSingleton;

public class WebServiceSingleton {

	private static WebServiceSingleton instance = null;

	/*
	 * private final String NAMESPACE = "http://www.w3schools.com/webservices/";
	 * private final String URL =
	 * "http://www.w3schools.com/webservices/tempconvert.asmx"; private final
	 * String SOAP_ACTION =
	 * "http://www.w3schools.com/webservices/CelsiusToFahrenheit"; private final
	 * String METHOD_NAME = "CelsiusToFahrenheit";
	 */
	
	/* TEST VALUES 
	private final String NAMESPACE = "http://192.168.0.101/webServicesTest/";
	private final String URL = "http://192.168.0.101/webServicesTest/serverIdrGames.php";
	private final String SOAP_ACTION = "http://192.168.0.101/webServicesTest/insertDataUser";
	private final String METHOD_NAME = "insertDataUser";
	private String TAG = "PGGURU";
	*/
	
	private final String NAMESPACE = "http://173.254.28.135/~idontrem/webServices/";
	private final String URL = "http://173.254.28.135/~idontrem/webServices/serverIdrGames.php";
	private final String SOAP_ACTION = "http://173.254.28.135/~idontrem/webServices/insertDataUser";
	private final String METHOD_NAME = "insertDataUser";
	private String TAG = "PGGURU";

	private WebServiceSingleton() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WebServiceSingleton getInstance() {
		try {
			if (instance == null) {
				instance = new WebServiceSingleton();
			}
		} catch (Exception e) {
		}
		return instance;
	}

	public static void setInstance(WebServiceSingleton instance) {
		WebServiceSingleton.instance = instance;
	}

	public void sendEmail(String email) {
		try {

			if (UserInfoSingleton.getInstance().isHasSendMail()) {
				// HUDManagerSingleton.getInstance().addHUD(new
				// InformativeHUD("email already send "+UserInfoSingleton.getInstance().getEmail()),
				// true);
				return;
			}
			SendEmail sendEmail = new SendEmail(email);
			sendEmail.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendGeneralData() {
		try {
			
			if (!UserInfoSingleton.getInstance().isSendInfo() || UserInfoSingleton.getInstance().getEmail() == null || UserInfoSingleton.getInstance().getEmail().isEmpty()) {
				return;
			}
			
			//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("sending general info"), true);
			SendEmail sendEmail= new SendEmail(UserInfoSingleton.getInstance().getEmail());
			sendEmail.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendDataTwitterYoutube() {
		try {

			if (UserInfoSingleton.getInstance().isHasSendTwitter()) {
				//HUDManagerSingleton.getInstance().addHUD(
				//		new InformativeHUD("twitter already send"
				//				+ UserInfoSingleton.getInstance().getEmail()),
				//		true);
				return;
			}

			SendTwitter sendTwitter = new SendTwitter();
			sendTwitter.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// public void sendDataAsync() {
	// SceneManagerSingleton.getInstance().getActivity()
	// .runOnUiThread(new Runnable() {
	//
	// @Override
	// public void run() {
	// // sendData();
	// SendData send = new SendData();
	// send.execute("");
	// }
	//
	// });
	//
	// }

	private boolean sendData(String emailTxt, String twitterName,
			String twitterAccessToken, String twitterSecret, String language,
			String country) {
		try {
			
			String contWhatsapp = UserInfoSingleton.getInstance().getContShareWhatsapp()+"";
			String contTwitter = UserInfoSingleton.getInstance().getContShareTwitter()+"";
			String contFacebook = UserInfoSingleton.getInstance().getContShareFacebook()+"";
			String starRating = UserInfoSingleton.getInstance().getStarRating()+"";
			
			//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(contFacebook+" "+contTwitter+" "+ contWhatsapp+" "+sta), true);
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			// Property which holds input parameters
			PropertyInfo name = new PropertyInfo();
			// Set Name
			name.setName("email");
			// Set Value
			name.setValue(emailTxt);
			// Set dataType
			name.setType(String.class);
			// Add the property to request object
			request.addProperty(name);

			PropertyInfo twtName = new PropertyInfo();
			twtName.setName("twitterName");
			twtName.setValue(twitterName);
			twtName.setType(String.class);
			request.addProperty(twtName);

			PropertyInfo twtAccess = new PropertyInfo();
			twtAccess.setName("twitterAccessToken");
			twtAccess.setValue(twitterAccessToken);
			twtAccess.setType(String.class);
			request.addProperty(twtAccess);

			PropertyInfo twtSecret = new PropertyInfo();
			twtSecret.setName("twitterSecret");
			twtSecret.setValue(twitterSecret);
			twtSecret.setType(String.class);
			request.addProperty(twtSecret);

			PropertyInfo languageP = new PropertyInfo();
			languageP.setName("language");
			languageP.setValue(language);
			languageP.setType(String.class);
			request.addProperty(languageP);

			PropertyInfo countryP = new PropertyInfo();
			countryP.setName("country");
			countryP.setValue(country);
			countryP.setType(String.class);
			request.addProperty(countryP);
			
			PropertyInfo starP = new PropertyInfo();
			starP.setName("star");
			starP.setValue(starRating);
			starP.setType(String.class);
			request.addProperty(starP);
			
			PropertyInfo whatsappP = new PropertyInfo();
			whatsappP.setName("whatsapp");
			whatsappP.setValue(contWhatsapp);
			whatsappP.setType(String.class);
			request.addProperty(whatsappP);
			
			PropertyInfo facebookP = new PropertyInfo();
			facebookP.setName("facebook");
			facebookP.setValue(contFacebook);
			facebookP.setType(String.class);
			request.addProperty(facebookP);

			PropertyInfo twitterP = new PropertyInfo();
			twitterP.setName("twitter");
			twitterP.setValue(contTwitter);
			twitterP.setType(String.class);
			request.addProperty(twitterP);
			
		
			// Create envelope
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			// envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			// Create HTTP call object
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,
					60000);

			try {
				// Invole web service
				androidHttpTransport.call(SOAP_ACTION, envelope);
				// Get the response
				String response = (String) envelope.getResponse();
				// Assign it to fahren static variable
				String textToShow = response.toString();

				// HUDManagerSingleton.getInstance().addHUD(new
				// InformativeHUD(textToShow), true);
				if (textToShow.equals("true")) {
					return true;
				}
				return false;
				//

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// private class SendData extends AsyncTask<String, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(String... params) {
	// try {
	//
	// sendData("abc", "miguel", "", "",);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// }

	private class SendEmail extends AsyncTask<String, Void, Void> {

		private String email;

		public SendEmail(String email) {
			this.email = email;
		}

		@Override
		protected Void doInBackground(String... params) {
			try {

				boolean result = sendData(email, "", "", "", Locale
						.getDefault().getLanguage(), Locale.getDefault()
						.getCountry());
				if (result) {
					 //HUDManagerSingleton.getInstance().addHUD(new
					 //InformativeHUD("trueACA"), true);
					UserInfoSingleton.getInstance().setHasSendMail(true);
				} else {
					 //HUDManagerSingleton.getInstance().addHUD(new
					 //InformativeHUD("falseACA"), true);
					UserInfoSingleton.getInstance().setHasSendMail(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			
		}

	}

	private class SendTwitter extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			try {
				if (!TwitterSingleton.getInstance().isLoggedIn()) {
					return null;
				}
				boolean result = sendData(UserInfoSingleton.getInstance()
						.getEmail(), TwitterSingleton.getInstance()
						.getUserName(), TwitterSingleton.getInstance()
						.getAccessToken(), TwitterSingleton.getInstance()
						.getAccessSecret(), Locale.getDefault().getLanguage(),
						Locale.getDefault().getCountry());

				if (result) {
					// HUDManagerSingleton.getInstance().addHUD(new
					// InformativeHUD("trueACA"), true);
					UserInfoSingleton.getInstance().setHasSendTwitter(true);
					UserInfoSingleton.getInstance().setSendInfo(false);
				} else {
					// HUDManagerSingleton.getInstance().addHUD(new
					// InformativeHUD("falseACA"), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
