package com.mgl.drop.game.database;

import java.util.ArrayList;

import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.SpriteDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LevelDAO extends SQLiteOpenHelper {

	// String sqlCreate = "CREATE TABLE Level(id INTEGER, name TEXT)";

	
	public LevelDAO(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Se ejecuta la sentencia SQL de creación de la tabla
		// db.execSQL(sqlCreate);
		Log.d("DATABASE",
				"LA TABLA LEVEL SE INTENTA CREAR, MAS DEBE CARGARSE DEL ASSET");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versionA, int versionB) {
		// TODO
		// NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la
		// opción de
		// eliminar la tabla anterior y crearla de nuevo vacía con el nuevo
		// formato.
		// Sin embargo lo normal será que haya que migrar datos de la tabla
		// antigua
		// a la nueva, por lo que este método debería ser más elaborado.

		// Se elimina la versión anterior de la tabla
		//db.execSQL("DROP TABLE IF EXISTS Level");

		// Se crea la nueva versión de la tabla
		// db.execSQL(sqlCreate);

	}

	public ArrayList<Level> loadLevelList() {
		
		try {
			String SQL = " select l.id, l.name, l.avalible, l.stars, l.position, l.facebook from level l where state = 1 order by l.position ";
			SQLiteDatabase db = this.getReadableDatabase();
			if(db==null){
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			
			ArrayList<Level> levelList = new ArrayList<Level>();
			//Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			    	 Level level = new Level();
			    	 
			         level.setId(Long.valueOf(c.getString(0)));
			         level.setName(c.getString(1));
			         level.setAvalible((Integer.valueOf(c.getString(2)).equals(Integer.valueOf(1)) ? true : false));
			         try {
			        	 level.setStars(Integer.valueOf(c.getString(3)));
					} catch (Exception e) {
						 level.setStars(Integer.valueOf(0));
					}
			         level.setName(c.getString(4));
			         level.setFacebook((Integer.valueOf(c.getString(5)).equals(Integer.valueOf(1)) ? true : false));
			         
			         levelList.add(level);
			     } while(c.moveToNext());
			}
			
			db.close();
			
			return levelList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public ArrayList<SpriteDB> loadSpriteDBList(Long idLevel) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			Log.d("idLevel", "Objectlist "+idLevel);
			String SQL = " select l.id, l.idLevel, l.type, l.x, l.y,l.textureName ,l.data, l.myorder from sprite l where l.idlevel = "+idLevel+" order by l.myorder desc ";
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			
			if(db==null){
				return null;
			}
			
			ArrayList<SpriteDB> spriteList = new ArrayList<SpriteDB>();
			//Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			    	 SpriteDB sprite = new SpriteDB();
			    	 
			         sprite.setId(Long.valueOf(c.getString(0)));
			         sprite.setIdLevel(Long.valueOf(c.getString(1)));
			         sprite.setType(Long.valueOf(c.getString(2)));
			         sprite.setX(Long.valueOf(c.getString(3)));
			         sprite.setY(Long.valueOf(c.getString(4)));
			         sprite.setTextureName(c.getString(5));
			         sprite.setData(c.getString(6));
			         sprite.setOrder(Long.valueOf(c.getString(7)));
			         
			         spriteList.add(sprite);
			         
			     } while(c.moveToNext());
			}
			
			
			for(SpriteDB sp : spriteList){
				Log.d("id ", " "+sp.getId());
			}
			
			return spriteList;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			db.close();
		}
	}

	public void setStars(Long idLevel, int stars) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			
			
			String SQL = " update level set stars = "+stars+" where id = "+idLevel+ " ";
			//Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);
			
						
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		finally{
			db.close();
		}
	}

	public void setNextAvalible(Long idLevel) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			
			String SQL = " update level set avalible = 1 where id = "+idLevel+ " ";
			//Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);
			
						
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		finally{
			db.close();
		}
	}

	public void unlockLevel(Level level) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			
			String SQL = " update level set avalible = 1 where id = "+level.getId()+ " ";
			//Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);
			
						
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		finally{
			db.close();
		}
		
	}

	public Level loadLevelById(Long idLevel) {

		try {
			String SQL = " select l.id, l.name, l.avalible, l.stars, l.position, l.helppost, l.facebook from level l where state = 1 and l.id = "+ idLevel +" order by l.position ";
			SQLiteDatabase db = this.getReadableDatabase();
			if(db==null){
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			
			ArrayList<Level> levelList = new ArrayList<Level>();
			//Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
			     //Recorremos el cursor hasta que no haya más registros
			     do {
			    	 Level level = new Level();
			    	 
			         level.setId(Long.valueOf(c.getString(0)));
			         level.setName(c.getString(1));
			         level.setAvalible((Integer.valueOf(c.getString(2)).equals(Integer.valueOf(1)) ? true : false));
			         level.setStars(Integer.valueOf(c.getString(3)));
			         level.setName(c.getString(4));
			         level.setHelpInfo(Long.valueOf(c.getString(5)));
			         level.setFacebook((Integer.valueOf(c.getString(6)).equals(Integer.valueOf(1)) ? true : false));
			         
			         return level;
			         //levelList.add(level);
			         
			     } while(c.moveToNext());
			}
			
			db.close();
			
			//return levelList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
	}

	
	
}
