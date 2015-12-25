package com.mgl.drop.game.sprites.ayoutuberunner;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.controller.LevelController;

public class SpriteColorChange extends MySprite{
	
	private float contTime = 0;
	private float time = 2;
	
	private float rBegin;
	private float gBegin;
	private float bBegin;
	
	private float rEnd;
	private float gEnd;
	private float bEnd;
	

	public SpriteColorChange(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		
			try {
				
				if(level.getPlayer().collidesWith(this)){
					this.detachSelf();
					level.removeEntity(this);
					level.getEntityChangeColor().changeColorEntity(this);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	

	public float getrBegin() {
		return rBegin;
	}

	public void setrBegin(float rBegin) {
		this.rBegin = rBegin;
	}

	public float getgBegin() {
		return gBegin;
	}

	public void setgBegin(float gBegin) {
		this.gBegin = gBegin;
	}

	public float getbBegin() {
		return bBegin;
	}

	public void setbBegin(float bBegin) {
		this.bBegin = bBegin;
	}

	public float getrEnd() {
		return rEnd;
	}

	public void setrEnd(float rEnd) {
		this.rEnd = rEnd;
	}

	public float getgEnd() {
		return gEnd;
	}

	public void setgEnd(float gEnd) {
		this.gEnd = gEnd;
	}

	public float getbEnd() {
		return bEnd;
	}

	public void setbEnd(float bEnd) {
		this.bEnd = bEnd;
	}

	public void setXmlParameter(String parameter) {
		try {
			
			ArrayList<Long> parameterList = MyXmlParser.getParameterList(parameter);
			int i = 0;
			for(Long param : parameterList){
				try {
					if(i==0){
						rBegin = param.floatValue();
					}
					if(i==1){
						gBegin = param.floatValue();
					}
					
					if(i==2){
						bBegin = param.floatValue();
					}
					
					if(i==3){
						rEnd = param.floatValue();
					}
					if(i==4){
						gEnd = param.floatValue();
					}
					
					if(i==5){
						bEnd = param.floatValue();
					}
					if(i==6){
						time = param.floatValue();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
			
			float val = 255;
			rBegin = rBegin/val;
			gBegin = gBegin/val;
			bBegin = bBegin/val;
			
			rEnd = rEnd/val;
			gEnd = gEnd/val;
			bEnd = bEnd/val;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getContTime() {
		return contTime;
	}

	public void setContTime(float contTime) {
		this.contTime = contTime;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}
	
}
