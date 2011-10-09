package com.badlogic.helloworld;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HelloWorld implements ApplicationListener 
{
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
		boolean touched = Gdx.input.isTouched();
		int ty = Gdx.input.getY();
		int tx = Gdx.input.getX();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //mesh.render(GL10.GL_TRIANGLES, 0, 3);
		batch.begin();
		HexCoordinateSystem hcs = new HexCoordinateSystem(100, 80, 20);
		Pt touchedPt = hcs.find(new Pt(ty, tx));
		for (int y = 0; y < 400; y += 10) { for (int x = 0; x < 400; x += 10) {
			Pt loc = hcs.find(new Pt(y, x));
			if (touched && loc.equals(touchedPt)) {
				batch.setColor(1, 1, 1, 1);
			} else {
				batch.setColor((loc.y % 3) * 0.3f, (loc.x % 3) * 0.3f, 0.2f, 1.0f);
			}
			batch.draw(texture, x, y);
		}}
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
		if (texture == null) {
			texture = new Texture(Gdx.files.internal("dot.png"));
		}
		batch = new SpriteBatch();
	}
}