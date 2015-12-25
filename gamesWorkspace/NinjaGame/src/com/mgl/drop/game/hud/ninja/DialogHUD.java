package com.mgl.drop.game.hud.ninja;

import java.util.ArrayList;

import org.andengine.entity.text.Text;

import com.mgl.drop.util.Point;
import com.mgl.base.ButtonListener;
import com.mgl.base.TextFactory;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonGeneral;
import com.mgl.drop.texture.TextureSingleton;

public class DialogHUD extends MyHud {

	// private String textToShow;

	private LevelController controller;

	private String text;
	
	public DialogHUD( String text,
			LevelController controller) {
		super();
		try {
			
			this.text = text;
			this.controller = controller;

			create();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void create() {

		try {
			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0.2f);
			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			this.attachChild(background);

			SpriteBackground backgroundText = new SpriteBackground(0, 0,
					texture.getTextureByName("masterBanner.png"),
					texture.getVertexBufferObjectManager());
			backgroundText.setPosition(0, MainDropActivity.CAMERA_HEIGHT
					- backgroundText.getHeight());
			this.attachChild(backgroundText);

			addText(50,330);

			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setPosition(
					MainDropActivity.CAMERA_WIDTH - remove.getWidth()-5,
					MainDropActivity.CAMERA_HEIGHT - remove.getHeight()-5);

			this.attachChild(remove);
			this.registerTouchArea(remove);

			this.registerTouchArea(background);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addText(float x, float y) {
		try {

			DialogPaginator paginator = new DialogPaginator(this, new Point(x,y));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		try {

			controller.setMustUpdate(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LevelController getController() {
		return controller;
	}

	public void setController(LevelController controller) {
		this.controller = controller;
	}

	public class DialogPaginator {

		private ArrayList<Text> textList;

		int beginPosition = 0;
		int endPosition = 0;

		private ButtonGeneral readMore;
		private DialogHUD dialogHud;

		private Point position;
		
		
		TextureSingleton texture = TextureSingleton.getInstance();

		public DialogPaginator(DialogHUD dialogHUD2, Point position) {
			try {

				this.dialogHud = dialogHUD2;
				beginPosition = 0;
				this.position = position;
				createText();
				
				addText();
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void createText() {
			try {
				
				int totalLines = text.length()/30;
				textList = new ArrayList<Text>();
				
				String textAux = new String();
				int j = 0;
				for(int i =0;i<text.length();i++){
					
					j++;
					if(j>=30){
						String val = ""+ text.charAt(i-1);
						if(!val.equals(" ")){
							//set last word to the other line
							String text = textAux.substring(0,textAux.lastIndexOf(" "));
							String text2 = textAux.substring(textAux.lastIndexOf(" "),textAux.length());
							
							textAux = text;
							
							Text textAdd = TextFactory.createText(textAux, TextureSingleton.getInstance().getmFont1());
							textList.add(textAdd);
							textAux = new String();
							
							textAux = textAux+ text2.substring(1);
							j = text2.length();
							
						}else{
							Text textAdd = TextFactory.createText(textAux, TextureSingleton.getInstance().getmFont1());
							textList.add(textAdd);
							textAux = new String();
							j=0;
						}
						
					}
					textAux = textAux + text.charAt(i);
					
				}
				
				Text textAdd = TextFactory.createText(textAux, TextureSingleton.getInstance().getmFont1());
				textList.add(textAdd);
				
				addButtonReadMore();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void addText() {
			try {

				int i =0;
				int contLine = 0;
				int lines = 5;
				
				for(Text t : textList){
					t.detachSelf();
					if(i>=this.beginPosition){
						contLine ++;
						if(contLine<=lines){
							t.setPosition(position.getX(), position.getY() + (t.getHeight() * contLine));
							dialogHud.attachChild(t);	
						}else{
							break;
						}
					}
					i++;
				}
				
				this.beginPosition = beginPosition+contLine-1;
				
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private void addButtonReadMore() {
			try {

				if (readMore == null) {
					readMore = new ButtonGeneral(0, 0,
							texture.getTextureByName("readMore.png"),
							texture.getVertexBufferObjectManager(),
							new ButtonListener() {

								@Override
								public void onActionUp(float x, float y) {
									addText();
									// readMore.detachSelf();

								}

								@Override
								public void onActionMove(float x, float y) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onActionDown(float x, float y) {
									// TODO Auto-generated method stub

								}
							});
				}
				
				readMore.setSize(50, 50);
				readMore.setPosition(0,MainDropActivity.CAMERA_HEIGHT-readMore.getHeight() - 10);
				dialogHud.attachChild(readMore);
				dialogHud.registerTouchArea(readMore);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}

