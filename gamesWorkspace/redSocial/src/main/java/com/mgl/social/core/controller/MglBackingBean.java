package com.mgl.social.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;

import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.mgl.social.server.controller.BaseController;
import com.mgl.social.server.database.Category;
import com.mgl.social.server.database.Country;
import com.mgl.social.server.database.DateSearch;
import com.mgl.social.server.database.HashTag;
import com.mgl.social.server.database.Update;
import com.mgl.social.server.database.UpdateType;
import com.mgl.social.server.database.User;
import com.mgl.social.server.database.constant.CType;
import com.mgl.social.server.util.ManageDate;

public class MglBackingBean implements Serializable {

	/**
	 * 
	 */

	private User user = null;

	private ArrayList<Category> categoryList;
	private ArrayList<UpdateType> updateTypeList;
	private ArrayList<Country> countryList;
	private ArrayList<DateSearch> dateList;
	private ArrayList<SelectItem> updatePersonList;
	
	private BaseController controller;
	
	public static final Long GENERAL_LIMIT = 2L;
	public Long GENERAL_MULTIPLIER = 1L;
	
	public static final Long SEARCH_LIMIT = 1L;
	public Long SEARCH_MULTIPLIER = 1L;

	public MglBackingBean() {
		try {

			System.out.println("backing bean");
			boolean isValid = validateSession();

			controller = new BaseController();

			loadList();

			if (!isValid) {
				redirectURL("pages/login.jspx");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadList() {
		try {

			categoryList = controller.loadCategoryList();
			updateTypeList = controller.loadUpdateTypeList();
			countryList = controller.loadCountryList();
			dateList = controller.loadDateList();
			
			updatePersonList = new ArrayList<SelectItem>();
			updatePersonList.add(new SelectItem(CType.UPDATES, CType.UPDATES_STR));
			updatePersonList.add(new SelectItem(CType.PERSONS, CType.PERSONS_STR));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addMessageToSend(Long idUser, String name) {
		try {
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("idUserMessage", idUser);
			context.getExternalContext().getSessionMap().put("nameUserMessage", name);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public Long getIdUserMessage(){
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			return (Long) context.getExternalContext().getSessionMap().get("idUserMessage");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public String getNameUserMessage(){
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			return (String) context.getExternalContext().getSessionMap().get("nameUserMessage");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void addUserLogged(User user) {
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("user", user);
			this.user=user;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void setTypeTimeline(String type){
		try {
		
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap()
					.put("typeTimeline", type);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setHashtagGeneral(HashTag hashtag){
		try {
		
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap()
					.put("hashtag", hashtag);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public HashTag getHashtagGeneral(){
		try {
		
			FacesContext context = FacesContext.getCurrentInstance();
			return (HashTag) context.getExternalContext().getSessionMap()
					.get("hashtag");
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getTypeTimeline(){
		try {
			
			FacesContext context = FacesContext.getCurrentInstance();
			return (String) context.getExternalContext().getSessionMap()
					.get("typeTimeline");
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void addPerfilToView(Long idUser) {
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap()
					.put("perfilId", idUser);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getPerfilToView() {
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			Long id = (Long) context.getExternalContext().getSessionMap()
					.get("perfilId");

			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void addIdUpdate(Long idUpdate) {
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap()
					.put("idUpdate", idUpdate);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getIdUpdate() {
		try {

			FacesContext context = FacesContext.getCurrentInstance();
			Long id = (Long) context.getExternalContext().getSessionMap()
					.get("idUpdate");

			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * mafm
	 * 
	 * @return
	 */
	public boolean skipFreeAccessPages() {
		if (getRequestURI().endsWith("register.jspx")) {
			return true;
		}
		return false;
	}

	/**
	 * mafm
	 * 
	 * @return
	 */
	public boolean validateSession() {
		try {

			if (skipFreeAccessPages()) {
				return true;
			}

			FacesContext context = FacesContext.getCurrentInstance();
			user = (User) context.getExternalContext().getSessionMap()
					.get("user");
			if (user == null) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * mafm
	 */
	public void logOut() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void redirectURL(String path) {
		String URL = null;
		try {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();

			String scheme = getRequest().getScheme();
			String serverName = getRequest().getServerName();
			String contextPath = getRequest().getContextPath();
			int serverPort = getRequest().getServerPort();

			URL = scheme + "://" + serverName + ":" + serverPort + contextPath;
			context.redirect(URL + "/" + path);
		} catch (Exception e) {
			System.out.println("ERROR: Failured redirect");
		}
	}

	public String getRequestURI() {
		return getRequest().getRequestURI();
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public static void update(String... clientID) {
		try {
			for (String id : clientID) {
				getRequestContext().update(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
	}

	public static HttpSession getSession(boolean isNew) {
		if (FacesContext.getCurrentInstance().getExternalContext() != null) {
			return (HttpSession) FacesContext.getCurrentInstance()
					.getExternalContext().getSession(isNew);
		}
		return null;
	}

	/**
	 * Method for getting the current file path as the current SO
	 * 
	 * @return C:/ if is win2 or / is if a linux SO
	 */
	public static String getPathBySO() {
		return System.getProperty("os.name").toLowerCase().startsWith("win") ? "C:/"
				: "/";
	}

	/**
	 * method for add a message in the faces context
	 * 
	 * @param severityInfo
	 * @param title
	 * @param description
	 */
	public static void addMessage(Severity severityInfo, String title,
			String description) {
		getFacesContext().addMessage(null,
				new FacesMessage(severityInfo, title, description));
	}

	public MglBackingBean getBean(String beanName) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			MglBackingBean c = (MglBackingBean) facesContext
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(facesContext.getELContext(),
							"#{" + beanName + "}", MglBackingBean.class)
					.getValue(facesContext.getELContext());
			return c;
		} catch (Exception e) {
			return null;
		} finally {
			facesContext = null;
		}
	}
	
	public String calculateTimeBetween(String date, String current) {
		try {
			// Log.d("DATE DATE", current);
			String dat = "Time ago " + ": ";
			// "2014/01/30 10:20";
			date = date.replace("-", "/");
			current = current.replace("-", "/");
			
			Date currentDate = ManageDate.formatDate(current,
					ManageDate.YYYY_MM_DD_HH_MM);
			Date myDate = ManageDate.formatDate(date,
					ManageDate.YYYY_MM_DD_HH_MM);

			// Log.d("DATE DATE DATE",""+date);

			Long currentDateLong = currentDate.getTime();
			Long myDateLong = myDate.getTime();

			Long diff = currentDateLong - myDateLong;

			Date dateBet = new Date(diff);
			Long minute = TimeUnit.MILLISECONDS.toMinutes(diff);
			Long day = TimeUnit.MILLISECONDS.toDays(diff);
			Long hour = TimeUnit.MILLISECONDS.toHours(diff);

			Double monthD = day / 28D;
			int month = (int) monthD.doubleValue();

			Double yearD = day / 365D;
			int year = (int) yearD.doubleValue();

			// Log.d("DATE "+month+"NEW " +year, dateBet.toString());

			if (year > 0) {

				dat = dat + year + " " + "year";
				return dat;
			}
			if (month > 0) {
				dat = dat + month + " " + "months";
				return dat;
			}
			if (day > 0) {
				dat = dat + day + " " + "days";
				return dat;
			}
			if (hour >= 1) {
				dat = dat + hour + " " + "hours";
				return dat;
			}
			if (minute >= 1) {
				dat = dat + minute + " " + "minutes";
				return dat;
			}

			dat = dat + " " + "seconds";
			return dat;

		} catch (Exception e) {
			e.printStackTrace();
			return "Some Time Ago";
		}
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public ArrayList<UpdateType> getUpdateTypeList() {
		return updateTypeList;
	}

	public void setUpdateTypeList(ArrayList<UpdateType> updateTypeList) {
		this.updateTypeList = updateTypeList;
	}

	public BaseController getController() {
		return controller;
	}

	public void setController(BaseController controller) {
		this.controller = controller;
	}

	public ArrayList<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(ArrayList<Country> countryList) {
		this.countryList = countryList;
	}

	public ArrayList<DateSearch> getDateList() {
		return dateList;
	}

	public void setDateList(ArrayList<DateSearch> dateList) {
		this.dateList = dateList;
	}

	public ArrayList<SelectItem> getUpdatePersonList() {
		return updatePersonList;
	}

	public void setUpdatePersonList(ArrayList<SelectItem> updatePersonList) {
		this.updatePersonList = updatePersonList;
	}	
	
}
