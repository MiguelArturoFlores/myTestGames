package com.mgl.testandroid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgl.testandroid.R;

public class ArticleFragment2 extends Fragment{
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		 return inflater.inflate(R.layout.article_view_fragment2, container, false);
	    }
}