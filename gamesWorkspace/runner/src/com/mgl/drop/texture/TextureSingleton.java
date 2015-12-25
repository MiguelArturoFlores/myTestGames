package com.mgl.drop.texture;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.animationpack.AnimationPack;
import org.andengine.util.animationpack.AnimationPackLoader;
import org.andengine.util.animationpack.AnimationPackTiledTextureRegion;
import org.andengine.util.animationpack.AnimationPackTiledTextureRegionLibrary;
import org.andengine.util.debug.Debug;
import org.andengine.util.texturepack.TexturePack;
import org.andengine.util.texturepack.TexturePackLoader;
import org.andengine.util.texturepack.TexturePackTextureRegionLibrary;

import com.google.android.gms.internal.ob;
import com.mgl.drop.texture.constant.ButtonPack;
import com.mgl.drop.texture.constant.ObjectPack;
import com.mgl.drop.texture.constant.Screens1;
import com.mgl.drop.texture.constant.Screens2;
import com.mgl.drop.texture.constant.Screens3;
import com.mgl.drop.texture.constant.SpritePack1;
import com.mgl.drop.texture.constant.SpritePack2;
import com.mgl.drop.texture.constant.SpritePack3;
import com.mgl.drop.texture.constant.levelScreenshot;

import android.content.res.AssetManager;
import android.graphics.Color;

public class TextureSingleton {

	private static TextureSingleton instance = null;

	private TexturePack texturePack;
	//private TexturePackTextureRegionLibrary tpl;
	
	private TextureManager textureManager;
	private AssetManager assetManager;
	private VertexBufferObjectManager vertexBufferObjectManager;
	private FontManager fontManager;

	// fonts
	private Font mFont;
	private Font mFont1;
	private Font mFont2;
	// textures

	private HashMap<String, ITextureRegion> hashMap;
	private HashMap<String, ITextureRegion> hashMapAux;
	private HashMap<String, ITexture> hashMapItexture;
	private HashMap<String, BitmapTextureAtlas> hashMapAtlas;

	private boolean loadAll = false;

	private TextureSingleton(TextureManager textureManager,
			AssetManager assetManager,
			VertexBufferObjectManager vertexBufferObjectManager,
			FontManager fontManager) {
		try {
			System.out.println("CREO EL SINGLETON DE TEXTURAS");
			hashMap = new HashMap<String, ITextureRegion>();
			hashMapAux = new HashMap<String, ITextureRegion>();
			hashMapItexture = new HashMap<String, ITexture>();
			hashMapAtlas = new HashMap<String, BitmapTextureAtlas>();
			this.textureManager = textureManager;
			this.assetManager = assetManager;
			this.vertexBufferObjectManager = vertexBufferObjectManager;
			this.fontManager = fontManager;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadTextures() {
		try {
			
			loadTexture("background.jpg");
			
			loadTexture("backgroundW1.png");
			loadTexture("backgroundW2.png");
			loadTexture("backgroundW3.png");
			
			loadTexture("introBackground1.png");
			loadTexture("introBackground2.png");
			
			loadTexture("rateScreen.png");
			loadTexture("buttonPlay2.png");
			
			loadTexture("adn2Free.png");
			
			TexturePackLoader texturePackLoader = new TexturePackLoader(assetManager, textureManager);
			
			
			texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/ButtonPack.xml", "gfxSpriteSheets/");
			texturePack.loadTexture();
			TexturePackTextureRegionLibrary tplButtonPack = texturePack.getTexturePackTextureRegionLibrary();
			
			hashMap.put("reloadBar.png",tplButtonPack.get(ButtonPack.RELOADBAR_ID));
			hashMap.put("adnM1.png",tplButtonPack.get(ButtonPack.ADNM1_ID));
			hashMap.put("adnM2.png",tplButtonPack.get(ButtonPack.ADNM2_ID));
			hashMap.put("adnM3.png",tplButtonPack.get(ButtonPack.ADNM3_ID));
			hashMap.put("adnR.png",tplButtonPack.get(ButtonPack.ADNR_ID));
			hashMap.put("adnG.png",tplButtonPack.get(ButtonPack.ADNG_ID));
			hashMap.put("adnB.png",tplButtonPack.get(ButtonPack.ADNB_ID));
			hashMap.put("accelerateIcon.png",tplButtonPack.get(ButtonPack.ACCELERATEICON_ID));
			hashMap.put("adn1.png",tplButtonPack.get(ButtonPack.ADN1_ID));
			hashMap.put("adn2.png",tplButtonPack.get(ButtonPack.ADN2_ID));
			hashMap.put("arrowDown.png",tplButtonPack.get(ButtonPack.ARROWDOWN_ID));
			hashMap.put("arrowLeft.png",tplButtonPack.get(ButtonPack.ARROWLEFT_ID));
			hashMap.put("arrowRight.png",tplButtonPack.get(ButtonPack.ARROWRIGHT_ID));
			hashMap.put("arrowUp.png",tplButtonPack.get(ButtonPack.ARROWUP_ID));
			hashMap.put("black.jpg",tplButtonPack.get(ButtonPack.BLACK_ID));
			hashMap.put("blog.png",tplButtonPack.get(ButtonPack.BLOG_ID));
			hashMap.put("blueBackground.jpg",tplButtonPack.get(ButtonPack.BLUEBACKGROUND_ID));
			//hashMap.put("buttonPlay2.png",tplButtonPack.get(ButtonPack.BUTTONPLAY2_ID));
			hashMap.put("buttonPlay.png",tplButtonPack.get(ButtonPack.BUTTONPLAY_ID));
			hashMap.put("buttonTexture.png",tplButtonPack.get(ButtonPack.BUTTONTEXTURE_ID));
			hashMap.put("closeHud.png",tplButtonPack.get(ButtonPack.CLOSEHUD_ID));
			hashMap.put("emptyStar.png",tplButtonPack.get(ButtonPack.EMPTYSTAR_ID));
			hashMap.put("eye.png",tplButtonPack.get(ButtonPack.EYE_ID));
			hashMap.put("fb.png",tplButtonPack.get(ButtonPack.FB_ID));
			hashMap.put("fillStar.png",tplButtonPack.get(ButtonPack.FILLSTAR_ID));
			hashMap.put("gray.jpg",tplButtonPack.get(ButtonPack.GRAY_ID));
			hashMap.put("help.png",tplButtonPack.get(ButtonPack.HELP_ID));
			hashMap.put("iceIcon.png",tplButtonPack.get(ButtonPack.ICEICON_ID));
			hashMap.put("igrm.png",tplButtonPack.get(ButtonPack.IGRM_ID));
			hashMap.put("joystickBall.png",tplButtonPack.get(ButtonPack.JOYSTICKBALL_ID));
			hashMap.put("joystickLimit.png",tplButtonPack.get(ButtonPack.JOYSTICKLIMIT_ID));
			hashMap.put("mainWindow.png",tplButtonPack.get(ButtonPack.MAINWINDOW_ID));
			hashMap.put("money.png",tplButtonPack.get(ButtonPack.MONEY_ID));
			hashMap.put("moreMoney.png",tplButtonPack.get(ButtonPack.MOREMONEY_ID));
			hashMap.put("offBegin.png",tplButtonPack.get(ButtonPack.OFFBEGIN_ID));
			hashMap.put("onBegin.png",tplButtonPack.get(ButtonPack.ONBEGIN_ID));
			hashMap.put("pause.png",tplButtonPack.get(ButtonPack.PAUSE_ID));
			hashMap.put("powerBar.png",tplButtonPack.get(ButtonPack.POWERBAR_ID));
			hashMap.put("powerIceCircle.png",tplButtonPack.get(ButtonPack.POWERICECIRCLE_ID));
			hashMap.put("rate.png",tplButtonPack.get(ButtonPack.RATE_ID));
			hashMap.put("redLine.png",tplButtonPack.get(ButtonPack.REDLINE_ID));
			hashMap.put("retry.png",tplButtonPack.get(ButtonPack.RETRY_ID));
			hashMap.put("selectLevel.png",tplButtonPack.get(ButtonPack.SELECTLEVEL_ID));
			hashMap.put("spot.png",tplButtonPack.get(ButtonPack.SPOT_ID));
			hashMap.put("selectLevel.png",tplButtonPack.get(ButtonPack.SELECTLEVEL_ID));
			hashMap.put("shotIcon.png",tplButtonPack.get(ButtonPack.SHOTICON_ID));
			hashMap.put("sideWindow.png",tplButtonPack.get(ButtonPack.SIDEWINDOW_ID));
			hashMap.put("soundOff.png",tplButtonPack.get(ButtonPack.SOUNDOFF_ID));
			hashMap.put("soundOn.png",tplButtonPack.get(ButtonPack.SOUNDON_ID));
			hashMap.put("twttr.png",tplButtonPack.get(ButtonPack.TWTTR_ID));
			hashMap.put("video.png",tplButtonPack.get(ButtonPack.VIDEO_ID));
			tplButtonPack = null;
			
			texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/levelScreenshot.xml", "gfxSpriteSheets/");
			texturePack.loadTexture();
			TexturePackTextureRegionLibrary tplScreenshot = texturePack.getTexturePackTextureRegionLibrary();
			
			hashMap.put("level1.png", tplScreenshot.get(levelScreenshot.LEVEL1_ID));
			hashMap.put("level2.png", tplScreenshot.get(levelScreenshot.LEVEL2_ID));
			hashMap.put("level3.png", tplScreenshot.get(levelScreenshot.LEVEL3_ID));
			hashMap.put("level4.png", tplScreenshot.get(levelScreenshot.LEVEL4_ID));
			hashMap.put("level5.png", tplScreenshot.get(levelScreenshot.LEVEL5_ID));
			hashMap.put("level6.png", tplScreenshot.get(levelScreenshot.LEVEL6_ID));
			
			tplScreenshot = null;
			
			texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/ObjectPack.xml", "gfxSpriteSheets/");
			texturePack.loadTexture();
			TexturePackTextureRegionLibrary tplObject = texturePack.getTexturePackTextureRegionLibrary();
			
			hashMap.put("bone1.png",tplObject.get(ObjectPack.BONE1_ID));
			hashMap.put("bone2.png",tplObject.get(ObjectPack.BONE2_ID));
			hashMap.put("bone4.png",tplObject.get(ObjectPack.BONE4_ID));
			hashMap.put("gel.png",tplObject.get(ObjectPack.GEL_ID));
			hashMap.put("hedgehog1.png",tplObject.get(ObjectPack.HEDGEHOG1_ID));
			hashMap.put("hedgehog2.png",tplObject.get(ObjectPack.HEDGEHOG2_ID));
			hashMap.put("hedgehog3.png",tplObject.get(ObjectPack.HEDGEHOG3_ID));
			hashMap.put("hedgehog4.png",tplObject.get(ObjectPack.HEDGEHOG4_ID));
			hashMap.put("lakeBase.png",tplObject.get(ObjectPack.LAKEBASE_ID));
			hashMap.put("meteor1.png",tplObject.get(ObjectPack.METEOR3_ID));
			hashMap.put("meteor2.png",tplObject.get(ObjectPack.METEOR3_ID));
			hashMap.put("meteor3.png",tplObject.get(ObjectPack.METEOR3_ID));
			hashMap.put("meteorOffFocus.png",tplObject.get(ObjectPack.METEOROFFFOCUS_ID));
			hashMap.put("platform.png",tplObject.get(ObjectPack.PLATFORM_ID));
			hashMap.put("roofBase1.png",tplObject.get(ObjectPack.ROOFBASE1_ID));
			hashMap.put("roofBegin1.png",tplObject.get(ObjectPack.ROOFBEGIN1_ID));
			hashMap.put("title.png",tplObject.get(ObjectPack.TITLE_ID));
			hashMap.put("tunelFront.png",tplObject.get(ObjectPack.TUNELFRONT_ID));
			hashMap.put("tunelBase.png",tplObject.get(ObjectPack.TUNELBASE_ID));
			hashMap.put("vulcano.png",tplObject.get(ObjectPack.VULCANO_ID));
			hashMap.put("wallBorder.png",tplObject.get(ObjectPack.WALLBORDER_ID));
			hashMap.put("wall.png",tplObject.get(ObjectPack.WALL_ID));
			hashMap.put("turboBack.png",tplObject.get(ObjectPack.TURBOBACK_ID));
			//hashMap.put("wall.png",tplObject.get(ObjectPack.WALL_ID));
			
			tplObject = null;
			
			//texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/Screens1.xml", "gfxSpriteSheets/");
			//texturePack.loadTexture();
			//TexturePackTextureRegionLibrary tplScreens1 = texturePack.getTexturePackTextureRegionLibrary();
			
			//hashMap.put("background.jpg",tplScreens1.get(Screens1.BACKGROUND_ID));
			//hashMap.put("backgroundW1.png",tplScreens1.get(Screens1.BACKGROUNDW1_ID));
			//hashMap.put("backgroundW2.png",tplScreens1.get(Screens1.BACKGROUNDW2_ID));
			//hashMap.put("backgroundW3.png",tplScreens1.get(Screens1.BACKGROUNDW3_ID));
			//hashMap.put("introBackground1.png",tplScreens1.get(Screens1.INTROBACKGROUND1_ID));
			//hashMap.put("introBackground2.png",tplScreens1.get(Screens1.INTROBACKGROUND2_ID));
			
			texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/Screens2.xml", "gfxSpriteSheets/");
			texturePack.loadTexture();
			TexturePackTextureRegionLibrary tplScreens2 = texturePack.getTexturePackTextureRegionLibrary();
			
			hashMap.put("loading1.png",tplScreens2.get(Screens2.LOADING1_ID));
			hashMap.put("help1.png",tplScreens2.get(Screens2.HELP1_ID));
			hashMap.put("help3.png",tplScreens2.get(Screens2.HELP3_ID));
			hashMap.put("help4.png",tplScreens2.get(Screens2.HELP4_ID));
			hashMap.put("help5.png",tplScreens2.get(Screens2.HELP5_ID));
			
			tplScreens2 = null;
			
			texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/Screens3.xml", "gfxSpriteSheets/");
			texturePack.loadTexture();
			TexturePackTextureRegionLibrary tplScreens3 = texturePack.getTexturePackTextureRegionLibrary();
			
			hashMap.put("winAll.png",tplScreens3.get(Screens3.WINALL_ID));
			hashMap.put("winImage.png",tplScreens3.get(Screens3.WINIMAGE_ID));
			hashMap.put("help2.png",tplScreens3.get(Screens3.HELP2_ID));
			
			tplScreens3 = null;
			
			texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/SpritePack1.xml", "gfxSpriteSheets/");
			texturePack.loadTexture();
			TexturePackTextureRegionLibrary tplSpritePack1 = texturePack.getTexturePackTextureRegionLibrary();
			
			hashMap.put("adn2Animated.png",tplSpritePack1.get(SpritePack1.ADN2ANIMATED_ID,4,2));
			hashMap.put("bubbleStar.png",tplSpritePack1.get(SpritePack1.BUBBLESTAR_ID,4,2));
			hashMap.put("burble.png",tplSpritePack1.get(SpritePack1.BURBLE_ID,2,2));
			hashMap.put("checkPoint.png",tplSpritePack1.get(SpritePack1.CHECKPOINT_ID,4,2));
			hashMap.put("ice.png",tplSpritePack1.get(SpritePack1.ICE_ID,3,2));
			hashMap.put("powerIceCircleAnimated.png",tplSpritePack1.get(SpritePack1.POWERICECIRCLEANIMATED_ID,4,4));
			hashMap.put("shot.png",tplSpritePack1.get(SpritePack1.SHOT_ID,5,1));
			hashMap.put("virusA.png",tplSpritePack1.get(SpritePack1.VIRUSA_ID,3,3));
			hashMap.put("enemy1.png",tplSpritePack1.get(SpritePack1.ENEMY1_ID,2,7));
			hashMap.put("enemy2.png",tplSpritePack1.get(SpritePack1.ENEMY2_ID,2,7));
			hashMap.put("enemy3.png",tplSpritePack1.get(SpritePack1.ENEMY3_ID,2,7));
			hashMap.put("enemy.png",tplSpritePack1.get(SpritePack1.ENEMY_ID,4,3));
			hashMap.put("powerShot.png",tplSpritePack1.get(SpritePack1.POWERSHOT_ID,4,1));
			hashMap.put("intro1.png",tplSpritePack1.get(SpritePack1.INTRO1_ID));
			hashMap.put("intro2.png",tplSpritePack1.get(SpritePack1.INTRO2_ID));
			hashMap.put("intro3.png",tplSpritePack1.get(SpritePack1.INTRO3_ID));
			hashMap.put("intro4.png",tplSpritePack1.get(SpritePack1.INTRO4_ID));
			
			/*texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/SpritePack2.xml", "gfxSpriteSheets/");
			texturePack.loadTexture();
			TexturePackTextureRegionLibrary tplSpritePack2 = texturePack.getTexturePackTextureRegionLibrary();
			
			hashMap.put("virusAModel.png",tplSpritePack2.get(SpritePack2.VIRUSAMODEL_ID,4,3));
			hashMap.put("virusBModel.png",tplSpritePack2.get(SpritePack2.VIRUSBMODEL_ID,4,3));
			hashMap.put("virusB.png",tplSpritePack2.get(SpritePack2.VIRUSB_ID,3,3));
			hashMap.put("virusCModel.png",tplSpritePack2.get(SpritePack2.VIRUSCMODEL_ID,4,3));
			hashMap.put("virusC.png",tplSpritePack2.get(SpritePack2.VIRUSC_ID,3,3));
			hashMap.put("virusA.png",tplSpritePack2.get(SpritePack2.VIRUSA_ID,3,3));
			*/
			
			//texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/SpritePack3.xml", "gfxSpriteSheets/");
			//texturePack.loadTexture();
			//TexturePackTextureRegionLibrary tplSpritePack3 = texturePack.getTexturePackTextureRegionLibrary();
			
			//hashMap.put("virusADead.png",tplSpritePack3.get(SpritePack3.VIRUSADEAD_ID,4,3));
			//hashMap.put("virusBDead.png",tplSpritePack3.get(SpritePack3.VIRUSBDEAD_ID,4,3));
			//hashMap.put("virusCDead.png",tplSpritePack3.get(SpritePack3.VIRUSCDEAD_ID,4,3));
			
			loadTextureAtlas("virusAModel.png",987,740,4,3);
			loadTextureAtlas("virusBModel.png",987,740,4,3);
			loadTextureAtlas("virusB.png",344,281,3,3);
			loadTextureAtlas("virusCModel.png",987,952,4,3);
			loadTextureAtlas("virusC.png",281,361,3,3);
			loadTextureAtlas("virusA.png",345,282,3,3);
			
			
			loadTextureAtlas("virusADead.png",878,658,4,3);
			loadTextureAtlas("virusBDead.png",878,658,4,3);
			loadTextureAtlas("virusCDead.png",875,657,4,3);
			
			//hashMap.put("winAll.png",tpl.get(pack4.WINALL_ID));
			
			/*AnimationPackLoader animationPackLoader = new AnimationPackLoader(assetManager, textureManager);
			AnimationPack mAnimationPack;
			AnimationPackTiledTextureRegionLibrary mAnimationPackAnimationDataLibrary;
			
			mAnimationPack = animationPackLoader.loadFromAsset("gfxSpriteSheets/Vampire.xml", "gfxSpriteSheets/");
			mAnimationPackAnimationDataLibrary = mAnimationPack.getAnimationPackAnimationDataLibrary();
			
			AnimationPackTiledTextureRegion animationPackTiledTextureRegion = mAnimationPackAnimationDataLibrary.get(Vampire.ARMADURA0016_ID+"");
			*/
			
			
			loadTexturesMainScene();
			// secundarias
			

			// JOSE TEXTURES
			// aANIMATED;
			//loadTextureAtlas("bubbleStar.png",600,300,4,2);
			//loadTextureAtlas("checkPoint.png",600,300,4,2);
			//loadTextureAtlas("adn2Animated.png",600,300,4,2);
			///loadTextureAtlas("powerIceCircleAnimated.png",1000,1000,4,4);
			//loadTextureAtlas("ice.png", 270, 188, 3, 2);
			//loadTextureAtlas("shot.png", 575, 118,5, 1);
			//loadTextureAtlas("enemy.png", 374, 243, 4, 3);
			//loadTextureAtlas("enemy1.png", 108, 329, 2, 7);
			//loadTextureAtlas("enemy2.png", 150, 448, 2, 7);
			//loadTextureAtlas("enemy3.png", 150, 448, 2, 7);
			//loadTextureAtlas("virusA.png", 345, 282, 3, 3);
			//loadTextureAtlas("virusADead.png", 878, 658, 4, 3);
			//loadTextureAtlas("virusAModel.png", 987, 740, 4, 3);
			//loadTextureAtlas("burble.png", 140, 139, 2, 2);

			//loadTextureAtlas("shop.png", 300, 154, 2, 1);

			loadAll = true;
			// backgroud

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void loadTexturesAux() {
		try {
			
			loadTexture("background.jpg");
			
			loadTexture("backgroundW1.png");
			loadTexture("backgroundW2.png");
			loadTexture("backgroundW3.png");
			
			loadTexture("introBackground1.png");
			loadTexture("introBackground2.png");
			
			loadTexture("rateScreen.png");
		
			
			
			loadTexture("reloadBar.png");
			loadTexture("adnM1.png");
			loadTexture("adnM2.png");
			loadTexture("adnM3.png");
			loadTexture("adnR.png");
			loadTexture("adnG.png");
			loadTexture("adnB.png");
			loadTexture("accelerateIcon.png");
			loadTexture("adn1.png");
			loadTexture("adn2.png");
			loadTexture("arrowDown.png");
			loadTexture("arrowLeft.png");
			loadTexture("arrowRight.png");
			loadTexture("arrowUp.png");
			loadTexture("black.jpg");
			loadTexture("blog.png");
			loadTexture("blueBackground.jpg");
			loadTexture("buttonPlay2.png");
			loadTexture("buttonPlay.png");
			loadTexture("buttonTexture2Red.png");
			loadTexture("buttonTexture.png");
			loadTexture("buttonTextureRed.png");
			loadTexture("buyPower.png");
			loadTexture("closeHud.png");
			loadTexture("emptyStar.png");
			loadTexture("eye.png");
			loadTexture("fb.png");
			loadTexture("fillStar.png");
			loadTexture("gray.jpg");
			loadTexture("help.png");
			loadTexture("iceIcon.png");
			loadTexture("igrm.png");
			loadTexture("joystickBall.png");
			loadTexture("joystickLimit.png");
			loadTexture("mainWindow.png");
			loadTexture("money.png");
			loadTexture("moreMoney.png");
			loadTexture("next.png");
			loadTexture("offBegin.png");
			loadTexture("onBegin.png");
			loadTexture("pause.png");
			loadTexture("pauseMenu.png");
			loadTexture("powerBar.png");
			loadTexture("powerGameBarH.png");
			loadTexture("powerIceCircle.png");
			loadTexture("rate.png");
			loadTexture("redLine.png");
			loadTexture("retry.png");
			loadTexture("selectLevel.png");
			loadTexture("shop1.png");
			loadTexture("shop.png");
			loadTexture("spot.png");
			loadTexture("selectLevel.png");
			loadTexture("shotIcon.png");
			loadTexture("sideWindow.png");
			loadTexture("soundOff.png");
			loadTexture("soundOn.png");
			loadTexture("twttr.png");
			loadTexture("video.png");
			
			TexturePackLoader texturePackLoader = new TexturePackLoader(assetManager, textureManager);
			texturePack =  texturePackLoader.loadFromAsset("gfxSpriteSheets/levelScreenshot.xml", "gfxSpriteSheets/");
			texturePack.loadTexture();
			TexturePackTextureRegionLibrary tplScreenshot = texturePack.getTexturePackTextureRegionLibrary();
			
			hashMap.put("level1.png", tplScreenshot.get(levelScreenshot.LEVEL1_ID));
			hashMap.put("level2.png", tplScreenshot.get(levelScreenshot.LEVEL2_ID));
			hashMap.put("level3.png", tplScreenshot.get(levelScreenshot.LEVEL3_ID));
			hashMap.put("level4.png", tplScreenshot.get(levelScreenshot.LEVEL4_ID));
			hashMap.put("level5.png", tplScreenshot.get(levelScreenshot.LEVEL5_ID));
			hashMap.put("level6.png", tplScreenshot.get(levelScreenshot.LEVEL6_ID));
			
			
			loadTexture("bone1.png");
			loadTexture("bone2.png");
			loadTexture("bone3.png");
			loadTexture("bone4.png");
			loadTexture("gel.png");
			loadTexture("hedgehog1.png");
			loadTexture("hedgehog2.png");
			loadTexture("hedgehog3.png");
			loadTexture("hedgehog4.png");
			loadTexture("lakeBase.png");
			loadTexture("meteor1.png");
			loadTexture("meteor2.png");
			loadTexture("meteor3.png");
			loadTexture("meteorOffFocus.png");
			loadTexture("platform.png");
			loadTexture("roofBase1.png");
			loadTexture("roofBase.png");
			loadTexture("roofBegin1.png");
			loadTexture("roofBegin.png");
			loadTexture("roofEnd.png");
			loadTexture("smasherBone.png");
			loadTexture("title.png");
			loadTexture("tunelFront.png");
			loadTexture("tunelBase.png");
			loadTexture("vulcano.png");
			loadTexture("wallBorder.png");
			loadTexture("wall.png");
			loadTexture("turboBack.png");
			
			
			loadTexture("logo.png");
			
			loadTexture("loading1.png");
			loadTexture("verticalBackgroundGeneral.png");
			loadTexture("verticalBackgroundReward.png");
			loadTexture("help1.png");
			loadTexture("help3.png");
			loadTexture("help4.png");
			loadTexture("help5.png");
			
			
			loadTexture("winAll.png");
			loadTexture("winImage.png");
			loadTexture("help2.png");
			
			loadTextureAtlas("adn2Animated.png",600,300,4,2);
			loadTextureAtlas("bubbleStar.png",600,300,4,2);
			loadTextureAtlas("burble.png",140,139,2,2);
			loadTextureAtlas("checkPoint.png",600,300,4,2);
			loadTextureAtlas("ice.png",270,188,3,2);
			loadTextureAtlas("powerIceCircleAnimated.png",1000,1000,4,4);
			loadTextureAtlas("shot.png",575,118,5,1);
			loadTextureAtlas("virusA.png",345,282,3,3);
			loadTextureAtlas("enemy1.png",108,329,2,7);
			loadTextureAtlas("enemy2.png",150,448,2,7);
			loadTextureAtlas("enemy3.png",150,448,2,7);
			loadTextureAtlas("enemy.png",374,243,4,3);
			loadTextureAtlas("powerShot.png",460,112,4,1);
			loadTexture("intro1.png");
			loadTexture("intro2.png");
			loadTexture("intro3.png");
			loadTexture("intro4.png");
			
			loadTextureAtlas("virusAModel.png",987,740,4,3);
			loadTextureAtlas("virusBModel.png",987,740,4,3);
			loadTextureAtlas("virusB.png",344,281,3,3);
			loadTextureAtlas("virusCModel.png",987,952,4,3);
			loadTextureAtlas("virusC.png",281,361,3,3);
			loadTextureAtlas("virusA.png",345,282,3,3);
			
			
			loadTextureAtlas("virusADead.png",878,658,4,3);
			loadTextureAtlas("virusBDead.png",878,658,4,3);
			loadTextureAtlas("virusCDead.png",875,657,4,3);
			
			
			loadTexturesMainScene();

			loadAll = true;
			// backgroud

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public static TextureSingleton getInstance(TextureManager textureManager,
			AssetManager assetManager,
			VertexBufferObjectManager vertexBufferObjectManager,
			FontManager fontManager) {

		instance = new TextureSingleton(textureManager, assetManager,
				vertexBufferObjectManager, fontManager);

		return instance;
	}

	public static TextureSingleton getInstance() {
		return instance;
	}

	private void initFonts() {

		final ITexture fontTexture = new BitmapTextureAtlas(textureManager,
				256, 256);

		final ITexture fontTexture1 = new BitmapTextureAtlas(textureManager,
				256, 256);
		
		final ITexture fontTexture2 = new BitmapTextureAtlas(textureManager,
				256, 256);


		mFont = FontFactory.createFromAsset(this.fontManager, fontTexture,
				assetManager, "fnt/bandmess.ttf", 28f, true, Color.WHITE);
		
		mFont1 = FontFactory.createFromAsset(this.fontManager, fontTexture1,
				assetManager, "fnt/bandmess.ttf", 32f, true, Color.WHITE);
		
		mFont2 = FontFactory.createFromAsset(this.fontManager, fontTexture2,
				assetManager, "fnt/bandmess.ttf", 18f, true, Color.WHITE);

		fontTexture.load();
		fontTexture1.load();
		fontTexture2.load();

		mFont.load();
		mFont1.load();
		mFont2.load();

	}

	private ITextureRegion loadTextureAtlas(final String name, int width,
			int height, int widthImage, int heightImage) {
		try {

			ITiledTextureRegion pITextureRegion;
			BitmapTextureAtlas pAtlas;

			pAtlas = new BitmapTextureAtlas(getTextureManager(), width, height,
					TextureOptions.BILINEAR);
			pITextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(pAtlas, getAssetManager(), "gfx/"
							+ name, 0, 0, widthImage, heightImage);
			pAtlas.load();

			hashMapAtlas.put(name, pAtlas);

			hashMap.put(name, pITextureRegion);

			return pITextureRegion;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private ITextureRegion loadTexture(final String name) {
		try {

			ITexture iTexture = new BitmapTexture(textureManager,
					new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return assetManager.open("gfx/" + name);
						}
					});
			iTexture.load();

			hashMap.put(name, TextureRegionFactory.extractFromTexture(iTexture));
			hashMapItexture.put(name, iTexture);

			return TextureRegionFactory.extractFromTexture(iTexture);

		} catch (IOException e) {
			Debug.e(e);
			return null;
		}

	}

	// getters // getters// getters// getters// getters// getters// getters//
	// getters// getters
	public TextureManager getTextureManager() {
		return textureManager;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return vertexBufferObjectManager;
	}

	public Font getmFont() {
		return mFont;
	}

	public Font getmFont1() {
		return mFont1;
	}

	public FontManager getFontManager() {
		return fontManager;
	}

	public void setFontManager(FontManager fontManager) {
		this.fontManager = fontManager;
	}

	public static void setInstance(TextureSingleton instance) {
		TextureSingleton.instance = instance;
	}

	public void setTextureManager(TextureManager textureManager) {
		this.textureManager = textureManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public void setVertexBufferObjectManager(
			VertexBufferObjectManager vertexBufferObjectManager) {
		this.vertexBufferObjectManager = vertexBufferObjectManager;
	}

	public void setmFont(Font mFont) {
		this.mFont = mFont;
	}

	public void setmFont1(Font mFont1) {
		this.mFont1 = mFont1;
	}

	public ITextureRegion getTextureByName(String name) {
		try {

			if (loadAll) {
				return hashMap.get(name);
			} else {
				return hashMapAux.get(name);
			}

		} catch (Exception e) {
			return null;
		}

	}

	public ITiledTextureRegion getTextureAnimateByName(String name) {
		try {
			
			if (loadAll) {
				return (ITiledTextureRegion) hashMap.get(name);
			}else{
				return (ITiledTextureRegion) hashMapAux.get(name);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void destroy() {
		try {

			for (ITexture it : hashMapItexture.values()) {
				it.unload();
			}
			for (BitmapTextureAtlas bt : hashMapAtlas.values()) {
				bt.unload();
			}

			hashMap = null;
			hashMapAtlas = null;
			hashMapItexture = null;

			instance = null;

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void unloadTextures() {

		try {

			for (ITexture it : hashMapItexture.values()) {
				it.unload();
			}
			for (BitmapTextureAtlas bt : hashMapAtlas.values()) {
				bt.unload();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Font getmFont2() {
		return mFont2;
	}

	public void setmFont2(Font mFont2) {
		this.mFont2 = mFont2;
	}

	public HashMap<String, ITextureRegion> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<String, ITextureRegion> hashMap) {
		this.hashMap = hashMap;
	}

	public HashMap<String, ITexture> getHashMapItexture() {
		return hashMapItexture;
	}

	public void setHashMapItexture(HashMap<String, ITexture> hashMapItexture) {
		this.hashMapItexture = hashMapItexture;
	}

	public HashMap<String, BitmapTextureAtlas> getHashMapAtlas() {
		return hashMapAtlas;
	}

	public void setHashMapAtlas(HashMap<String, BitmapTextureAtlas> hashMapAtlas) {
		this.hashMapAtlas = hashMapAtlas;
	}

	public void loadLogo() {
		try {
			loadTexture("logo.png");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ITextureRegion getLoadingTexture() {
		try {
			return hashMap.get("logo.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void initGameLoadingFont() {
		try {

			final ITexture fontTexture2 = new BitmapTextureAtlas(
					textureManager, 256, 256);

			mFont2 = FontFactory.createFromAsset(this.fontManager,
					fontTexture2, assetManager, "fnt/bandmess.ttf", 64f,
					true, Color.WHITE);

			fontTexture2.load();

			mFont2.load();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadTexturesMainScene() {
		try {

			initFonts();
			
			
			// primarias

			
			
			loadAll = false;
			
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	
	
}
