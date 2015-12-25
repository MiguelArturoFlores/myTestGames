package com.mgl.drop.game.database;

import java.util.ArrayList;










import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mgl.base.MyObjectGeneral;
import com.mgl.base.userinfo.UserInfo;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.PlayerDB;
import com.mgl.drop.game.database.model.Score;
import com.mgl.drop.game.database.model.SpriteDB;
import com.mgl.drop.game.database.model.Trophy;

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
		// db.execSQL("DROP TABLE IF EXISTS Level");

		// Se crea la nueva versión de la tabla
		// db.execSQL(sqlCreate);

	}

	public ArrayList<Level> loadLevelList() {

		try {
			String SQL = " select l.id, l.name, l.avalible, l.stars, l.position, l.facebook, l.minstar,l.red,l.green,l.blue,l.percentage from level l where state = 1 order by l.position ";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<Level> levelList = new ArrayList<Level>();
			Level level = new Level();
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					level = new Level();

					level.setId(Long.valueOf(c.getString(0)));
					level.setName(c.getString(1));
					level.setAvalible((Integer.valueOf(c.getString(2)).equals(
							Integer.valueOf(1)) ? true : false));
					try {
						level.setStars(Integer.valueOf(c.getString(3)));
					} catch (Exception e) {
						level.setStars(Integer.valueOf(0));
					}
					level.setPosition(Integer.valueOf(c.getString(4)));
					level.setFacebook((Integer.valueOf(c.getString(5)).equals(
							Integer.valueOf(1)) ? true : false));

					level.setMinStar((Integer.valueOf(c.getString(6))));
					level.setRed((Float.valueOf(c.getString(7))));
					level.setGreen((Float.valueOf(c.getString(8))));
					level.setBlue((Float.valueOf(c.getString(9))));
					
					level.setPercentage((Float.valueOf(c.getString(10))));
					
					levelList.add(level);
				} while (c.moveToNext());
				
				level.setLast(true);
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
			Log.d("idLevel", "Objectlist " + idLevel);
			String SQL = " select l.id, l.idLevel, l.type, l.x, l.y,l.textureName ,l.data, l.myorder from sprite l where l.idlevel = "
					+ idLevel + " order by l.myorder desc ";
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			if (db == null) {
				return null;
			}

			ArrayList<SpriteDB> spriteList = new ArrayList<SpriteDB>();
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
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

				} while (c.moveToNext());
			}

			for (SpriteDB sp : spriteList) {
				Log.d("id ", " " + sp.getId());
			}

			return spriteList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			db.close();
		}
	}

	public void setStars(Long idLevel, int stars) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update level set stars = " + stars + " where id = "
					+ idLevel + " ";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
	}

	public void setNextAvalible(Long idLevel) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update level set avalible = 1 where id = " + idLevel
					+ " ";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
	}

	public void unlockLevel(Level level) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {

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

	public Level loadLevelById(Long idLevel) {

		try {
			String SQL = " select l.id, l.name, l.avalible, l.stars, l.position, l.helppost, l.facebook, l.minstar,l.red,l.green,l.blue,l.percentage from level l where state = 1 and l.id = "
					+ idLevel + " order by l.position ";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

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
					level.setPosition(Integer.valueOf(c.getString(4)));
					level.setHelpInfo(Long.valueOf(c.getString(5)));
					level.setFacebook((Integer.valueOf(c.getString(6)).equals(
							Integer.valueOf(1)) ? true : false));
					
					level.setMinStar((Integer.valueOf(c.getString(7))));

					level.setRed((Float.valueOf(c.getString(8))));
					level.setGreen((Float.valueOf(c.getString(9))));
					level.setBlue((Float.valueOf(c.getString(10))));
					level.setPercentage((Float.valueOf(c.getString(11))));
					return level;
					// levelList.add(level);

				} while (c.moveToNext());
			}

			db.close();

			// return levelList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	
	public UserInfo loadUserInfo() {

		try {
			String SQL = " select u.powera, u.powerb, u.powerc, u.powerd, u.money, u.publicity, u.lastDate ,u.contBasic ,u.contBat, u.contHole, u.contArmor, u.contZigZag, u.hasrate, u.haslike, u.hasbuy, u.contpowera, u.contpowerb, u.contpowerc, u.contpowerd, u.playerselected, u.lastDateWhatsapp, u.email, u.hasSendMail, u.hasSendTwitter, u.hasSendYoutube "
					+ " , u.lastDateFacebook, u.language, u.contShareWhatsapp,u.contShareFacebook, u.contShareTwitter, u.starRating, u.youtubeToken, u.sendInfo, u.currentLevel from userinfo u ";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<UserInfo> userInfoList = new ArrayList<UserInfo>();
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {

					UserInfo userInfo = new UserInfo();

					userInfo.setPowerA(Integer.valueOf(c.getString(0)));
					userInfo.setPowerB(Integer.valueOf(c.getString(1)));
					userInfo.setPowerC(Integer.valueOf(c.getString(2)));
					userInfo.setPowerD(Integer.valueOf(c.getString(3)));

					userInfo.setMoney(Integer.valueOf(c.getString(4)));
					userInfo.setPublicity(Integer.valueOf(c.getString(5)));
					userInfo.setDate((c.getString(6)));

					userInfo.setContBasic(Integer.valueOf(c.getString(7)));
					userInfo.setContBat(Integer.valueOf(c.getString(8)));
					userInfo.setContHole(Integer.valueOf(c.getString(9)));
					userInfo.setContArmor(Integer.valueOf(c.getString(10)));
					userInfo.setContZigZag(Integer.valueOf(c.getString(11)));
					userInfo.setHasRate((Integer.valueOf(c.getString(12)) == 0 ? false
							: true));
					userInfo.setHasLike((Integer.valueOf(c.getString(13)) == 0 ? false
							: true));
					userInfo.setHasBuy((Integer.valueOf(c.getString(14)) == 0 ? false
							: true));
					
					userInfo.setContPowerA(Integer.valueOf(c.getString(15)));
					userInfo.setContPowerB(Integer.valueOf(c.getString(16)));
					userInfo.setContPowerC(Integer.valueOf(c.getString(17)));
					userInfo.setContPowerD(Integer.valueOf(c.getString(18)));
					userInfo.setPlayerSelect(Integer.valueOf(c.getString(19)));
					
					userInfo.setDateWhatsapp((c.getString(20)));
					userInfo.setEmail(c.getString(21));

					userInfo.setHasSendMail((Integer.valueOf(c.getString(22)) == 0 ? false
							: true));
					userInfo.setHasSendTwitter((Integer.valueOf(c.getString(23)) == 0 ? false
							: true));
					userInfo.setHasSendYoutube((Integer.valueOf(c.getString(24)) == 0 ? false
							: true));

					userInfo.setDateFacebook(c.getString(25));
					userInfo.setLanguage(c.getString(26));
					userInfo.setContShareWhatsapp(Integer.valueOf(c.getString(27)));
					userInfo.setContShareFacebook(Integer.valueOf(c.getString(28)));
					userInfo.setContShareTwitter(Integer.valueOf(c.getString(29)));
					userInfo.setStarRating(Integer.valueOf(c.getString(30)));
					userInfo.setYoutubeToken(c.getString(31));
					userInfo.setSendInfo((Integer.valueOf(c.getString(32)) == 0 ? false
							: true));
					userInfo.setCurrentLevel(Integer.valueOf(c.getString(33)));
					
					return userInfo;

				} while (c.moveToNext());
			}

			db.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public void increasePower(String attUpdate, int value) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			String SQL = " update userinfo set " + attUpdate + " = " + value
					+ " ";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

	}

	public void updateUserInfo(String attUpdate, int value) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			String SQL = " update userinfo set " + attUpdate + " = " + value
					+ " ";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

	}
	
	public void lastLogin(String date) {

		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update userinfo set lastDate = '" + date+"'";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

	}

	public ArrayList<Score> loadScoreList() {

		try {
			String SQL = " select s.score from Score s order by s.score desc ";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<Score> scoreList = new ArrayList<Score>();
			int i = 1;
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					Score score = new Score();

					score.setScore(Long.valueOf(c.getString(0)));
					score.setPosition(Long.valueOf(i));
					i++;
					scoreList.add(score);
				} while (c.moveToNext());
			}

			db.close();
			return scoreList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void newScore(ArrayList<Score> scoreList) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " delete from score ";
			db.execSQL(SQL);

			for (Score s : scoreList) {
				SQL = " insert into score values (" + s.getScore().intValue()
						+ ") ";
				db.execSQL(SQL);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

	}

	public ArrayList<Trophy> loadTrophyList() {
		try {
			String SQL = " select s.id, s.name, s.unlock, s.textShow, s.texture from Trophy s";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<Trophy> trophyList = new ArrayList<Trophy>();
			int i = 1;
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					Trophy trophy = new Trophy();

					trophy.setId(Integer.valueOf(c.getString(0)));
					trophy.setName(c.getString(1));
					trophy.setUnlock(Integer.valueOf(c.getString(2)) == 1 ? true
							: false);
					trophy.setPosition(Long.valueOf(i));
					trophy.setTextName(c.getString(3));
					trophy.setTextureName(c.getString(4));
					i++;
					trophyList.add(trophy);
				} while (c.moveToNext());
			}

			db.close();
			return trophyList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void unlockTrophy(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update trophy set unlock = 1 where id = " + id + " ";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
	}

	public void changeUserInfo(String attUpdate, String value) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update userinfo set " + attUpdate + " = " + value
					+ " ";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

	}

	public int loadStars() {
		try {
			String SQL = " select l.stars from Level l ";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return 0;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<Score> scoreList = new ArrayList<Score>();
			int i = 1;
			// Nos aseguramos de que existe al menos un registro
			int starsCount = 0;
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					starsCount = starsCount + Integer.valueOf(c.getString(0));
					i++;
				} while (c.moveToNext());
			}

			db.close();
			return starsCount;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public ArrayList<PlayerDB> loadPlayerList() {
	
		try {
			String SQL = " select p.id, p.name, p.likesQuantity, p.stars, p.texture from Player p order by p.likesQuantity desc ";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<PlayerDB> playerList = new ArrayList<PlayerDB>();
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					PlayerDB player = new PlayerDB();

					player.setId(Integer.valueOf(c.getString(0)));
					player.setName(c.getString(1));
					player.setLikesQuantity((Integer.valueOf(c.getString(2))));
					player.setStars((Integer.valueOf(c.getString(3))));
					player.setTexture(((c.getString(4))));
					
					playerList.add(player);
				} while (c.moveToNext());
			}

			db.close();

			return playerList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public PlayerDB loadPlayerById(int playerSelect) {
		try {
			String SQL = " select p.id, p.name, p.likesQuantity, p.stars, p.texture from Player p where p.id = "+playerSelect;
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<PlayerDB> playerList = new ArrayList<PlayerDB>();
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					PlayerDB player = new PlayerDB();

					player.setId(Integer.valueOf(c.getString(0)));
					player.setName(c.getString(1));
					player.setLikesQuantity((Integer.valueOf(c.getString(2))));
					player.setStars((Integer.valueOf(c.getString(3))));
					player.setTexture(((c.getString(4))));
					
					return player;
				} while (c.moveToNext());
			}

			db.close();

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void updatePlayerDB(Integer id, int likesQuantity) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			String SQL = " update Player set  likesQuantity  = " + likesQuantity+" where id = "+id;
					
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
	}

	public void setLevelStar(Long idLevel, int starNumber) {
		try {
			
			SQLiteDatabase db = this.getReadableDatabase();
			try {
				String SQL = " update level set  minstar = " + starNumber+" where id = "+idLevel;
						
				db.execSQL(SQL);

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				db.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPercentage(Long idLevel, float percentage) {
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			try {
				String SQL = " update level set  percentage = " + percentage+" where id = "+idLevel;
						
				db.execSQL(SQL);

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void lastWhatsappShare(String date) {
	
		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update userinfo set lastDateWhatsapp = '" + date+"'";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
		
	}

	public void changeEmail(String string, String email) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			
			String SQL = " update userinfo set email = '" + email+"'";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
	}

//	public void lastLoginWhatsapp(String date) {
//		SQLiteDatabase db = this.getReadableDatabase();
//		try {
//
//			String SQL = " update userinfo set lastDateWhatsapp = '" + date+"'";
//			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
//			db.execSQL(SQL);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		} finally {
//			db.close();
//		}
//	}
	
	public void lastFacebookShare(String date) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update userinfo set lastDateFacebook = '" + date+"'";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

	}

	public int getTotalLike() {
		
		try {
			String SQL = " select p.likesQuantity from Player p ";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return 0;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			// Nos aseguramos de que existe al menos un registro
			int starsCount = 0;
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				do {
					starsCount = starsCount + Integer.valueOf(c.getString(0));
				} while (c.moveToNext());
			}

			db.close();
			return starsCount;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		
	}

}
