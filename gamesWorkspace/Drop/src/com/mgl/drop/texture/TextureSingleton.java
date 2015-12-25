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
	private HashMap<String, ITexture> hashMapItexture;
	private HashMap<String, BitmapTextureAtlas> hashMapAtlas;

	private TextureSingleton(TextureManager textureManager,
			AssetManager assetManager,
			VertexBufferObjectManager vertexBufferObjectManager,
			FontManager fontManager) {
		try {
			System.out.println("CREO EL SINGLETON DE TEXTURAS");
			hashMap = new HashMap<String, ITextureRegion>();
			hashMapItexture = new HashMap<String, ITexture>();
			hashMapAtlas = new HashMap<String, BitmapTextureAtlas>();
			this.textureManager = textureManager;
			this.assetManager = assetManager;
			this.vertexBufferObjectManager = vertexBufferObjectManager;
			this.fontManager = fontManager;
			initFonts();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadTextures() {
		try {

			// TODO load automatically from folder
			System.out.println("begin Loading textures");

			
			loadTexture("inHouseIntersitial.png");
			loadTexture("inHouseBanner.png");
			
			loadTexture("backgroundBegin.png");
			loadTexture("backgroundLevel.png");
			loadTexture("black.jpg");
			loadTexture("blueBackground.jpg");
			loadTexture("buttonPlay.jpg");
			loadTexture("gray.jpg");
			
			loadTexture("levelTexture.png");
			loadTexture("closeHud.png");
			
			loadTexture("back.png");

			loadTexture("share.jpg");
			
			loadTexture("play.png");
			loadTexture("point.png");
			loadTexture("reset.jpg");
			
			loadTexture("howToShitA.png");
			loadTexture("howToShitB.png");
			loadTexture("howToShitC.png");
			loadTexture("howToShitD.png");
			
			loadTexture("howToRocketA.png");
			loadTexture("howToRocketB.png");
			loadTexture("howToRocketC.png");
			loadTexture("howToRocketD.png");
			
			loadTexture("howToBombA.png");
			loadTexture("howToBombB.png");
			loadTexture("howToBombC.png");
			loadTexture("howToBombD.png");
			loadTexture("howToBombE.png");
			
			loadTexture("howToSplitA.png");
			loadTexture("howToSplitB.png");
			loadTexture("howToSplitC.png");
			loadTexture("howToSplitD.png");
			
			loadTexture("howToKidA.png");
			loadTexture("howToKidB.png");
			
			loadTexture("thanks.png");
			
			loadTexture("buttonMoreGamesGreen.png");
			loadTexture("buttonMoreGamesRed.png");
			
			loadTexture("orangeVolumeOn.png");
			loadTexture("orangeVolumeOff.png");
			loadTexture("orangePause.png");
			loadTexture("startOrange.png");
			loadTexture("tryAgainOrange.png");
			loadTexture("menuOrange.png");
			loadTexture("orangePause.png");
			loadTexture("playOrange.png");
			loadTexture("selectNext.png");
			loadTexture("shareOrange.png");
			loadTexture("shareYellow.png");
			loadTexture("emptyStar.png");
			loadTexture("fillStar.png");
			loadTexture("poopBar.png");
			loadTexture("feather.png");
			
			loadTexture("levelFeather.png");
			loadTexture("levelFeatherLocked.png");
			loadTexture("levelBonus.png");
			loadTexture("levelBonusLocked.png");
			loadTexture("unlockFacebook.png");
			
			loadTexture("loosePigeon.png");
			loadTexture("winPigeon.png");
			
			loadTexture("box.png");
			loadTexture("ovni.png");
			
			//backgroud
			loadTexture("backgroundCity.png");
			loadTexture("backgroundBird.png");
			loadTexture("backgroundTree.png");
			loadTexture("backgroundMountain.png");
			loadTexture("backgroundSky.png");
			
			loadTexture("startButton.png");
			loadTexture("zoomIn.png");
			loadTexture("zoomOut.png");
			
			loadTexture("roof.png");
			
			loadTexture("roofGround.png");
			loadTexture("nido.png");
			loadTexture("ballRock.png");

			loadTexture("next.png");
			loadTexture("prev.png");

			// JOSE TEXTURES
			loadTexture("floorPrincipal.png");
			// aANIMATED;
			loadTextureAtlas("monsterKid.png", 480, 561, 4, 4);
			loadTextureAtlas("monster.png", 325, 650, 5, 5);
			loadTextureAtlas("runner.png", 900, 390, 10, 3);
			
			loadTextureAtlas("ovni.png", 1080, 160, 6, 2);
			
			loadTextureAtlas("poopButton.png", 600, 85, 6, 1);
			loadTextureAtlas("monsterFat.png", 330, 375, 3, 3);
			
			loadTextureAtlas("paloma.png", 150, 240, 3, 4);
			loadTextureAtlas("poopBasic.png", 480, 300, 4, 2);
			loadTextureAtlas("poopMultiple.png", 260, 200, 2, 2);
			loadTextureAtlas("poopBomb.png", 400, 210, 5, 3);
			loadTextureAtlas("poopRocket.png", 300, 1000, 3, 10);
			loadTextureAtlas("pigeonBegin.png", 500, 60, 10, 1);
			loadTextureAtlas("bigShit.png", 600, 200, 4, 1);
			loadTextureAtlas("miley.png", 630, 720, 7, 6);
			loadTextureAtlas("justin.png", 900, 600, 10, 5);
			
			System.out.println("end Loading textures");
			

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
				assetManager, "fnt/fast99.ttf", 32f, true, Color.WHITE);
		mFont2 = FontFactory.createFromAsset(this.fontManager, fontTexture1,
				assetManager, "fnt/fast99.ttf", 64f, true, Color.WHITE);

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

			// return loadTexture(name);
			return hashMap.get(name);

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
			return (ITiledTextureRegion) hashMap.get(name);

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

	
	
}
