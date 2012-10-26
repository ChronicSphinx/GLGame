package com.example.glbasics;

import javax.microedition.khronos.opengles.GL10;

import com.framework.Game;
import com.framework.Screen;
import com.framework.gl.Texture;
import com.framework.gl.Vertices;
import com.framework.impl.GLGame;
import com.framework.impl.GLGraphics;

public class UnicornScreenTest extends GLGame 
{
	public Screen getStartScreen() {
		return new UnicornScreen(this);
	}
	
	class UnicornScreen extends Screen 
	{
		static final int NUM_UNICORNS = 50;
		GLGraphics glGraphics;
		Vertices unicornModel;
		Texture texture;
		Unicorn[] unicorns;
		
		
		public UnicornScreen(Game game) 
		{
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			texture = new Texture((GLGame)game,"angry_unicorn.png");
			unicornModel = new Vertices(glGraphics, 4, 12, false, true);
			unicornModel.setVertices(new float[]{-16,-16,0,1,
											 16,-16,1,1,
											 16,16,1,0,
											 -16,16,0,0}, 0, 16);
			unicornModel.setIndices(new short[] {0,1,2,2,3,0},0,6);
			
			unicorns = new Unicorn[100];
			for(int i = 0 ; i < 100 ; i++)
			{
				unicorns[i] = new Unicorn();
			}
		}
		
		@Override
		public void update(float deltaTime)
		{
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
			for(int i =0;i < NUM_UNICORNS; i++)
			{
				unicorns[i].update(deltaTime);
			}
		}
		
		
		@Override
		public void present(float deltaTime) 
		{
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClearColor(1, 0, 0, 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);

			gl.glEnable(GL10.GL_TEXTURE_2D);
			texture.bind();
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			for(int i = 0; i < NUM_UNICORNS; i ++)
			{
				gl.glLoadIdentity();
				gl.glTranslatef(unicorns[i].x, unicorns[i].y, 0);
				//gl.glRotatef(45, 0, 0, 1);
//				gl.glScalef(2f, 0.5f, 0);
				unicornModel.draw(GL10.GL_TRIANGLES, 0, 6);
			}
		}
		
		@Override
		public void pause() {
		}
		@Override
		public void resume() {
		}
		@Override
		public void dispose() {
		}
	}		
}
