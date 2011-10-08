package com.badlogic.helloworld;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HelloWorld implements ApplicationListener 
{
	private Mesh mesh;
	private Texture texture;
    private SpriteBatch batch;

	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //mesh.render(GL10.GL_TRIANGLES, 0, 3);
		batch.begin();
		batch.draw(texture, 10, 10);
		batch.end();
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
	@Override
	public void create() {
		if (mesh == null) {
			mesh = new Mesh(true, 3, 3, new VertexAttribute(Usage.Position, 3,
					"a_position"));

			mesh.setVertices(new float[] { -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0,
					0.5f, 0 });
			mesh.setIndices(new short[] { 0, 1, 2 });
		}
		if (texture == null) {
			texture = new Texture(Gdx.files.internal("box2.png"));
		}
		batch = new SpriteBatch();
	}
}