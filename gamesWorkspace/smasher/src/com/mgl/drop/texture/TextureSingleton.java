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
import org.andengine.util.debug.Debug;

import android.content.res.AssetManager;
import android.graphics.Color;

public class TextureSingleton {

	private static TextureSingleton instance = null;

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

			
			
			// secundarias
			loadTexture("heart.png");
			loadTexture("videoReward.png");
			loadTexture("facebook.png");

			loadTexture("bossHit.png");
			loadTexture("borderRed.png");
			loadTexture("wave.png");

			// help
			loadTexture("help1.png");
			loadTexture("help2.png");
			loadTexture("help3.png");
			loadTexture("help4.png");
			loadTexture("help5.png");
			loadTexture("help6.png");
			loadTexture("help7.png");

			loadTexture("googleLogin.png");
			loadTexture("googleLogout.png");
			loadTexture("googleAchievement.png");
			loadTexture("googleLeaderboard.png");
			
			
			// trohpy
			loadTexture("trofeo1.png");
			loadTexture("trofeo2.png");
			loadTexture("trofeo3.png");
			loadTexture("trofeo4.png");
			loadTexture("trofeo5.png");
			loadTexture("trofeo6.png");
			loadTexture("trofeoGris.png");

			loadTexture("video.png");
			loadTexture("pause.png");
			loadTexture("retry.png");
			loadTexture("selectLevel.png");
			loadTexture("soundOn.png");
			loadTexture("soundOff.png");

			loadTexture("paper.png");
			loadTexture("chest.png");
			loadTexture("corona.png");
			loadTexture("bag.png");
			loadTexture("cadiz.png");
			loadTexture("neck.png");
			loadTexture("cruz.png");
			loadTexture("water.png");
			loadTexture("rock.png");

			loadTexture("powerGameBar.png");
			loadTexture("powerGameBarH.png");
			loadTexture("pauseMenu.png");

			loadTexture("emptyStar.png");
			loadTexture("fillStar.png");

			loadTexture("winAll.png");
			loadTexture("looseImage.png");
			loadTexture("winImage.png");

			loadTexture("lifeRed.png");
			loadTexture("lifeBlack.png");
			loadTexture("redLine.png");

			loadTexture("deadBody.png");
			loadTexture("wall.png");
			loadTexture("fireFloor.png");

			loadTexture("powerBar.png");
			loadTexture("deadPower.png");
			loadTexture("wallPower.png");
			loadTexture("firePower.png");
			loadTexture("trunkPower.png");

			loadTexture("death1.png");
			loadTexture("death2.png");
			
			loadTexture("whatsapp.png");
			loadTexture("twttr.png");
			loadTexture("fb.png");

			// JOSE TEXTURES
			loadTexture("floorPrincipal.png");

			// aANIMATED;

			loadTextureAtlas("fire.png", 480, 959, 1, 8);
			loadTextureAtlas("trunk.png", 480, 478, 1, 5);
			loadTextureAtlas("fireDeath.png", 451, 601, 3, 3);

			loadTextureAtlas("batVampire.png", 680, 107, 4, 1);

			loadTextureAtlas("desapearVampire.png", 794, 149, 8, 1);
			loadTextureAtlas("armorVampire.png", 721, 361, 4, 2);
			loadTextureAtlas("unarmorVampire.png", 721, 310, 4, 2);
			loadTextureAtlas("vampire1Smash.png", 420, 72, 3, 1);
			loadTextureAtlas("vampire2Smash.png", 420, 65, 3, 1);
			loadTextureAtlas("vampire3Smash.png", 584, 98, 3, 1);
			loadTextureAtlas("vampire4Smash.png", 720, 94, 3, 1);
			loadTextureAtlas("adamSmash.png", 514, 111, 3, 1);

			loadTextureAtlas("humanSmash.png", 451, 101, 3, 1);

			loadTextureAtlas("pointToHit.png", 200, 100, 2, 1);
			loadTextureAtlas("shop.png", 300, 154, 2, 1);
			loadTextureAtlas("offert.png", 600, 291, 2, 1);

			loadTextureAtlas("blood.png", 201, 151, 4, 3);
			loadTextureAtlas("justin.png", 577, 150, 6, 1);
			loadTextureAtlas("julian.png", 240, 301, 2, 2);
			loadTextureAtlas("julianSmash.png", 640, 86, 3, 1);
			loadTextureAtlas("adam.png", 637, 192, 5, 1);
			loadTextureAtlas("armorSplash.png", 801, 501, 2, 2);
			loadTextureAtlas("boss.png", 1306, 1157, 3, 3);
			loadTextureAtlas("bossSmash.png", 1071, 572, 2, 2);

			loadAll = true;
			// backgroud

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadTextureSplash() {
		try {

			loadTexture("backgroundBegin.png");

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
				assetManager, "fnt/dacw.ttf", 28f, true, Color.WHITE);
		mFont1 = FontFactory.createFromAsset(this.fontManager, fontTexture1,
				assetManager, "fnt/mrsmonster.ttf", 32f, true, Color.WHITE);
		
		mFont2 = FontFactory.createFromAsset(this.fontManager, fontTexture2,
				assetManager, "fnt/mrsmonster.ttf", 50f, true, Color.WHITE);

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
					TextureOptions.DEFAULT);
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
			/*
			 * ITiledTextureRegion pITextureRegion; BitmapTextureAtlas pAtlas;
			 * 
			 * pAtlas = new BitmapTextureAtlas(getTextureManager(), width,
			 * height,TextureOptions.BILINEAR); pITextureRegion =
			 * BitmapTextureAtlasTextureRegionFactory
			 * .createTiledFromAsset(pAtlas,getAssetManager(), "gfx/"+name, 0,
			 * 0,widthImage,heightImage); pAtlas.load();
			 * 
			 * 
			 * 
			 * return pITextureRegion;
			 */
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

	public void loadTextureInterface() {

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
					fontTexture2, assetManager, "fnt/mrsmonster.ttf", 64f,
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
			loadTexture("offBegin.png");
			loadTexture("onBegin.png");
			loadTexture("freeMoney.png");
			loadTexture("verticalBackgroundGeneral.png");
			loadTexture("verticalBackgroundReward.png");
			loadTexture("moreMoney.png");
			loadTexture("blackBackground.png");
			loadTexture("score.png");
			loadTexture("rate.png");
			loadTexture("cadizT.png");
			loadTexture("buttonPlay2.png");
			loadTexture("buyPower.png");
			loadTexture("buttonPlay.png");
			loadTexture("title.png");
			loadTexture("buttonTexture.png");
			loadTexture("buttonTextureGrey.png");
			loadTexture("buttonTextureRed.png");
			loadTexture("buttonTexture2Red.png");
			loadTexture("selecLevelBackground.png");
			loadTexture("fade.png");
			loadTexture("background.png");
			loadTexture("fade2.png");
			loadTexture("background2.png");
			loadTexture("tomb1.png");
			loadTexture("tomb2.png");
			loadTexture("tomb3.png");
			loadTexture("tomb4.png");
			loadTexture("lock.png");
			loadTexture("lockWand.png");
			loadTexture("black.jpg");
			loadTexture("blueBackground.jpg");
			loadTexture("buttonPlay.jpg");
			loadTexture("gray.jpg");
			loadTexture("closeHud.png");
			loadTexture("next.png");
			loadTexture("prev.png");
			loadTexture("money.png");

			loadTextureAtlas("monsterFat.png", 961, 150, 10, 1);
			loadTextureAtlas("human.png", 600, 151, 6, 1);

			

			hashMapAux = (HashMap<String, ITextureRegion>) hashMap.clone();
			loadAll = false;
			
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
