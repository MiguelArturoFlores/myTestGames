package com.mgl.base.server;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;

import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.InformativeHUD;

public class ServerSingleton {

	private static ServerSingleton instance;

	//server things
	private String METHOD_NAME = "price";
	private String URL = "http://www.idontremembergames.net16.net/service.php?wsdl";
	private String SOAP_ACTION = "http://www.www.idontremembergames.net16.net.com/demourn:demo/price";
	private String NAMESPACE = "http://www.www.idontremembergames.net16.net.com/demourn:demo";
	
	private ServerSingleton() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ServerSingleton getInstance() {
		try {
			if (instance == null) {
				instance = new ServerSingleton();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public void testServer() {
		
		try {
			
			 //myAsyncTask myRequest = new myAsyncTask();
		     //myRequest.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	 private class myAsyncTask extends AsyncTask<Void, Void, Void>    {
		 
		 
	        @Override
	        protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	        }
	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();       
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0) {
	        	
	        	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				request.addProperty("algo1","algo1");
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE  httpTransport = new HttpTransportSE(URL);
				httpTransport.debug = true;
				
				 try {
		                httpTransport.call(SOAP_ACTION, envelope);
		            } catch (HttpResponseException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (XmlPullParserException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } //send request
		            SoapObject result = null;
		            try {
		                result = (SoapObject)envelope.getResponse();
		            } catch (SoapFault e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } 
		 
		            Log.d("App",""+result.getProperty(1).toString());
		           String response = result.getProperty(1).toString();
				
		           HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(response),true);
	        	
	        	return null;
	        	
	        }
	 }
	        
	
}
