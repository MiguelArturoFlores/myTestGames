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

import com.mgl.drop.texture.constant.levelScreenshot;
import com.mgl.drop.texture.constant.pack1;
import com.mgl.drop.texture.constant.pack2;
import com.mgl.drop.texture.constant.pack3;
import com.mgl.drop.texture.constant.pack4;
import com.mgl.drop.texture.constant.pack5;
import com.mgl.drop.texture.constant.tomb;

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
	private Font mFont3;
	private Font mFont4;
	
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
			
			loadTexture("square.png");
			
			loadTexture("arrowRight.png");
			loadTexture("arrowLeft.png");
			
			loadTexture("black.jpg");
			loadTexture("closeHud.png");	
			
			loadTexture("soundOff.png");	
			loadTexture("soundOn.png");	
			
			//zeolandia
			loadTexture("zeoland/hexagon.png","hexagon.png");
			
			loadTexture("zeoland/title.png","title.png");
			loadTexture("zeoland/backgroundBattle.png","backgroundBattle.png");
			
			loadTexture("zeoland/buttonMove.png","buttonMove.png");
			loadTexture("zeoland/buttonAttack.png","buttonAttack.png");
			loadTexture("zeoland/buttonSpecial.png","buttonSpecial.png");
			
			loadTexture("zeoland/zeoFace.png","zeoFace.png");
			
			loadTexture("zeoland/grass1.png","grass1.png");
			loadTexture("zeoland/grass2.png","grass2.png");
			loadTexture("zeoland/grass3.png","grass3.png");
			
			loadTexture("zeoland/tree1.png","tree1.png");
			loadTexture("zeoland/tree2.png","tree2.png");
			loadTexture("zeoland/tree3.png","tree3.png");
			loadTexture("zeoland/tree4.png","tree4.png");
			loadTexture("zeoland/tree5.png","tree5.png");
			
			loadTexture("zeoland/collition1.png","collition1.png");
			loadTexture("zeoland/collition2.png","collition2.png");
			loadTexture("zeoland/collition3.png","collition3.png");
			loadTexture("zeoland/collition4.png","collition4.png");
			loadTexture("zeoland/collition5.png","collition5.png");
			loadTexture("zeoland/collition6.png","collition6.png");
			loadTexture("zeoland/collition7.png","collition7.png");
			loadTexture("zeoland/collition8.png","collition8.png");
			loadTexture("zeoland/collition9.png","collition9.png");
			loadTexture("zeoland/collition10.png","collition10.png");
			
			
			loadTexture("zeoland/floor1.png","floor1.png");
			loadTexture("zeoland/floor2.png","floor2.png");
			loadTexture("zeoland/floor3.png","floor3.png");
			
			loadTexture("zeoland/house.png","house.png");
			loadTexture("zeoland/portal.png","portal.png");
			loadTexture("zeoland/emptyBar.png","emptyBar.png");
			loadTexture("zeoland/fillLifeBar.png","fillLifeBar.png");
			loadTexture("zeoland/fillManaBar.png","fillManaBar.png");
			loadTexture("zeoland/expBar.png","expBar.png");
			
			//===================DECORATIVE========================================
			loadTexture("zeoland/levelDecorative/stone.png","stone.png");
			loadTextureAtlas("zeoland/levelDecorative/chest.png",66,14,6,1,"chest.png");
			
			//========================MAIN=========================================
			loadTextureAtlas("zeoland/main/title.png",693,272,3,2,"title.png");
			loadTextureAtlas("zeoland/main/titleText.png",342,44,2,1,"titleText.png");
			
			//=======================MAP===========================================
			loadTexture("zeoland/map/arrowRightMap.png","arrowRightMap.png");
			loadTexture("zeoland/map/arrowRightPress.png","arrowRightPress.png");
			loadTexture("zeoland/map/arrowLeftMap.png","arrowLeftMap.png");
			loadTexture("zeoland/map/arrowLeftPress.png","arrowLeftPress.png");
			loadTexture("zeoland/map/arrowMap.png","arrowMap.png");
			loadTexture("zeoland/map/backgroundMap.png","backgroundMap.png");
			loadTexture("zeoland/map/enter.png","enter.png");
			loadTexture("zeoland/map/enterPress.png","enterPress.png");
			loadTexture("zeoland/map/placeLogo.png","placeLogo.png");
			loadTexture("zeoland/map/placeMap1.png","placeMap1.png");
			loadTexture("zeoland/map/placeMap2.png","placeMap2.png");
			loadTexture("zeoland/map/Z.png","Z.png");
			loadTexture("zeoland/map/arrowNextMap.png","arrowNextMap.png");
			loadTexture("zeoland/map/arrowPrevMap.png","arrowPrevMap.png");
			loadTexture("zeoland/map/blackBar.png","blackBar.png");
			for(int i=1;i<=5;i++){
				loadTexture("zeoland/map/cloud"+i+".png","cloud"+i+".png");
			}
			loadTextureAtlas("zeoland/map/goldenHead.png", 2000, 720, 5, 3, "goldenHead.png");
			loadTextureAtlas("zeoland/map/pulp.png",69,25,3,1,"pulp.png");
			loadTextureAtlas("zeoland/map/shark.png",51,17,3,1,"shark.png");
			loadTextureAtlas("zeoland/map/ship.png",66,36,3,1,"ship.png");
			
			
			//backgroundMenus======================================================
			loadTexture("zeoland/equipment/arrowRight.png","arrowRight.png");
			loadTexture("zeoland/equipment/arrowLeft.png","arrowLeft.png");
			loadTexture("zeoland/equipment/buttonItem.png","buttonItem.png");
			loadTexture("zeoland/equipment/drop.png","drop.png");
			loadTexture("zeoland/equipment/use.png","use.png");
			loadTexture("zeoland/equipment/backgroundItem.png","backgroundItem.png");
			loadTexture("zeoland/equipment/backgroundMenu.png","backgroundMenu.png");
			loadTexture("zeoland/equipment/backgroundZeo.png","backgroundZeo.png");
			loadTexture("zeoland/equipment/backgroundState.png","backgroundState.png");
			loadTexture("zeoland/equipment/selectItem.png","selectItem.png");
			
			//button=====================================================================
			loadTexture("zeoland/buttonBar.png","buttonBar.png");
			loadTexture("zeoland/buttonMenu.png","buttonMenu.png");
			
			//extra======================================================================
			loadTexture("zeoland/textBox.png","textBox.png");
			loadTexture("zeoland/npc1Face.png","npc1Face.png");
			loadTexture("zeoland/skip.png","skip.png");
			loadTexture("zeoland/playerBegin.png","playerBegin.png");
			loadTexture("zeoland/yes.png","yes.png");
			loadTexture("zeoland/no.png","no.png");
			loadTexture("zeoland/levelUpBackground.png","levelUpBackground.png");
			loadTexture("zeoland/readMore.png","readMore.png");
			
			//=================LEVEL1=====================================================
			for(int i=1;i<=3;i++){
				loadTexture("zeoland/level1/mountain"+i+".png","mountain"+i+".png");
			}
			loadTexture("zeoland/level1/bridge1.png","bridge1.png");
			loadTexture("zeoland/level1/bridge2.png","bridge2.png");
			loadTexture("zeoland/level1/blueSky.png","blueSky.png");
			
			//=================ITEMs=====================================================
			loadTextureAtlas("zeoland/items/surprise.png", 504, 152, 2, 1,"surprise.png");
			
			for(int i=1;i<=9;i++){
				loadTexture("zeoland/items/item"+i+".png","item"+i+".png");
				loadTexture("zeoland/items/item"+i+".png","item"+i+"B.png");
			}
			
			loadTextureAtlas("zeoland/cloud.png", 225, 45, 5,1,"cloud.png");
			loadTextureAtlas("zeoland/turnArrow.png", 22, 10, 2,1,"turnArrow.png");
			//npc-------------------------------------------------------------------
			
			loadTextureAtlas("zeoland/npc1.png", 45, 95, 3,5,"npc1.png");
			
			//zeo------------------------------------------------------------------
			loadTextureAtlas("zeoland/zeo.png", 45, 95, 3,5,"zeo.png");
			loadTextureAtlas("zeoland/zeo2.png", 45, 95, 3,5,"zeo2.png");
			loadTextureAtlas("zeoland/zeoBattle.png", 60, 32, 3,1,"zeoBattle.png");
			loadTextureAtlas("zeoland/zeoBasic.png", 123, 40, 3,1,"zeoBasic.png");
			loadTextureAtlas("zeoland/zeoSpecial1.png", 201, 158, 3,2,"zeoSpecial1.png");
			
			loadTextureAtlas("zeoland/enemy1.png", 45, 76, 3,4,"enemy1.png");
			loadTextureAtlas("zeoland/enemy1Battle.png", 95, 40, 5,2,"enemy1Battle.png");
			
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

		final ITexture fontTexture3 = new BitmapTextureAtlas(textureManager,
				256, 256);

		final ITexture fontTexture4 = new BitmapTextureAtlas(textureManager,
				256, 256);


		mFont = FontFactory.createFromAsset(this.fontManager, fontTexture,
				assetManager, "fnt/pixelmix.ttf", 16f, true, Color.WHITE);
		
		mFont1 = FontFactory.createFromAsset(this.fontManager, fontTexture1,
				assetManager, "fnt/pixelmix.ttf", 14f, true, Color.WHITE);
		
		mFont3 = FontFactory.createFromAsset(this.fontManager, fontTexture3,
				assetManager, "fnt/pixelmix.ttf", 12f, true, Color.WHITE);
		
		mFont4 = FontFactory.createFromAsset(this.fontManager, fontTexture4,
				assetManager, "fnt/pixelmix.ttf", 10f, true, Color.WHITE);
		
		mFont2 = FontFactory.createFromAsset(this.fontManager, fontTexture2,
				assetManager, "fnt/pixelmix.ttf", 8f, true, Color.WHITE);

		fontTexture.load();
		fontTexture1.load();
		fontTexture2.load();
		fontTexture3.load();
		fontTexture4.load();

		mFont.load();
		mFont1.load();
		mFont2.load();
		mFont3.load();
		mFont4.load();

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

	private ITextureRegion loadTextureAtlas(final String name, int width,
			int height, int widthImage, int heightImage,String nameAux) {
		try {

			ITiledTextureRegion pITextureRegion;
			BitmapTextureAtlas pAtlas;

			pAtlas = new BitmapTextureAtlas(getTextureManager(), width, height,
					TextureOptions.DEFAULT);
			pITextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(pAtlas, getAssetManager(), "gfx/"
							+ name, 0, 0, widthImage, heightImage);
			pAtlas.load();

			hashMapAtlas.put(nameAux, pAtlas);

			hashMap.put(nameAux, pITextureRegion);

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

	private ITextureRegion loadTexture(final String name,String nameAux) {
		try {

			ITexture iTexture = new BitmapTexture(textureManager,
					new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return assetManager.open("gfx/" + name);
						}
					});
			iTexture.load();
			
			
			hashMap.put(nameAux, TextureRegionFactory.extractFromTexture(iTexture));
			hashMapItexture.put(nameAux, iTexture);

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

	public TexturePack getTexturePack() {
		return texturePack;
	}

	public void setTexturePack(TexturePack texturePack) {
		this.texturePack = texturePack;
	}

	public TexturePackTextureRegionLibrary getTpl() {
		return tpl;
	}

	public void setTpl(TexturePackTextureRegionLibrary tpl) {
		this.tpl = tpl;
	}

	public Font getmFont3() {
		return mFont3;
	}

	public void setmFont3(Font mFont3) {
		this.mFont3 = mFont3;
	}

	public Font getmFont4() {
		return mFont4;
	}

	public void setmFont4(Font mFont4) {
		this.mFont4 = mFont4;
	}

	public HashMap<String, ITextureRegion> getHashMapAux() {
		return hashMapAux;
	}

	public void setHashMapAux(HashMap<String, ITextureRegion> hashMapAux) {
		this.hashMapAux = hashMapAux;
	}

	public boolean isLoadAll() {
		return loadAll;
	}

	public void setLoadAll(boolean loadAll) {
		this.loadAll = loadAll;
	}

	
	
}
