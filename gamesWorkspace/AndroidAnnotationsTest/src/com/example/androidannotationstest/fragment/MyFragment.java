package com.example.androidannotationstest.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import com.example.androidannotationstest.R;
import com.mgl.otto.util.BusProvider;
import com.mgl.otto.util.messages.MyButtonMessage;
import com.squareup.otto.Subscribe;

import android.app.Fragment;
import android.widget.TextView;

@EFragment(R.layout.fragment1)
public class MyFragment extends Fragment{

	@ViewById(R.id.textViewFragment1)
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
	
}
