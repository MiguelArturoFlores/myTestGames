package com.example.androidannotationstest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.example.androidannotationstest.controller.MyController;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewById(R.id.textView)
	public TextView textView;

	@ViewById(R.id.buttonNextActivity)
	public Button buttonNext;
	
	@ViewById(R.id.buttonFragmentShow)
	public Button buttonFragmentShow;
	
	@AfterViews
	public void changeTextView(){
		try {
			//textView.setText("Hardcoded text to show");

			textView.setText(new MyController().getTextToShow());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Click(R.id.buttonNextActivity)
	public void buttonNextClick(){
		try {
			
			//System.out.println("buttonCLicked");
			
			FragmentViewActivity_.intent(this).start();
			
			//to send parameters
			//Intent intent = FragmentViewActivity_.intent(this).get();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Click(R.id.buttonFragmentShow)
	public void buttonFragmentShow(){
		try {
			
			FragmentAnimationActivity_.intent(this).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
