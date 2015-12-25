package com.mgl.twiitermanager.message;

import java.util.ArrayList;

import com.mgl.twiitermanager.model.MessageConstant;

public class MessageSingleton {

	private static MessageSingleton instance = null;

	private ArrayList<String> crappyMessageList;
	private ArrayList<String> vampireMessageList;

	private ArrayList<String> youtuberMessageList;

	private MessageSingleton() {
		try {

			crappyMessageList = new ArrayList<>();
			String s = new String(
					"Hi can you support me? Im an indie game developer https://play.google.com/store/apps/details?id=com.mgl.crappypigeon can you try it, and some advice?");
			crappyMessageList.add(s);

			s = new String(
					"Hello! Im an indie game developer https://play.google.com/store/apps/details?id=com.mgl.crappypigeon can you try my game? its free it help me");
			crappyMessageList.add(s);

			s = new String(
					"Greetings its me! and indiedev https://play.google.com/store/apps/details?id=com.mgl.crappypigeon can you try my game? its free it help me");
			crappyMessageList.add(s);

			s = new String(
					"hey! this is my free android game https://play.google.com/store/apps/details?id=com.mgl.crappypigeon can you try my game? its free it help me");
			crappyMessageList.add(s);

			s = new String(
					"do you want to support an indie game dev ? its free https://play.google.com/store/apps/details?id=com.mgl.crappypigeon");
			crappyMessageList.add(s);

			s = new String(
					"pleas I need some helo this is my free android game, can you share with frinds? https://play.google.com/store/apps/details?id=com.mgl.crappypigeon");
			crappyMessageList.add(s);

			s = new String(
					"this is a free android game when y r a pigeon who craps on Bieber and Miley want to try? https://play.google.com/store/apps/details?id=com.mgl.crappypigeon");
			crappyMessageList.add(s);

			s = new String(
					"Hey Im a developer! this is my game, y r a pigeon and craps on people! please help me https://play.google.com/store/apps/details?id=com.mgl.crappypigeon");
			crappyMessageList.add(s);

			s = new String(
					"can you help me? please share with some friends my new game! its very funny please take a look https://play.google.com/store/apps/details?id=com.mgl.crappypigeon");
			crappyMessageList.add(s);

			// vampire

			vampireMessageList = new ArrayList<>();
			s = new String(
					"Hi can you support me? Im an indie game developer https://play.google.com/store/apps/details?id=com.mgl.smasher can you try it, and some advice?");
			vampireMessageList.add(s);

			s = new String(
					"Hello! Im an indie game developer https://play.google.com/store/apps/details?id=com.mgl.smasher can you try my game? its free it help me");
			vampireMessageList.add(s);

			s = new String(
					"Greetings its me! and indiedev https://play.google.com/store/apps/details?id=com.mgl.smasher can you try my game? its free it help me");
			vampireMessageList.add(s);

			s = new String(
					"hey! this is my free android game https://play.google.com/store/apps/details?id=com.mgl.smasher can you try my game? its free it help me");
			vampireMessageList.add(s);

			s = new String(
					"do you want to support an indie game dev ? its free https://play.google.com/store/apps/details?id=com.mgl.smasher");
			vampireMessageList.add(s);

			s = new String(
					"Hello! can you help me sharing with some friends? its a free android game that Ive develop https://play.google.com/store/apps/details?id=com.mgl.smasher");
			vampireMessageList.add(s);

			s = new String(
					"do you like vampires? here is Vampire Smasher, my new android game please take a look https://play.google.com/store/apps/details?id=com.mgl.smasher");
			vampireMessageList.add(s);

			s = new String(
					"hey, please help me Im an inide game developer this is my free android game share with some frnds please https://play.google.com/store/apps/details?id=com.mgl.smasher");
			vampireMessageList.add(s);

			s = new String(
					"I think this game can be good for you or a good recommendation Im the developer! https://play.google.com/store/apps/details?id=com.mgl.smasher");
			vampireMessageList.add(s);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MessageSingleton getInstance() {
		if (instance == null) {
			instance = new MessageSingleton();
		}
		return instance;
	}

	public String getMessage(int idMessageConstant) {
		try {

			switch (idMessageConstant) {
			case MessageConstant.CRAPPY_PIGEON_MESSAGE:

				return crappyMessageList.get(getRandomMax(0,
						crappyMessageList.size() - 1));

			case MessageConstant.VAMPIRE_SMASHER_MESSAGE:

				return vampireMessageList.get(getRandomMax(0,
						vampireMessageList.size() - 1));

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String();
	}

	public static int getRandomMax(int min, int max) {

		try {

			Double val = min + ((Math.random() * 123456323) % (1 + max - min));
			return val.intValue();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return 1;
	}

	public String getMessageYoutuberDash() {
		try {

			int val = getRandomMax(0, 9);

			String message = new String();

			String url = "https://play.google.com/store/apps/details?id=com.mgl.youtuberdash";

			switch (val) {
			case 0:

				message =  "Jugue Youtuber Dash "+" "+url +" esta genial! estan mis youtuber favoritos, descargalo #androidgames #youtuberDash"; 
				
				break;
			case 1:
				
				message =  "Youtuber Dash esta genial!"+" "+url +"  pruebenlo! #indiedev #youtuberDash RT";
				
				break;
				
			case 2:
				
				message =  "Esto esta genial! hay muchos youtubers"+" "+url +"  pruebenlo! #gamedev #youtuberDash";
				
				break;
			case 3:
				
				message =  "Mira este nuevo juego para android Youtubers Dash"+" "+url +" me encanta! #android #youtuberDash RT";
				
				break;
				
			case 4:
				
				message =  "Hay muchos youtubers en este juego! "+" "+url +" me encanta! #youtuberDash #indiedev RT";
				
				break;
				
			case 5:
				
				message =  "Miren este juego "+" "+url +" es super adictivo! #youtuberDash RT";
				
				break;
				
			case 6:
				
				message =  "Miren este juego "+" "+url +" tiene una musica genial! #youtuberDash #indiedev RT";
				
				break;
				
			case 7:
				
				message =  "Youtuber Dash es super adictivo "+" "+url +" recomendado #youtuberDash #gamedev RT";
				
				break;
				
			case 8:	
				
				message =  "Recomendado "+" "+url +" es super adictivo! #youtuberDash #indiedev RT";
				
				break;
				
			case 9:
				
				message =  "Este juego esta genial"+" "+url +" es super adictivo! #youtuberDash #indiedev RT";
				
				break;
				
			default:
				break;
			}
			
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "#indiedev Recomendado https://play.google.com/store/apps/details?id=com.mgl.youtuberdash";
	}
	
	public String getMessageVampireSmasher() {
		String url = "https://play.google.com/store/apps/details?id=com.mgl.smasher #gamedev #vampireSmasher #vampire";
		try {

			int val = getRandomMax(0, 9);

			String message = new String();


			switch (val) {
			case 0:
				message =  "No me canso de aplastar a estos vampiros! deberían probarlo! "+url;
				
				break;
			case 1:
				
				message =  "Desestresante! jajaja me encanta aplastarlos "+url;
				
				break;
				
			case 2:
				
				message =  "alguien más es adicto a este juego? XD "+url;
				
				break;
			case 3:
				
				message =  "Que juego tan divertido! pruebenlo "+url;
				
				break;
				
			case 4:
				
				message =  "Lo mejor de este juego es que pone al día mis reflejos! "+url;
				
				break;
				
			case 5:
				
				
				message =  "Este juego esta genial! tambien les gusta? "+url;
				
				break;
				
			case 6:
				
				
				
				message =  "No paro de aplastar a estos vampiros! haha "+url;
				
				break;
				
			case 7:
				
				
				message =  "Vampire Smasher me gusta, es todo un reto! "+url;
				
				break;
				
			case 8:	
				
				message =  "Totalmente recomendado Vampire Smasher, aplastalos! "+url;
				
				break;
				
			case 9:
				
				
				message =  "Los reto a pasar este juego, comentenme! "+url;
				
				break;
				
			default:
				break;
			}
			
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Los reto a pasar este juego, comentenme! "+url;
	}
	

	public String getMessageYoutuber(String youtuberName, String userName) {
		try {
			int val = getRandomMax(0, 8);

			String message = new String();

			String url = "https://play.google.com/store/apps/details?id=com.mgl.youtuberdash";

			switch (val) {
			case 0:

				message = "Hola " + userName + " , veo que te gusta "
						+ youtuberName
						+ "! lo inclui en un juego para android " + url;

				break;
			case 1:

				message = "ey " + userName + " , te gusta " + youtuberName
						+ "! lo inclui en mi juego android " + url;

				break;

			case 2:

				message = "hey " + userName + " , que bien te gusta "
						+ youtuberName + "! esta en este juego android " + url;

				break;
			case 3:

				message = userName + " , sabes que inclui a " + youtuberName
						+ "!en mi juego android " + url;

				break;
			case 4:

				message = userName + " , Saludos! " + youtuberName
						+ " esta en mi juego android " + url;

				break;
			case 5:

				message = userName + " , Saludos! agregue a " + youtuberName
						+ "! a mi juego android " + url + " te gusta?";

				break;

			case 6:

				message = userName + " , Hola sabes que " + youtuberName
						+ "!esta n mi juego android " + url + " t gusta?";

				break;
			case 7:

				message = "hey " + userName + " , que bien te gusta "
						+ youtuberName + "! mira lo que hice " + url
						+ " que tal?";

				break;

			case 8:

				message = "nwn " + userName + " , veo que te gusta "
						+ youtuberName + "! lo puse en mi juego " + url
						+ " que tal?";

				break;

			default:
				break;
			}

			return message;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String();
	}

	public String getMessageHelpMeShare(String userName) {
		String message = new String(
				userName
						+ " me ayudarias a darlo a conocer? con algunos amigos? quiero hacerlo llegar a el! ");
		try {

			int val = getRandomMax(0, 5);

			switch (val) {
			case 0:
				message = userName
						+ " me ayudarias a darlo a conocer? con algunos amigos? quiero hacerlo llegar a el! ";
				break;
			case 1:
				message = userName
						+ " me apoyarias para hacerlo llegar a mas gente? quiero que el lo vea!!";
				break;
			case 2:
				message = userName
						+ " te gusto?? me ayudarias a compartirlo con amigos, para darlo a conocer?";
				break;
			case 3:
				message = userName
						+ " somos 3 desarrollando este juego, nos apoyarias compartiendolo, para darlo a a conocer?";
				break;
			case 4:
				message = userName
						+ " espero que te guste!! me ayudarias a darlo a conocer compartiendolo con amigos? ";
				break;
			case 5:
				message = userName
						+ " ojala te gsute trabajamos duro en esto! me apoyarias a compartiendolo con amigos? ";
				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

}
