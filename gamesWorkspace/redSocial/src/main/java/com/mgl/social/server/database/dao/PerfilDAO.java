package com.mgl.social.server.database.dao;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Query;

import com.mgl.social.server.database.HashTag;
import com.mgl.social.server.database.constant.CUpdateType;
import com.mgl.social.server.message.send.obj.ObjPerfil;
import com.mgl.social.server.message.send.obj.ObjViewUserGeneral;
import com.mgl.social.server.util.ManageDate;

public class PerfilDAO extends BaseDAO {

	public ObjPerfil loadPerfilInfo(Long idUser, Long myId) {

		StringBuilder hql = null;
		Query qo = null;
		try {
			hql = new StringBuilder();
			hql.append("SELECT user.id as id, user.name as name, user.lastName as lastName, user.description as description, user.email as email, photo.photo as photo ");
			hql.append(",(select count(follower.id) from t_friend follower where follower.idFollowing=:myId and follower.idFollowed=user.id )");
			hql.append(",(select count(follower.id) from t_friend follower where follower.idFollowing=:myId )");
			hql.append(",(select count(follower.id) from t_friend follower where follower.idFollowed=:myId )");

			hql.append("FROM t_user user ");
			hql.append(" LEFT JOIN t_photo photo ON user.idPhoto = photo.id");
			hql.append(" WHERE user.id =:idUser ");

			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("idUser", idUser);
			qo.setParameter("myId", myId);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();
			System.out.println("LISTA SIZA" + objList.size());

			for (Object[] obj : objList) {
				ObjPerfil perfil = new ObjPerfil();
				perfil.setIdUser(Long.valueOf(String.valueOf(obj[0])));
				perfil.setName(String.valueOf(obj[1]));
				perfil.setLastName(String.valueOf(obj[2]));
				perfil.setDescription(String.valueOf(obj[3]));
				perfil.setEmail(String.valueOf(obj[4]));
				//String photoStr = loadPhotoFromDisk(String.valueOf(obj[5]));
				perfil.setPhoto(String.valueOf(obj[5]));
				if (String.valueOf(obj[6]).equals("0")) {
					perfil.setFollow(false);
					System.out.println("not following");
				} else {
					perfil.setFollow(true);
					System.out.println("following");
				}

				perfil.setFollowing(String.valueOf(obj[7]));
				perfil.setFollowed(String.valueOf(obj[8]));

				if (perfil.getIdUser().equals(myId)) {
					perfil.setMe(true);
				} else {
					perfil.setMe(false);
				}

				return perfil;
			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public ArrayList<ObjViewUserGeneral> loadGeneralPerfilInfo(String idType,
			Long typeUpdate, Long type, Long idUser, Long limit, Long multiplier) {

		StringBuilder hql = null;
		Query qo = null;
		try {

			Long limitMin = limit;
			limit = limit * multiplier;
			limitMin = limit - limitMin;
			// TODO Optimize this when i recive the top date
			limitMin = 0L;

			hql = new StringBuilder();
			hql.append(" SELECT user.id as id, user.name as name, user.lastName as lastName, photo.photo as photo, lk.id as lfId, data.id as dataId");
			if (type.equals(CUpdateType.COMMENT)) {
				hql.append(", lk.comment ");
			}

			hql.append(" ,(select count(follower.id) from t_friend follower where follower.idFollowing=:idUser and follower.idFollowed=user.id )");

			hql.append(" FROM t_user user ");
			hql.append(" LEFT JOIN t_photo photo ON user.idPhoto = photo.id,");
			if (typeUpdate.equals(CUpdateType.UPDATE)) {
				hql.append(" t_update data, ");
			} else if (typeUpdate.equals(CUpdateType.PHOTO)) {
				hql.append(" t_photo data, ");
			} else if (typeUpdate.equals(CUpdateType.VIDEO)) {
				hql.append(" t_video data, ");
			} else if (typeUpdate.equals(CUpdateType.POST)) {
				hql.append(" t_post data, ");
			} else {
				return null;
			}

			if (type.equals(CUpdateType.LIKE)) {
				hql.append(" t_like lk ");
			} else if (type.equals(CUpdateType.COMMENT)) {
				hql.append(" t_comment lk ");
			} else if (type.equals(CUpdateType.SHARE)) {
				hql.append(" t_share lk ");
			} else {
				return null;
			}

			hql.append(" WHERE data.id = :idType "
					+ " AND lk.idUser = user.id AND lk.idUpdate = data.id  LIMIT :MINLIM,:MAXLIM ");

			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("idType", idType);
			qo.setParameter("idUser", idUser);

			qo.setParameter("MAXLIM", limit);
			qo.setParameter("MINLIM", limitMin);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();

			ArrayList<ObjViewUserGeneral> returnList = new ArrayList<ObjViewUserGeneral>();

			for (Object[] obj : objList) {
				ObjViewUserGeneral user = new ObjViewUserGeneral();
				user.setIdUser(Long.valueOf(String.valueOf(obj[0])));
				user.setName(String.valueOf(obj[1]) + " "
						+ String.valueOf(obj[2]));
				//String photoStr = loadPhotoFromDisk(String.valueOf(obj[3]));
				
				user.setPhoto(String.valueOf(obj[3]));
				
				user.setIdType(Long.valueOf(String.valueOf(obj[4])));
				user.setId(Long.valueOf(String.valueOf(obj[5])));
				int pos = 6;
				if (type.equals(CUpdateType.COMMENT)) {
					user.setComment(String.valueOf(obj[6]));
					pos = 7;
				}
				if (user.getIdUser().equals(idUser)) {
					System.out.println("true");
					user.setMe(true);
				} else {
					System.out.println("false");
					user.setMe(false);
				}
				if (String.valueOf(obj[pos]).equals("0")) {
					user.setFollowing(false);
					System.out.println("not following");
				} else {
					user.setFollowing(true);
					System.out.println("following");
				}
				user.setType(type);
				user.setTypeUpdate(typeUpdate);
				returnList.add(user);
			}
			System.out.println("el tam de la lista es : " + returnList.size());
			return returnList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public ArrayList<ObjViewUserGeneral> loadSuggestionList(Long idUser,
			Long limitSugesstion, Long multiplierSugesstion) {

		StringBuilder hql = null;
		Query qo = null;
		try {
			hql = new StringBuilder();
			hql.append("select usr.id, usr.name,usr.lastName, photo.photo, count(usr.id) from t_friend ff, t_user usr LEFT JOIN t_photo photo ON usr.idPhoto = photo.id where ff.idFollowing in (select usr.id from t_user usr, t_friend f where f.idFollowing = 58 and usr.id = f.idFollowed) and usr.id = ff.idFollowed and ff.idFollowed not in(select usr.id from t_user usr, t_friend f where f.idFollowing = :idUser and usr.id = f.idFollowed) and usr.id!=:idUser group by usr.id order by count(usr.id) desc");

			qo = getSession().createSQLQuery(hql.toString());

			qo.setParameter("idUser", idUser);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();

			ArrayList<ObjViewUserGeneral> returnList = new ArrayList<ObjViewUserGeneral>();

			for (Object[] obj : objList) {
				ObjViewUserGeneral user = new ObjViewUserGeneral();
				user.setIdUser(Long.valueOf(String.valueOf(obj[0])));
				user.setName(String.valueOf(obj[1]) + " "
						+ String.valueOf(obj[2]));
				//String photoStr = loadPhotoFromDisk(String.valueOf(obj[3]));
				user.setPhoto(String.valueOf(obj[3]));

				user.setFollowing(false);

				returnList.add(user);
			}
			System.out.println("el tam de la lista es de sugerencias es: "
					+ returnList.size());
			return returnList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public ArrayList<HashTag> loadHashTaglist(Long idUser, Long limitHashTag,
			Long multiplierHashTag, Date date) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			String dateAux = ManageDate.formatDate(date, ManageDate.YYYY_MM_DD);
			dateAux = dateAux.replace("/", "-");

			hql = new StringBuilder();
			hql.append("select hash.id, hash.name, upd.date from t_update upd, t_hashtag hash where (upd.idUser in (select usr.id from t_user usr, t_friend f where f.idFollowing = :idUser and usr.id = f.idFollowed) or upd.idUser =:idUser )and upd.id = hash.idUpdate and upd.date >= :DATE group by hash.name order by count(hash.name) desc LIMIT :MINLIM,:MAXLIM ");

			qo = getSession().createSQLQuery(hql.toString());

			qo.setParameter("idUser", idUser);
			qo.setParameter("DATE", dateAux);
			qo.setParameter("MINLIM", 0);
			qo.setParameter("MAXLIM", 6);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();

			ArrayList<HashTag> returnList = new ArrayList<HashTag>();

			for (Object[] obj : objList) {
				HashTag hash = new HashTag();
				hash.setId(Long.valueOf(String.valueOf(obj[0])));
				hash.setName(String.valueOf(obj[1]));
				returnList.add(hash);
			}
			System.out.println("el tam de la lista es de hash es: "
					+ returnList.size());
			return returnList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public ArrayList<HashTag> loadHashTaglistCountry(Long idCountry,
			Long limitHastag, Long multiplierHastag, Date date) {
		StringBuilder hql = null;
		Query qo = null;
		try {

			String dateAux = ManageDate.formatDate(date, ManageDate.YYYY_MM_DD);
			dateAux = dateAux.replace("/", "-");

			hql = new StringBuilder();
			hql.append("select hash.id, hash.name, upd.date from t_update upd, t_hashtag hash where upd.idUser in (select usr.id from t_user usr where usr.idCountry = :idCountry and upd.id = hash.idUpdate and upd.date >= :DATE) group by hash.name order by count(hash.name) desc LIMIT :MINLIM,:MAXLIM ");

			qo = getSession().createSQLQuery(hql.toString());

			qo.setParameter("idCountry", idCountry);
			qo.setParameter("DATE", dateAux);
			qo.setParameter("MINLIM", 0);
			qo.setParameter("MAXLIM", 6);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();

			ArrayList<HashTag> returnList = new ArrayList<HashTag>();

			for (Object[] obj : objList) {
				HashTag hash = new HashTag();
				hash.setId(Long.valueOf(String.valueOf(obj[0])));
				hash.setName(String.valueOf(obj[1]));
				returnList.add(hash);
			}
			System.out.println("el tam de la lista es de hashCountry es: "
					+ returnList.size());
			return returnList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

}
