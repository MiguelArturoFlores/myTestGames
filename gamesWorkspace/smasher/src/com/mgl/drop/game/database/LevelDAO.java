package com.mgl.drop.game.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mgl.base.MyEntitySmasher;
import com.mgl.base.userinfo.UserInfo;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.Score;
import com.mgl.drop.game.database.model.SpriteDB;
import com.mgl.drop.game.database.model.Trophy;
import com.mgl.drop.game.entity.Behavior;
import com.mgl.drop.game.entity.Wave;

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
			String SQL = " select l.id, l.name, l.avalible, l.stars, l.position, l.facebook from level l where state = 1 order by l.position ";
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
					try {
						level.setStars(Integer.valueOf(c.getString(3)));
					} catch (Exception e) {
						level.setStars(Integer.valueOf(0));
					}
					level.setName(c.getString(4));
					level.setFacebook((Integer.valueOf(c.getString(5)).equals(
							Integer.valueOf(1)) ? true : false));

					levelList.add(level);
				} while (c.moveToNext());
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
			String SQL = " select l.id, l.name, l.avalible, l.stars, l.position, l.helppost, l.facebook from level l where state = 1 and l.id = "
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
					level.setName(c.getString(4));
					level.setHelpInfo(Long.valueOf(c.getString(5)));
					level.setFacebook((Integer.valueOf(c.getString(6)).equals(
							Integer.valueOf(1)) ? true : false));

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

	public ArrayList<Wave> loadWaveList(Long idLevel) {
		try {

			if (idLevel >= 3) {
				// return Wave.generateWaveByLevelBETA(idLevel);
				return Wave.generateWaveByLevel(idLevel);
			}

			String SQL = "select lw.id , b.id, lw.begintime, wb.speed, wb.begintime, wb.behaviordata, wb.behaviortype, wb.monstertype, wb.probabilityHuman, wb.directionX  from levelwave lw, wavebehavior wb, behavior b where lw.idlevel = "
					+ idLevel
					+ " and lw.idWave = wb.idwave and wb.idbehavior = b.id order by lw.begintime ";

			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<Wave> waveList = new ArrayList<Wave>();
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				Wave wave = null;
				do {

					if (wave == null) {
						wave = new Wave();
						wave.setId(Long.valueOf(c.getString(0)));
						wave.setBeginTime(Long.valueOf(c.getString(2)));
					} else {
						// wave= waveList.get(waveList.size()-1);
					}

					if (!Long.valueOf(c.getString(0)).equals(wave.getId())) {
						wave.generateSmashSprites();
						waveList.add(wave);
						wave = new Wave();
						wave.setId(Long.valueOf(c.getString(0)));
						wave.setBeginTime(Long.valueOf(c.getString(2)));
					}

					Behavior behavior = new Behavior();
					behavior.setId(Integer.valueOf(c.getString(1)));
					behavior.setSpeed(Integer.valueOf(c.getString(3)));
					behavior.setBeginTime(Float.valueOf(c.getString(4)));
					behavior.setIdLevel(idLevel.intValue());
					behavior.setData(c.getString(5));
					behavior.setBehaviorType(Integer.valueOf(c.getString(6)));
					behavior.setMonsterType(c.getString(7));
					behavior.setProbabilityHuman(Integer.valueOf(c.getString(8)));
					behavior.setSpeedX(Integer.valueOf(c.getString(9)));

					wave.getBehaviorList().add(behavior);

				} while (c.moveToNext());

				if (wave != null) {
					wave.generateSmashSprites();
					waveList.add(wave);
				}
			}

			db.close();

			return waveList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<Wave> loadWaveListBySprite(Long idLevel) {
		try {
			// String SQL =
			// " select sw.idwave, sw.x, sw.y, spr.type, sw.begintime, lw.begintime  from levelwave lw, wave w, spritewave sw left join sprite spr on sw.idsprite = spr.id  where lw.idlevel = "+idLevel+" and sw.idWave = w.id and w.id = lw.idwave order by lw.begintime ";
			String SQL = " select wb.idwave, sb.x, sb.y, spr.type, sb.begintime, lw.begintime, sb.speed, wb.begintime from levelwave lw, wave w, wavebehavior wb, behavior b, spritebehavior sb left join sprite spr on sb.idsprite = spr.id where lw.idlevel = "
					+ idLevel
					+ " and lw.idwave = w.id and w.id = wb.idwave and wb.idbehavior = b.id and sb.idBehavior = b.id order by lw.begintime ";
			SQLiteDatabase db = this.getReadableDatabase();
			if (db == null) {
				return null;
			}
			Cursor c = this.getReadableDatabase().rawQuery(SQL, null);

			ArrayList<Wave> waveList = new ArrayList<Wave>();
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya más registros
				Wave wave = null;
				do {

					if (wave == null) {
						wave = new Wave();
						wave.setId(Long.valueOf(c.getString(0)));
						wave.setBeginTime(Long.valueOf(c.getString(5)));
					} else {
						// wave= waveList.get(waveList.size()-1);
					}

					if (!Long.valueOf(c.getString(0)).equals(wave.getId())) {
						waveList.add(wave);
						wave = new Wave();
						wave.setId(Long.valueOf(c.getString(0)));
						wave.setBeginTime(Long.valueOf(c.getString(5)));
					}

					MyEntitySmasher smasher = new MyEntitySmasher();
					smasher.setActive(false);

					smasher.setX(Float.valueOf(c.getString(1)));
					smasher.setY(Float.valueOf(c.getString(2)));
					smasher.setIdSprite(Integer.valueOf(c.getString(3)));
					smasher.setTime(Float.valueOf(c.getString(4))
							+ Float.valueOf(c.getString(7)));
					smasher.setSpeed(Integer.valueOf(c.getString(6)));
					smasher.setWidth(20);
					smasher.setHeight(60);

					wave.getSpriteList().add(smasher);

				} while (c.moveToNext());

				if (wave != null) {
					waveList.add(wave);
				}
			}

			db.close();

			return waveList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public UserInfo loadUserInfo() {

		try {
			String SQL = " select u.powera, u.powerb, u.powerc, u.powerd, u.money, u.publicity, u.lastDate ,u.contBasic ,u.contBat, u.contHole, u.contArmor, u.contZigZag, u.hasrate, u.haslike, u.hasbuy, u.contpowera, u.contpowerb, u.contpowerc, u.contpowerd, u.lastDateWhatsapp, u.email, u.hasSendMail, u.hasSendTwitter, u.hasSendYoutube "
					+ " , u.lastDateFacebook, u.language, u.contShareWhatsapp,u.contShareFacebook, u.contShareTwitter, u.starRating, u.youtubeToken, u.sendInfo from userinfo u ";
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

					userInfo.setDateWhatsapp((c.getString(19)));
					userInfo.setEmail(c.getString(20));

					userInfo.setHasSendMail((Integer.valueOf(c.getString(21)) == 0 ? false
							: true));
					userInfo.setHasSendTwitter((Integer.valueOf(c.getString(22)) == 0 ? false
							: true));
					userInfo.setHasSendYoutube((Integer.valueOf(c.getString(23)) == 0 ? false
							: true));

					userInfo.setDateFacebook(c.getString(24));
					userInfo.setLanguage(c.getString(25));
					userInfo.setContShareWhatsapp(Integer.valueOf(c
							.getString(26)));
					userInfo.setContShareFacebook(Integer.valueOf(c
							.getString(27)));
					userInfo.setContShareTwitter(Integer.valueOf(c
							.getString(28)));
					userInfo.setStarRating(Integer.valueOf(c.getString(29)));
					userInfo.setYoutubeToken(c.getString(30));
					userInfo.setSendInfo((Integer.valueOf(c.getString(31)) == 0 ? false
							: true));

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

	public void lastLogin(String date) {

		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update userinfo set lastDate = '" + date + "'";
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

	public void lastWhatsappShare(String date) {

		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update userinfo set lastDateWhatsapp = '" + date
					+ "'";
			// Cursor c = this.getReadableDatabase().rawQuery(SQL, null);
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}

	}

	public void lastFacebookShare(String date) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {

			String SQL = " update userinfo set lastDateFacebook = '" + date
					+ "'";
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

	public void updateDatabaseUserInfo(UserInfo u) {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			
			String SQL = " update userinfo set publicity = " +u.getPublicity();
			SQL = SQL + " , money= " +u.getMoney();
			SQL = SQL + " , powera= " +u.getPowerA();
			SQL = SQL + " , powerb= " +u.getPowerB();
			SQL = SQL + " , powerc= " +u.getPowerC();
			SQL = SQL + " , powerd= " +u.getPowerD();
			SQL = SQL + " , contBat= " +u.getContBat();
			SQL = SQL + " , contArmor= " +u.getContArmor();
			SQL = SQL + " , contZigZag= " +u.getContZigZag();
			SQL = SQL + " , contBasic= " +u.getContBasic();
			SQL = SQL + " , contPowerA= " +u.getContPowerA();
			SQL = SQL + " , contPowerB= " +u.getContPowerB();
			SQL = SQL + " , contPowerC= " +u.getContPowerC();
			SQL = SQL + " , contPowerD= " +u.getContPowerD();
			
			db.execSQL(SQL);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			db.close();
		}
	}

}
