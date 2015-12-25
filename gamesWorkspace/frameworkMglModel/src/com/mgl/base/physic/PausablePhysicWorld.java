package com.mgl.base.physic;

import java.util.ArrayList;

import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PausablePhysicWorld extends PhysicsWorld {
	
	private ArrayList<Body> bodyToRemove;
	       
        public PausablePhysicWorld(Vector2 pGravity, boolean pAllowSleep) {
		super(pGravity, pAllowSleep);
			bodyToRemove = new ArrayList<Body>();
        }

		// ===========================================================
        // Variables
        // ===========================================================
       
        private boolean paused;
        private boolean reactivated;
        private float lastSecondsElapsed;
       
 
        // ===========================================================
        // Constructors
        // ===========================================================
       
        
       
        private void setUpPauseablePhysicsWorld(boolean paused, boolean reactivated, float lastSecondsElapsed) {
            this.paused = paused;
            this.lastSecondsElapsed = lastSecondsElapsed;
            this.reactivated = reactivated;
        }
       
        // ===========================================================
        // Overridden Methods
        // ===========================================================
       
        @Override
        public void onUpdate(float pSecondsElapsed) {
                //if not paused -> do as normal, save last pSecondsElapsed
                if(!paused) {
                        lastSecondsElapsed = pSecondsElapsed;
                       
                        //if reactivated -> take last pSecondsElapsed (last time when PhysicsWorld was not paused)
                        //else do as normal
                        if(reactivated) {
                                super.onUpdate(lastSecondsElapsed);
                                reactivated = false;
                        } else{
                                super.onUpdate(pSecondsElapsed);
                        } 
                               
                }
                //if paused == true -> do not perform mWorld.step() -> no update
                //TODO: check if mRunnableHandler and mPhysicsConactorManager .onUpdate() is needed
                else {
                        this.mRunnableHandler.onUpdate(pSecondsElapsed);
                        this.mPhysicsConnectorManager.onUpdate(pSecondsElapsed);
                }
                
                //removeBodies();
        }
 
       
        
        
        // ===========================================================
        // Getters and Setters
        // ===========================================================
       
        public void removeBodies() {
        	try {
				
        		for(Body b : bodyToRemove){
        			destroyBody(b);
        			b=null;
        		}
        		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean isPaused() {
                return paused;
        }
 
        public void setPaused(boolean paused) {
                //check if physics got reactivated
                if(!paused)
                        reactivated = true;
               
                this.paused = paused;
        }

		public void addBodyToRemove(Body b) {
			try {
				
				if(bodyToRemove == null){
					bodyToRemove = new ArrayList<Body>();
				}
				
				bodyToRemove.add(b);
				//b= null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
       
}
