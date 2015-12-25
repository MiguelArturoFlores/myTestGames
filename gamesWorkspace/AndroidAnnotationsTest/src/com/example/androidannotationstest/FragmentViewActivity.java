package com.example.androidannotationstest;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.mgl.otto.util.BusProvider;
import com.mgl.otto.util.messages.HideMessage;
import com.mgl.otto.util.messages.MyButtonMessage;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

@EActivity(R.layout.activity_fragment_view)
public class FragmentViewActivity extends Activity {

	@ViewById(R.id.textToSend)
	EditText textToSend;
	
	
	
	@Click(R.id.buttonSendText)
	public void sendClick(){
		try {
			String message = textToSend.getText().toString();
			textToSend.setText("");
			MyButtonMessage msg = new MyButtonMessage();
			msg.setMessage(message);
			BusProvider.getInstance().post(msg);
			
			HideMessage hiden = new HideMessage();
			hiden.setHideMessage("this is a hide Message");
			BusProvider.getInstance().post(hiden);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
