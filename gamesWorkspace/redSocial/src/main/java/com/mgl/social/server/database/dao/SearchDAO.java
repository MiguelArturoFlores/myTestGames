package com.mgl.social.server.database.dao;

import java.util.ArrayList;

import org.hibernate.Query;

import com.mgl.social.server.message.send.obj.ObjSearch;

public class SearchDAO extends BaseDAO {

	public ArrayList<ObjSearch> loadGeneralSearch(String text, Long idUser, Long limit, Long multiplier) {

		StringBuilder hql = null;
		Query qo = null;
		try {
			
			Long limitMin = limit;
			limit = limit * multiplier;
			limitMin = limit - limitMin;
			// TODO Optimize this when i recive the top date
			limitMin = 0L;
			
			// text = "'%"+text+"%'";
			System.out.println("TEXT: " + text);
			hql = new StringBuilder();
			hql.append(" SELECT user.id as id, user.name as name, user.lastName as lastName, user.description as description, photo.photo as photo ");
			hql.append(" ,(select count(follower.id) from t_friend follower where follower.idFollowing=:idUser and follower.idFollowed=user.id )");
			hql.append(" FROM t_user user left join t_photo photo on ( photo.id=user.idPhoto ) WHERE  user.id !=:idUser and (user.name like '%"
					+ text + "%' or user.lastName like '%" + text + "%') LIMIT :MINLIM,:MAXLIM ");

			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("idUser", idUser);
			qo.setParameter("MAXLIM", limit);
			qo.setParameter("MINLIM", limitMin);
			// qo.setParameter("text", text);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();
			System.out.println("LISTA SIZA" + objList.size());
			ArrayList<ObjSearch> searchList = new ArrayList<ObjSearch>();
			for (Object[] obj : objList) {
				ObjSearch objSearch = new ObjSearch();
				objSearch.setIdUser(String.valueOf(obj[0]));
				objSearch.setName(String.valueOf(obj[1]));
				objSearch.setLastName(String.valueOf(obj[2]));
				objSearch.setDescription(String.valueOf(obj[3]));
				
				//String photoStr = loadPhotoFromDisk(String.valueOf(obj[4]));
				objSearch.setPhoto(String.valueOf(obj[4]));
				if (String.valueOf(obj[5]).equals("0")) {
					objSearch.setFollowing(false);
					System.out.println("not following");
				} else {
					objSearch.setFollowing(true);
					System.out.println("following");
				}
				searchList.add(objSearch);
			}

			return searchList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public ArrayList<ObjSearch> loadPersoneSerach(String name, String lastName,
			String mail, Long idUser) {

		StringBuilder hql = null;
		Query qo = null;
		try {
			// text = "'%"+text+"%'";
			
			hql = new StringBuilder();
			hql.append(" SELECT user.id as id, user.name as name, user.lastName as lastName, user.description as description, user.photo as photo ");
			hql.append(" ,(select count(follower.id) from t_friend follower where follower.idFollowing=:idUser and follower.idFollowed=user.id )");
			hql.append(" FROM t_user user WHERE  user.id !=:idUser and (user.name like '%"
					+ name + "%' or user.lastName like '%" + lastName + "%' or user.email like '%" + lastName + "%' ) ");

			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("idUser", idUser);
			// qo.setParameter("text", text);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();
			System.out.println("LISTA SIZA" + objList.size());
			ArrayList<ObjSearch> searchList = new ArrayList<ObjSearch>();
			for (Object[] obj : objList) {
				ObjSearch objSearch = new ObjSearch();
				objSearch.setIdUser(String.valueOf(obj[0]));
				objSearch.setName(String.valueOf(obj[1]));
				objSearch.setLastName(String.valueOf(obj[2]));
				objSearch.setDescription(String.valueOf(obj[3]));
				String photoStr = loadPhotoFromDisk(String.valueOf(obj[4]));
				objSearch.setPhoto(photoStr);
				if (String.valueOf(obj[5]).equals("0")) {
					objSearch.setFollowing(false);
					System.out.println("not following");
				} else {
					objSearch.setFollowing(true);
					System.out.println("following");
				}
				searchList.add(objSearch);
			}

			return searchList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}

	}

	public ArrayList<ObjSearch> loadUpdateSerach(String text, Long idUser) {
		StringBuilder hql = null;
		Query qo = null;
		try {
			// text = "'%"+text+"%'";
			System.out.println("TEXT: " + text);
			hql = new StringBuilder();
			hql.append(" SELECT user.id as id, user.name as name, user.lastName as lastName, user.description as description, user.photo as photo ");
			hql.append(" ,(select count(follower.id) from t_friend follower where follower.idFollowing=:idUser and follower.idFollowed=user.id )");
			hql.append(" FROM t_user user WHERE  user.id !=:idUser and (user.name like '%"
					+ text + "%' or user.lastName like '%" + text + "%') ");

			qo = getSession().createSQLQuery(hql.toString());
			qo.setParameter("idUser", idUser);
			// qo.setParameter("text", text);

			ArrayList<Object[]> objList = (ArrayList<Object[]>) qo.list();
			System.out.println("LISTA SIZA" + objList.size());
			ArrayList<ObjSearch> searchList = new ArrayList<ObjSearch>();
			for (Object[] obj : objList) {
				ObjSearch objSearch = new ObjSearch();
				objSearch.setIdUser(String.valueOf(obj[0]));
				objSearch.setName(String.valueOf(obj[1]));
				objSearch.setLastName(String.valueOf(obj[2]));
				objSearch.setDescription(String.valueOf(obj[3]));
				String photoStr = loadPhotoFromDisk(String.valueOf(obj[4]));
				objSearch.setPhoto(photoStr);
				if (String.valueOf(obj[5]).equals("0")) {
					objSearch.setFollowing(false);
					System.out.println("not following");
				} else {
					objSearch.setFollowing(true);
					System.out.println("following");
				}
				searchList.add(objSearch);
			}

			return searchList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
			qo = null;
		}
	}

}
