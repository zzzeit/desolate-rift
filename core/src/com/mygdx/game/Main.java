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
import com.mygdx.game.obj.Box;
import com.mygdx.game.obj.shape.Rect;

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
//		Gdx.graphics.setResizable(false);

//		camera = new OrthographicCamera(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH));
		camera = new OrthographicCamera();
		viewport = new FitViewport(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH), camera);
		viewport.apply();


		renderer = new ShapeRenderer();
		world = new World(new Vector2(0, -9.81f), true); // Gravity downwards
		debugRenderer = new Box2DDebugRenderer();

		// Create ground body
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0, -10);
		Body groundBody = world.createBody(groundBodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(camera.viewportWidth, 10);
		groundBody.createFixture(groundBox, 0.0f);
		groundBox.dispose();

		// Create dynamic body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0, 4);
		Body body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(1f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = .5f; // Bounciness
		body.createFixture(fixtureDef);
		circle.dispose();

		Rect r = new Rect(.5f, .5f, StaticBody, 1f);
		r.getBody().setTransform(new Vector2(0f, 0f), 0);

		Box b = new Box(.5f, .5f, DynamicBody, 0f);
		b.getBody().setTransform(new Vector2(0.3f, 2f), 0);

	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		world.step(deltaTime, 6, 2);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setProjectionMatrix(camera.combined);

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		// Render your game objects
		renderer.end();

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
