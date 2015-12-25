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
import org.andengine.util.texturepack.TexturePack;
import org.andengine.util.texturepack.TexturePackTextureRegionLibrary;

import android.content.res.AssetManager;
import android.graphics.Color;

public class TextureSingleton {

	private static TextureSingleton instance = null;

	private TexturePack texturePack;
	private TexturePackTextureRegionLibrary tpl;
	
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
			
			loadTexture("backgroundW1.png");
			loadTexture("backgroundW2.png");
			loadTexture("backgroundW3.png");
			
			loadTexture("title.png");
			
			loadTexture("googleLogin.png");
			loadTexture("googleLogout.png");
			loadTexture("googleAchievement.png");
			loadTexture("googleLeaderboard.png");
			loadTexture("blog.png");
			loadTexture("whatsapp.png");
			loadTexture("igrm.png");
			loadTexture("money.png");
			loadTexture("moreMoney.png");
			loadTexture("offBegin.png");
			loadTexture("onBegin.png");
			loadTexture("pause.png");
			loadTexture("buttonPlay.png");
			loadTexture("gray.jpg");
			loadTexture("pauseMenu.png");
			loadTexture("offBegin.png");
			loadTexture("retry.png");
			loadTexture("selectLevel.png");
			loadTexture("twttr.png");
			loadTexture("fb.png");
			loadTexture("mainWindow.png");
			loadTexture("sideWindow.png");
			
			loadTexture("square.png");
			
			loadTexture("arrowRight.png");
			loadTexture("arrowLeft.png");
			
			loadTexture("black.jpg");
			loadTexture("blackBar.png");
			loadTexture("fillStar.png");
			loadTexture("emptyStar.png");
			loadTexture("closeHud.png");	
			
			loadTexture("soundOff.png");	
			loadTexture("soundOn.png");	
			
			loadTexture("checkPoint.png");
			
			loadTexture("character1.png");
			loadTexture("character2.png");
			loadTexture("character3.png");
			loadTexture("character4.png");
			loadTexture("character5.png");
			loadTexture("character6.png");
			loadTexture("character7.png");
			loadTexture("character8.png");
			loadTexture("character9.png");
			loadTexture("character10.png");
			loadTexture("character11.png");
			loadTexture("character12.png");
			loadTexture("character13.png");
			loadTexture("character14.png");
			loadTexture("character15.png");
			loadTexture("character16.png");
			loadTexture("character17.png");
			
			loadTextureAtlas("dead.png", 1000, 1000, 4, 4);
			loadTexture("winImage.png");
			
			loadTexture("p1.png");
			loadTexture("p2.png");
			loadTexture("p3.png");
			loadTexture("p4.png");
			loadTexture("p5.png");
			
			loadTexture("pB1.png");
			loadTexture("pB2.png");
			loadTexture("pB3.png");
			loadTexture("pB4.png");
			loadTexture("pB5.png");
			
			loadTexture("platform1.png");
			loadTexture("platform2.png");
			loadTexture("platform3.png");
			loadTexture("platform4.png");
			loadTexture("platform5.png");
			
			loadTexture("floor1.png");
			loadTexture("floor2.png");
			loadTexture("floor3.png");
			loadTexture("floor4.png");
			loadTexture("floor5.png");
			loadTexture("floor6.png");
			loadTexture("floor7.png");
			loadTexture("floor8.png");
			
			loadTexture("floorLine.png");
			loadTexture("levelFade.png");
			loadTexture("levelVFade.png");
			
			loadTexture("tunel.png");
			loadTexture("tunelCover.png");
			
			loadTexture("jump.png");
			loadTexture("ship.png");
			
			loadTexture("frame.png");
			
			loadTexture("freeMoney.png");
			loadTexture("starGenerator.png");
			loadTexture("nave.png");
			loadTexture("likeCoin.png");
			loadTexture("generalWindow.png");
			loadTexture("changeColor.png");
			loadTexture("levelTexture.png");
			loadTexture("backStar.png");
			
			loadTexture("backgroundTest.png");
			loadTexture("backgroundGeneral.png");
			
			loadTexture("emptyBar.png");
			loadTexture("fillBar.png");
			
			loadTextureAtlas("likeCoinDead.png", 450, 300, 3, 2);
			
			loadTexturesMainScene();
			

			loadAll = true;

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
