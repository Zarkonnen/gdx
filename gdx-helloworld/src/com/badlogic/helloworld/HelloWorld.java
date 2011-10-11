package com.badlogic.helloworld;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

public class HelloWorld implements ApplicationListener 
{
	private Texture hexT;
	private Texture shipT;
    private SpriteBatch batch;
    private Tile[][] map = new Tile[12][8];
    private Pt selection = null;
    private int xScroll = 32;
    private int yScroll = 23;
    private MyInput input;
    private Sound thruster;
    //private OrthographicCamera camera;

	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void resize(int width, int height) {
		/*float ar = (float) width / (float) height * 4.0f / 3.0f;
		camera = new OrthographicCamera(2f * ar, 2f);*/
		/*if (width > height) {
			camera = new OrthographicCamera(3.2f, 1.2f);
		} else {
			camera = new OrthographicCamera(2f, 2f);
		}*/
		//new OrthographicCamera(width, height);
	}
	
	int[] diry = { 0,   0,   0,  0, 64,  64 };
	int[] dirx = { 192, 128, 64, 0, 64,  0  };
	
	@Override
	public void render() {
		/*camera.update();
        camera.apply(Gdx.gl10);
        //batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);*/
		int h = Gdx.graphics.getHeight();
		Pt clickP = input.clickP;
		input.clickP = null;
		clickP = clickP == null ? null : new Pt(h - clickP.y + yScroll, clickP.x + xScroll);
		yScroll += input.pan.y;
		xScroll -= input.pan.x;
		input.pan = Pt.ORIGIN;
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //mesh.render(GL10.GL_TRIANGLES, 0, 3);
		batch.begin();
		HexCoordinateSystem hcs = new HexCoordinateSystem(64, 47, 12);
		Pt clP = clickP == null ? null : hcs.find(clickP);
		for (int y = 0; y < 12; y++) {
			for (int x = 0; x < 8; x++) {
				Pt c = new Pt(y, x);
				Pt l = hcs.pos(c);
				if (c.equals(clP)) {
					if (map[c.y][c.x].ship != null) {
						selection = c;
					} else {
						int dir = selection == null ? -1 : hcs.direction(selection, c);
						if (dir != -1) {
							thruster.play();
							map[selection.y][selection.x].ship.direction = dir;
							map[c.y][c.x].ship = map[selection.y][selection.x].ship;
							map[selection.y][selection.x].ship = null;
							selection = c;
						}
					}
				}
				
				batch.draw(hexT, l.x - xScroll, l.y - yScroll);
				
				if (map[c.y][c.x].ship != null) {
					if (c.equals(selection)) {
						batch.setColor(1.0f, 0.5f, 0.5f, 1.0f);
					}
					batch.draw(shipT, l.x - xScroll, l.y - yScroll,
						dirx[map[c.y][c.x].ship.direction],
						192 - diry[map[c.y][c.x].ship.direction],
						64,
						64);
					batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
				}
			}
		}
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
		if (hexT == null) {
			hexT = new Texture(Gdx.files.internal("space.png"));
			shipT = new Texture(Gdx.files.internal("corvette.png"));
			for (int y = 0; y < 12; y++) {
				for (int x = 0; x < 8; x++) {
					map[y][x] = new Tile();
				}
			}
			
			map[5][5].ship = new Ship();
			map[2][4].ship = new Ship();
			input = new MyInput();
			Gdx.input.setInputProcessor(new GestureDetector(input));
			thruster = Gdx.audio.newSound(Gdx.files.internal("thruster.ogg"));
		}
		
		//camera = new OrthographicCamera(2f, 2f);
		batch = new SpriteBatch();
	}
}