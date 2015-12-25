package com.example.androidannotationstest;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import com.example.androidannotationstest.fragment.MyFragment;
import com.example.androidannotationstest.fragment.MyFragment2;
import com.example.androidannotationstest.fragment.MyFragment2_;
import com.example.androidannotationstest.fragment.MyFragmentTop;
import com.example.androidannotationstest.fragment.MyFragmentTop_;
import com.example.androidannotationstest.fragment.MyFragment_;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;

@EActivity(R.layout.activity_fragment_animation)
public class FragmentAnimationActivity extends Activity{
	
	private boolean appear = false; 
	
	private Fragment fragment;
	
	@Click(R.id.buttonTop)
	public void buttonTopClicked(){
		try {
			
			if(!appear){
			
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_botton, R.anim.slide_in_botton);
				
				MyFragmentTop fragment = MyFragmentTop_.builder().build();
				ft.replace(R.id.fragmentTopBotton, fragment, "MyFragmentTop");
				ft.show(fragment);
				ft.commit();
				
				this.fragment = fragment;
			}else{
				
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_in_top);
					ft.hide(fragment);
					ft.commit();

			}
			
			appear = !appear;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Click(R.id.buttonBotton)
	public void buttonBottonClicked(){
		try {
			
			if(!appear){
			
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.appear_from_top, R.anim.appear_from_top);
				
				MyFragmentTop fragment = MyFragmentTop_.builder().build();
				ft.replace(R.id.fragmentTopBotton, fragment, "MyFragmentTop");
				ft.show(fragment);
				ft.commit();
				
				this.fragment = fragment;
			}else{
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.hide_from_top, R.anim.hide_from_top);
				ft.hide(fragment);
				ft.commit();
				
			}
			
			appear = !appear;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Click(R.id.buttonLeft)
	public void buttonLeftClicked(){
		try {
			
			if(!appear){
			
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
				
				MyFragmentTop fragment = MyFragmentTop_.builder().build();
				ft.replace(R.id.fragmentTopBotton, fragment, "MyFragmentTop");
				ft.show(fragment);
				ft.commit();
				
				this.fragment = fragment;
			}else{
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_right);
				ft.hide(fragment);
				ft.commit();
				
			}
			
			appear = !appear;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Click(R.id.buttonRight)
	public void buttonRightClicked(){
		try {
			
			if(!appear){
			
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_from_left);
				
				MyFragmentTop fragment = MyFragmentTop_.builder().build();
				ft.replace(R.id.fragmentTopBotton, fragment, "MyFragmentTop");
				ft.show(fragment);
				ft.commit();
				
				this.fragment = fragment;
			}else{
				
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_right);
				ft.hide(fragment);
				ft.commit();
				
			}
			
			appear = !appear;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
