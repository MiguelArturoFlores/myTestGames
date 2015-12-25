package com.mgl.drop.game.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.scene.SceneManagerSingleton;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.LevelListDrawable;
import android.util.Log;

public class DatabaseDrop extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.mgl.crappypigeon/databases/";
	private static String DB_NAME = "palomaDB";
	// private static int DB_VERSION = 3;
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	private ArrayList<Level> leveList = null;
	private boolean isUpgrade = false;

	public DatabaseDrop(Context contexto, String nombre, CursorFactory factory,
			int version) {

		super(contexto, nombre, factory, version);
		this.myContext = contexto;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// No hacemos nada aqui
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {

			Log.d("UPGRADING OLD DATABASE", "UPDATING from oldversion "
					+ oldVersion + " to " + newVersion);

			// boolean dbExist = checkDataBase();

			// if (!dbExist) {
			// this.getReadableDatabase();
			// copyDataBase();
			// Log.d("UPGRADING 23 OLD DATABASE",
			// "UPDATING from oldversion "+oldVersion+" to "+newVersion);
			// return;
			// }

			/*String myPath = DB_PATH + DB_NAME;
			db = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.NO_LOCALIZED_COLLATORS
							| SQLiteDatabase.OPEN_READWRITE);
			*/
			

			leveList = loadAllLevel(db);
			
			for(Level  l:leveList){
				Log.d("level ", "level "+l.getId()+" " +l.getAvalible()+" facebook "+l.getFacebook());
			}

			

			isUpgrade = true;
			// ArrayList<Level> levelNew = loadAllLevel();

			// db.close();

			//
			// db = SQLiteDatabase.openDatabase(myPath, null,
			// SQLiteDatabase.NO_LOCALIZED_COLLATORS |
			// SQLiteDatabase.OPEN_READWRITE);;

			/*
			 * for(Level l : levelOld){ if(l.getAvalible()){ unlockLevel(l, db);
			 * } }
			 */
			Log.d("UPGRADING OLD DATABASE2", "UPDATING from oldversion "
					+ oldVersion + " to " + newVersion);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unlockLevel(Level level, SQLiteDatabase db) {
		// SQLiteDatabase db = this.getReadableDatabase();
		try {

			if (db == null) {
				return;
			}
			// db.openDatabase(DB_PATH, null,
			// SQLiteDatabase.NO_LOCALIZED_COLLATORS |
			// SQLiteDatabase.OPEN_READONLY);

			String SQL = " update level set avalible = 1 where id = "
					+ level.getId() + " ";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

	}

	private ArrayList<Level> loadAllLevel(SQLiteDatabase db) {

		try {
			String SQL = " select l.id, l.name, l.avalible, l.stars, l.position, l.facebook from level l where state = 1 order by l.position ";

			if (db == null) {
				return null;
			}
			Cursor c = db.rawQuery(SQL, null);

			ArrayList<Level> levelList = new ArrayList<Level>();
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					Level level = new Level();

					level.setId(Long.valueOf(c.getString(0)));
					level.setName(c.getString(1));
					level.setAvalible((Integer.valueOf(c.getString(2)).equals(
							Integer.valueOf(1)) ? true : false));
					level.setStars(Integer.valueOf(c.getString(3)));
					level.setName(c.getString(4));
					level.setFacebook((Integer.valueOf(c.getString(5)).equals(
							Integer.valueOf(1)) ? true : false));

					levelList.add(level);
				} while (c.moveToNext());
			}

			//db.close();

			return levelList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		Log.d("Creating database", "Creating database");

		if (dbExist) {
			// Si existe, no hacemos nada!

		} else {
			// Llamando a este método se crea la base de datos vacía en la ruta
			// por defecto del sistema de nuestra aplicación por lo que
			// podremos sobreescribirla con nuestra base de datos.
			// this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copiando database");
			}
		}
	}

	public void overrideDATABASE() throws IOException {

		boolean dbExist = checkDataBase();

		// Llamando a este método se crea la base de datos vacía en la ruta
		// por defecto del sistema de nuestra aplicación por lo que
		// podremos sobreescribirla con nuestra base de datos.
		this.getReadableDatabase();

		try {

			copyDataBase();

		} catch (IOException e) {

			throw new Error("Error copiando database");
		}

	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			if(android.os.Build.VERSION.SDK_INT >= 17){
	            DB_PATH = SceneManagerSingleton.getInstance().getActivity().getApplicationInfo().dataDir + "/databases/";
	        
			}
	        else{
	            DB_PATH = "/data/data/" + SceneManagerSingleton.getInstance().getActivity().getPackageName() + "/databases/";
	        
	        }
			
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.NO_LOCALIZED_COLLATORS
							| SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// Base de datos no creada todavia

		}

		if (checkDB != null) {

			checkDB.close();
		}

		return checkDB != null ? true : false;

	}

	public void openDataBase() throws SQLException {

		// Open the database
		try {

			if(android.os.Build.VERSION.SDK_INT >= 17){
	            DB_PATH = SceneManagerSingleton.getInstance().getActivity().getApplicationInfo().dataDir + "/databases/";
	         
	            System.out.println("databse path >= 17 "+DB_PATH);
			}
	        else{
	            DB_PATH = "/data/data/" + SceneManagerSingleton.getInstance().getActivity().getPackageName() + "/databases/";
	         
	            System.out.println("databse path < 17 "+DB_PATH);
	        }
			
			
			String myPath = DB_PATH + DB_NAME;
			
			myDataBase = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.NO_LOCALIZED_COLLATORS
							| SQLiteDatabase.OPEN_READONLY);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();
	}

	private void copyDataBase() throws IOException {

		//SceneManagerSingleton.getInstance().getActivity().getDatabasePath(name)
		String dbPathAux = new String();
		if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = SceneManagerSingleton.getInstance().getActivity().getApplicationInfo().dataDir + "/databases/";
            dbPathAux = SceneManagerSingleton.getInstance().getActivity().getApplicationInfo().dataDir + "/databases";
            System.out.println("databse path >= 17 "+DB_PATH);
		}
        else{
            DB_PATH = "/data/data/" + SceneManagerSingleton.getInstance().getActivity().getPackageName() + "/databases/";
            dbPathAux = "/data/data/" + SceneManagerSingleton.getInstance().getActivity().getPackageName() + "/databases";
            System.out.println("databse path < 17 "+DB_PATH);
        }
		File databaseFile = new File( dbPathAux);
        // check if databases folder exists, if not create one and its subfolders
        if (!databaseFile.exists()){
            databaseFile.mkdir();
        }
	
		
            		//"/data/data/com.mgl.crappypigeon/databases/";
            
		OutputStream databaseOutputStream = new FileOutputStream("" + DB_PATH
				+ DB_NAME);
		InputStream databaseInputStream;

		byte[] buffer = new byte[1024];
		int length;

		databaseInputStream = myContext.getAssets().open("palomaDB");
		while ((length = databaseInputStream.read(buffer)) > 0) {
			databaseOutputStream.write(buffer);
		}

		databaseInputStream.close();
		databaseOutputStream.flush();
		databaseOutputStream.close();
	}

	public static String getDB_PATH() {
		return DB_PATH;
	}

	public static void setDB_PATH(String dB_PATH) {
		DB_PATH = dB_PATH;
	}

	public static String getDB_NAME() {
		return DB_NAME;
	}

	public static void setDB_NAME(String dB_NAME) {
		DB_NAME = dB_NAME;
	}

	public SQLiteDatabase getMyDataBase() {
		return myDataBase;
	}

	public void setMyDataBase(SQLiteDatabase myDataBase) {
		this.myDataBase = myDataBase;
	}

	public ArrayList<Level> getLeveList() {
		return leveList;
	}

	public void setLeveList(ArrayList<Level> leveList) {
		this.leveList = leveList;
	}

	public boolean isUpgrade() {
		return isUpgrade;
	}

	public void setUpgrade(boolean isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	public Context getMyContext() {
		return myContext;
	}

}