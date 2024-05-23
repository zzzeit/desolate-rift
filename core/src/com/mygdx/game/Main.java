package com.mygdx.game;
// DELTASITE
// ANNIHILATION
// DESOLATE RIFT
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.*;
import static com.mygdx.game.entity.mob.MobileEntity.*;
import static com.mygdx.game.util.Settings.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
//import com.mygdx.game.map.ChunkHandler;
import com.mygdx.game.entity.mob.MobileEntity;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.mob.hostile.Zombie;
import com.mygdx.game.entity.mob.player.PHuman;
import com.mygdx.game.entity.obj.blocks.BeachBall;
import com.mygdx.game.entity.obj.blocks.MetalBox;
import com.mygdx.game.entity.obj.BlockEntity;
import com.mygdx.game.entity.obj.grounds.Grass;
import com.mygdx.game.entity.obj.grounds.Ground;
import com.mygdx.game.map.maps.Plains;
import com.mygdx.game.util.MyInputProcessor;

import java.util.Random;

import static com.mygdx.game.util.MyInputProcessor.*;

public class Main extends ApplicationAdapter {
	public static Random random = new Random();
	private static OrthographicCamera camera;
	private static ShapeRenderer renderer;
	private static Vector2 mouseRelative = new Vector2();

	public static SpriteBatch spriteBatch;
	public static BitmapFont font;
	public static World world;
	private static Vector2 playerPos = new Vector2(0, 0);
	private Viewport viewport;
	private Box2DDebugRenderer debugRenderer;
	private Texture texture;
	private static TextureAtlas textureAtlas;
	private Sprite sprite;
//	ChunkHandler g;


	@Override
	public void create () {
		// Set window size and make it not resizable
		Gdx.graphics.setWindowedMode(WIN_WIDTH, WIN_HEIGHT);
//		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.input.setInputProcessor(new MyInputProcessor());

		//
		camera = new OrthographicCamera();
		viewport = new FitViewport(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH), camera);
		viewport.apply();

		spriteBatch = new SpriteBatch();
		spriteBatch.enableBlending();
		font = new BitmapFont();

		font.setColor(Color.WHITE);
		font.getData().setScale(.1f);
		texture = new Texture("plank2.png");
		textureAtlas = new TextureAtlas("./pack/my_texture_atlas.atlas");
		sprite = new Sprite(texture);
		sprite.setScale(1/32f, 1/32f);
		sprite.setCenter(0f, 0f);


		renderer = new ShapeRenderer();
		world = new World(new Vector2(0, 0f), true); // Gravity downwards
		debugRenderer = new Box2DDebugRenderer();


		BeachBall.instantiate(6.5f, 6.5f, 1f, DynamicBody, .2f);
//		Box.instantiate(0, 0, 1f, 1f, DynamicBody, 1f);
		MetalBox.instantiate(0, 0, 1f, StaticBody, 1f);

//		Box.instantiate(30, 0, 1f, 61f, StaticBody, 1f);
//		Box.instantiate(-30, 0, 1f, 61f, StaticBody, 1f);
//		Box.instantiate(0, 30, 61f, 1f, StaticBody, 1f);
//		Box.instantiate(0, -30, 61f, 1f, StaticBody, 1f);

//		Zombie.instantiate(10f, 4f);
//		Zombie.instantiate(0f, 3f);
//		Zombie.instantiate(-15f, -5f);

		PHuman.instantiate(0f, 0f);
		Plains p = new Plains(200, 200);
		p.genMap();

		Ground.instantiate(new Grass(new Vector2(10, 0)));


//		g = new ChunkHandler();
	}
	int i = 0;
	@Override
	public void render () {
		// Mouse
		mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		float prevAngle = mouseAngle;
		mouseAngle = (float) Math.toDegrees(Math.atan2(mousePosition.x - WIN_WIDTH/2, mousePosition.y - WIN_HEIGHT/2));
		deltaMouseAngle = mouseAngle - prevAngle;
		System.out.println(deltaMouseAngle);


		float deltaTime = Gdx.graphics.getDeltaTime();
		world.step(deltaTime, 6, 2);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		// Lock the mouse cursor to the center of the window
//		Gdx.input.setCursorPosition(WIN_WIDTH / 2, WIN_HEIGHT / 2);
		playerPos.set(getMobInstance(PHuman.class, 1).getPosition());

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

//		g.draw(spriteBatch);
//		for (int x = 0; x < 50; x++)
//			for (int y = 0; y < 50; y++) {
//				sprite.setCenter(y, x);
//				sprite.draw(spriteBatch);
//			}
//		sprite.draw(spriteBatch);
//		BlockEntity.ren();
//		for (Sprite s : Ground.sprites)
//			s.draw(spriteBatch);
		BlockEntity.ren();
		spriteBatch.end();


		BlockEntity.upd();
		MobileEntity.upd();

//		getZombie(1).faceToPoint(getZombie(1).getHead().getPosition(), getMobInstance(Human.class, 1).getHead().getPosition(), 10);
		mouseRelative.set(Gdx.input.getDeltaX(), Gdx.input.getDeltaY());


//		if (events.contains(' '))


//		camera.position.set((float) ((Math.cos(getMobInstance(Human.class, 1).getAngle(false) + (Math.PI/2)) * (8 + (5 * (zoom - maxZoom)))) + getMobInstance(Human.class, 1).getHead().getPosition().x), (float) ((Math.sin(getMobInstance(Human.class, 1).getAngle(false) + (Math.PI/2)) * (8 + (5 * (zoom - maxZoom)))) + getMobInstance(Human.class, 1).getHead().getPosition().y), 0f);
		camera.position.set(getMobInstance(Human.class, 1).getHead().getPosition().x, getMobInstance(Human.class, 1).getHead().getPosition().y, 0f);
//		camera.rotate((float) (1 * Math.toDegrees(getMobInstance(Human.class, 1).getDeltaAngle())));
//		camera.rotateAround(new Vector3(getMobInstance(Human.class, 1).getHead().getPosition().x, getMobInstance(Human.class, 1).getHead().getPosition().y, 0f), camera.position, (float) (1 * Math.toDegrees(getMobInstance(Human.class, 1).getDAngle())));
//		getMobInstance(Human.class, 1).rotate((mouseRelative.x * .5f));

		camera.zoom = zoom;
		camera.update();
		renderer.setProjectionMatrix(camera.combined);



		renderer.begin(ShapeRenderer.ShapeType.Line);
//		renderer.setColor(new Color(1, 1, 1, 0.1f));
//		for (float x = -30.5f; x < 30; x++)
//			for (float y = -30.5f; y < 30; y++) {
//				renderer.rect(x, y, 1, 1);
//			}
		renderer.end();
//		events.clear();
		// Debug renderer
		debugRenderer.render(world, camera.combined);
//		debugRenderer.setDrawJoints(false);
		debugRenderer.setDrawBodies(false);
//		debugRenderer.setDrawContacts(false);
		clickEvent.clear();
		System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void dispose () {
		world.dispose();
		debugRenderer.dispose();
		renderer.dispose();
		spriteBatch.dispose();
		texture.dispose();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	public static Camera getCamera() {return camera;}
	public static ShapeRenderer getRenderer() {return renderer;}
	public static TextureAtlas getTextureAtlas() {return textureAtlas;}
	public static Vector2 getPlayerPos() {return playerPos;}
}
