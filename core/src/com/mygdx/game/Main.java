package com.mygdx.game;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.*;
import static com.mygdx.game.util.Settings.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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

	@Override
	public void create () {
		// Set window size and make it not resizable
		Gdx.graphics.setWindowedMode(WIN_WIDTH, WIN_HEIGHT);
		Gdx.input.setInputProcessor(new MyInputProcessor());
//		Gdx.graphics.setResizable(false);

//		camera = new OrthographicCamera(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH));
		camera = new OrthographicCamera();
		viewport = new FitViewport(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH), camera);
		viewport.apply();


		renderer = new ShapeRenderer();
		world = new World(new Vector2(0, 0f), true); // Gravity downwards
		debugRenderer = new Box2DDebugRenderer();


		Ball.ballInst(1.5f, 6f, 1f, DynamicBody, 1f);
		Box.instantiate(0, 0, 20, .5f, StaticBody, 1f);

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


		mouseRelative.set(Gdx.input.getDeltaX(), Gdx.input.getDeltaY());

		if (events.contains('W'))
			Human.getHuman(1).moveForward();
		if (events.contains('A'))
			Human.getHuman(1).moveLeft();
		if (events.contains('S'))
			Human.getHuman(1).moveBackward();
		if (events.contains('D'))
			Human.getHuman(1).moveRight();

		camera.position.set(Human.getHuman(1).getHead().getPosition().x, Human.getHuman(1).getHead().getPosition().y, 0f);
		camera.rotate((float) (1 * Math.toDegrees(Human.getHuman(1).getDAngle())));
		Human.getHuman(1).rotate(-mouseRelative.x);

		camera.zoom = 2f;
		camera.update();
		renderer.setProjectionMatrix(camera.combined);

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		// Render your game objects
		renderer.end();
//		events.clear();
		// Debug renderer
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void dispose () {
		world.dispose();
		debugRenderer.dispose();
		renderer.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

}
