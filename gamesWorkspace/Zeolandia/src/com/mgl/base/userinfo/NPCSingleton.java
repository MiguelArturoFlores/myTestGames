package com.mgl.base.userinfo;

import com.mgl.drop.game.controller.DialogController;
import com.mgl.drop.game.controller.DialogListener;

public class NPCSingleton {
	
	
	public static final int NPC_LEVE1_NORTH = 1;
	public static final int NPC_LEVE1_SOUTH = 2;
	public static final int NPC_LEVE1_MID = 3;

	private static NPCSingleton instance = null;
	
	private NPCSingleton(){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static NPCSingleton getInstance() {
		if(instance == null){
			instance = new NPCSingleton();
		}
		return instance;
	}

	public DialogController createNpcDialog(Long id) {
		try {
			
			switch (id.intValue()) {
			case NPC_LEVE1_NORTH:
				
				return createDialogLevel1North();
				
				
			case NPC_LEVE1_MID:
								
				return createDialogLevel1Mid();
				
			case NPC_LEVE1_SOUTH:
				
				
				return createDialogLevel1South();
				
				
				
			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	
	private DialogController createDialogLevel1North() {
		try {
			
			
			DialogController dialog1 = new  DialogController(null, null, "Hola soy IDR y si quieres te puedo enseñar algo");
			DialogController dialog1Yes = new  DialogController(null, null, "Muy bien, hay tecnicas especiales quieres aprende una?");
			DialogController dialog1No = new  DialogController(null, null, "ok chao!");
			
			DialogController dialog2Yes = new  DialogController(null, null, "Acabas de aprender una nueva !");
			DialogController dialog2No = new  DialogController(null, null, "... ... .!. ");
			dialog1Yes.setDialogNo(dialog2No);
			dialog1Yes.setDialogYes(dialog2Yes);
			
			
			dialog1.setDialogNo(dialog1No);
			dialog1.setDialogYes(dialog1Yes);

			dialog1Yes.setDialogListener(new DialogListener() {
				
				@Override
				public void onDialogYes() {
					try {
						
						PlayerSingleton.getInstance().unlockTechniqueOneZEO();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onDialogNo() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onDialogClose() {
					// TODO Auto-generated method stub
					
				}
			});
			
			return dialog1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private DialogController createDialogLevel1NorthAux() {
		try {
			
			DialogController dialog1 = new  DialogController(null, null, "Hola estoy aca y no se que hago puedes ayudarme ?");
			DialogController dialog1Yes = new  DialogController(null, null, "Gracias, debes ir a la casa de al lado y volver, lo haras?");
			DialogController dialog1No = new  DialogController(null, null, "ok chao!");
			
			DialogController dialog2Yes = new  DialogController(null, null, "Gracias estare esperando! adios!");
			DialogController dialog2No = new  DialogController(null, null, "Tu te lo pierdes!");
			dialog1Yes.setDialogNo(dialog2No);
			dialog1Yes.setDialogYes(dialog2Yes);
			
			
			dialog1.setDialogNo(dialog1No);
			dialog1.setDialogYes(dialog1Yes);
			
			return dialog1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private DialogController createDialogLevel1Mid() {
		try {
			
			DialogController dialog1 = new  DialogController(null, null, "Hola Arriba hay monstruos! que pena");
			
			return dialog1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private DialogController createDialogLevel1South() {
		try {
			
			DialogController dialog1 = new  DialogController(null, null, "Soy el NPC del sur saludos! explora bien la zona hay tesoros escondidos");
			
			return dialog1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
