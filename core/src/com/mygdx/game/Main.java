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
import com.mygdx.game.mob.Zombie;
import com.mygdx.game.obj.Ball;
import com.mygdx.game.obj.Box;
import com.mygdx.game.obj.shape.Entity;
import com.mygdx.game.obj.shape.Rect;
import com.mygdx.game.util.MyInputProcessor;

import static com.mygdx.game.util.MyInputProcessor.*;

public class Main extends ApplicationAdapter {
	private OrthographicCamera camera;
	private Viewport viewport;
	private ShapeRenderer renderer;
	public static World world;
	private Box2DDebugRenderer debugRenderer;

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
		Box.instantiate(0, 0, 19, 5, StaticBody, 1f);

		Zombie.instantiate(0, 4);

	}
	int i = 0;
	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		world.step(deltaTime, 6, 2);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Entity.upd();


		if (events.contains('0'))
			Zombie.getZombie(1).turnLeft();
		if (events.contains('1'))
			Zombie.getZombie(1).turnRight();
		if (events.contains('2'))
			Zombie.getZombie(1).moveForward();
		if (events.contains('3'))
			Zombie.getZombie(1).moveBackward();

		camera.position.set(Zombie.getZombie(1).getHead().getPosition().x, Zombie.getZombie(1).getHead().getPosition().y, 0f);
		camera.rotate((float) (1 * Math.toDegrees(Zombie.getZombie(1).getDAngle())));
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
