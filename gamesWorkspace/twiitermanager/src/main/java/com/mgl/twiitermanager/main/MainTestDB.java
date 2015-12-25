package com.mgl.twiitermanager.main;

import com.mgl.twiitermanager.controller.DBController;
import com.mgl.twiitermanager.controller.TwitterController;
import com.mgl.twiitermanager.model.Follower;
import com.mgl.twiitermanager.model.User;
import com.mgl.twiitermanager.util.ManageDate;

public class MainTestDB {

	public static void main(String[] args) {
		try {
			
			DBController controller = new DBController();
			
			//controller.insertUser(new User("miguel"),"1232132");
			controller.insertFollower(new Follower(1L, 2L, "test", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
