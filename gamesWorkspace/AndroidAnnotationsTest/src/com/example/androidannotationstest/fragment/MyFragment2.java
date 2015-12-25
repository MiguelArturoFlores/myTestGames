package com.example.androidannotationstest.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.example.androidannotationstest.R;
import com.mgl.otto.util.BusProvider;
import com.mgl.otto.util.messages.HideMessage;
import com.mgl.otto.util.messages.MyButtonMessage;
import com.squareup.otto.Subscribe;

import android.app.Fragment;
import android.widget.TextView;

@EFragment(R.layout.fragment2)
public class MyFragment2 extends Fragment{

	@ViewById(R.id.textViewFragment2)
	TextView text;
	
	
	@AfterViews
	public void registerToBusProvider(){
		try {
			
			BusProvider.getInstance().register(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Subscribe
	public void handleButtonPress(MyButtonMessage event){
		try {
			
			text.setText(event.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Subscribe
	public void handleButtonPress(HideMessage event){
		try {
			
			text.setText(text.getText()+event.getHideMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
