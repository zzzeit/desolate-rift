package com.mygdx.game;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.*;
import static com.mygdx.game.mob.Mob.mobs;
import static com.mygdx.game.mob.player.Human.getHuman;
import static com.mygdx.game.util.Settings.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.mob.Mob;
import com.mygdx.game.mob.hostile.Zombie;
import com.mygdx.game.mob.player.Human;
import com.mygdx.game.obj.Ball;
import com.mygdx.game.obj.Box;
import com.mygdx.game.obj.shape.Entity;
import com.mygdx.game.util.MyInputProcessor;

import static com.mygdx.game.util.MyInputProcessor.*;

public class Main extends ApplicationAdapter {
	private OrthographicCamera camera;
	private Viewport viewport;
	private ShapeRenderer renderer;
	public static World world;
	private Box2DDebugRenderer debugRenderer;
	private static Vector2 mouseRelative = new Vector2();
	private SpriteBatch spriteBatch;
	private Texture texture;
	private Sprite sprite;

	@Override
	public void create () {
		// Set window size and make it not resizable
		Gdx.graphics.setWindowedMode(WIN_WIDTH, WIN_HEIGHT);
		Gdx.input.setInputProcessor(new MyInputProcessor());
//		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
//		Gdx.graphics.setResizable(false);
//		camera = new OrthographicCamera(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH));
		camera = new OrthographicCamera();
		viewport = new FitViewport(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH), camera);
		viewport.apply();

		spriteBatch = new SpriteBatch();
		texture = new Texture("cobblestone.png");
		sprite = new Sprite(texture);
		sprite.setScale(.025f, .025f);
		sprite.setCenter(0f, 0f);


		renderer = new ShapeRenderer();
		world = new World(new Vector2(0, 0f), true); // Gravity downwards
		debugRenderer = new Box2DDebugRenderer();


		Ball.ballInst(1.5f, 6f, 1f, DynamicBody, 1f);
		Box.instantiate(0, 0, 1f, 1f, StaticBody, 1f);

		Zombie.instantiate(10f, 4f);
		Zombie.instantiate(0f, 3f);
		Zombie.instantiate(-15f, -5f);

		Human.instantiate(0f, 0f);

	}
	int i = 0;
	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		world.step(deltaTime, 6, 2);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Entity.upd();
		Mob.upd();
		for (Mob m : mobs)
			if (m instanceof Zombie) {
				((Zombie) m).faceToPoint(((Zombie) m).getHead().getPosition(), getHuman(1).getHead().getPosition(), 30);
			}
//		getZombie(1).faceToPoint(getZombie(1).getHead().getPosition(), getHuman(1).getHead().getPosition(), 10);
		mouseRelative.set(Gdx.input.getDeltaX(), Gdx.input.getDeltaY());

		if (events.contains('W'))
			getHuman(1).moveForward();
		if (events.contains('A'))
			getHuman(1).moveLeft();
		if (events.contains('S'))
			getHuman(1).moveBackward();
		if (events.contains('D'))
			getHuman(1).moveRight();

		camera.position.set((float) ((Math.cos(getHuman(1).getAngle(false) + (Math.PI/2)) * (10 + (7 * (zoom - 1.5f)))) + getHuman(1).getHead().getPosition().x), (float) ((Math.sin(getHuman(1).getAngle(false) + (Math.PI/2)) * (10 + (7 * (zoom - 1.5f)))) + getHuman(1).getHead().getPosition().y), 0f);
//		camera.position.set(getHuman(1).getHead().getPosition().x, getHuman(1).getHead().getPosition().y + 12f, 0f);
		camera.rotate((float) (1 * Math.toDegrees(getHuman(1).getDAngle())));
//		camera.rotateAround(new Vector3(0, 0, 0), new Vector3(0, 0, 1), 1);
//		camera.rotateAround(new Vector3(getHuman(1).getHead().getPosition().x, getHuman(1).getHead().getPosition().y, 0f), camera.position, (float) (1 * Math.toDegrees(getHuman(1).getDAngle())));
		getHuman(1).rotate(-mouseRelative.x);

		camera.zoom = zoom;
		camera.update();
		renderer.setProjectionMatrix(camera.combined);

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (int x = 0; x < 10; x++)
			for (int y = 0; y < 10; y++) {
				sprite.setCenter(x, y);
				sprite.draw(spriteBatch);
			}
		spriteBatch.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		// Render your game objects
		renderer.end();
//		events.clear();
		// Debug renderer
		debugRenderer.render(world, camera.combined);
		debugRenderer.setDrawJoints(false);
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

}
