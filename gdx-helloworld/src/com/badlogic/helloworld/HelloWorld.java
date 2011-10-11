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
    private int[][] redMap = new int[12][8];
    private int[][] greenMap = new int[12][8];
    private int xScroll = 32;
    private int yScroll = 23;

	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void render() {
		int h = Gdx.graphics.getHeight();
		boolean touchedRed = Gdx.input.isTouched(0);
		int tyRed = h - Gdx.input.getY(0) + yScroll;
		int txRed = Gdx.input.getX(0) + xScroll;
		boolean touchedGreen = Gdx.input.isTouched(1);
		int tyGreen = h - Gdx.input.getY(1) + yScroll;
		int txGreen = Gdx.input.getX(1) + xScroll;
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //mesh.render(GL10.GL_TRIANGLES, 0, 3);
		batch.begin();
		HexCoordinateSystem hcs = new HexCoordinateSystem(64, 46, 12);
		Pt clPRed = touchedRed ? hcs.find(new Pt(tyRed, txRed)) : null;
		Pt clPGreen = touchedGreen ? hcs.find(new Pt(tyGreen, txGreen)) : null;
		for (int y = 0; y < 12; y++) {
			for (int x = 0; x < 8; x++) {
				Pt c = new Pt(y, x);
				Pt l = hcs.pos(c);
				if (redMap[c.y][c.x] > 0) {
					redMap[c.y][c.x]--;
				}
				if (greenMap[c.y][c.x] > 0) {
					greenMap[c.y][c.x]--;
				}
				if (c.equals(clPRed)) {
					redMap[c.y][c.x] = 100;
				}
				if (c.equals(clPGreen)) {
					greenMap[c.y][c.x] = 100;
				}
				batch.setColor(1.0f - greenMap[c.y][c.x], 1.0f - redMap[c.y][c.x] / 100f, 1.0f - Math.max(redMap[c.y][c.x], greenMap[c.y][c.x]) / 100f, 1);
				batch.draw(texture, l.x - xScroll, l.y - yScroll);
			}
		}
		/*Pt touchedPt = hcs.find(new Pt(ty, tx));
		for (int y = 0; y < 400; y += 10) { for (int x = 0; x < 400; x += 10) {
			Pt loc = hcs.find(new Pt(y, x));
			if (touched && loc.equals(touchedPt)) {
				batch.setColor(1, 1, 1, 1);
			} else {
				batch.setColor((loc.y % 3) * 0.3f, (loc.x % 3) * 0.3f, 0.2f, 1.0f);
			}
			batch.draw(texture, x, y);
		}}*/
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
			texture = new Texture(Gdx.files.internal("hex.png"));
		}
		batch = new SpriteBatch();
	}
}