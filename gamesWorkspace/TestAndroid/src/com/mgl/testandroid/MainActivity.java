package com.mgl.testandroid;

import com.mgl.testandroid.fragment.ArticleFragment;
import com.mgl.testandroid.fragment.ArticleFragment2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static final String EXTRA_MESSAGE = "com.mgl.testandroid.EXTRA_MESSAGE";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
		
			setContentView(R.layout.activity_main);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	//fits android button send in activity_main
	public void sendMessage(View view) {
		try {

			Intent intent  = new Intent(this,DisplayMessageActivity.class);
			EditText editText = (EditText) findViewById(R.id.edit_message);
			String message = editText.getText().toString();
			intent.putExtra(EXTRA_MESSAGE, message);
			startActivity(intent);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//manage fragments 
	public void  changeFragment(View view){
		try {
			Fragment fr;
			if(view == findViewById(R.id.buttonShowFragment)){
				
				fr =new ArticleFragment();
				
			}else{
				fr =new ArticleFragment2();
			}
			
			FragmentManager fm = getFragmentManager();
			FragmentTransaction fragmentTransaction = fm.beginTransaction();
			fragmentTransaction.replace(R.id.fragmentContainer, fr);
			fragmentTransaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
