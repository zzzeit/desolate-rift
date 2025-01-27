package com.mygdx.game;
// DELTASITE
// ANNIHILATION
// DESOLATE RIFT
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.*;
import static com.mygdx.game.entity.mob.MobileEntity.*;
import static com.mygdx.game.util.IKeycodes.LEFTCLICK;
import static com.mygdx.game.util.IKeycodes.SPACE;
import static com.mygdx.game.util.Settings.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
//import com.mygdx.game.map.ChunkHandler;
import com.mygdx.game.entity.item.ItemEntity;
import com.mygdx.game.entity.item.items.Stick;
import com.mygdx.game.entity.mob.MobileEntity;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.mob.hostile.Zombie;
import com.mygdx.game.entity.mob.player.PHuman;
import com.mygdx.game.entity.mob.player.Player;
import com.mygdx.game.entity.obj.blocks.BeachBall;
import com.mygdx.game.entity.obj.blocks.MetalBox;
import com.mygdx.game.entity.obj.BlockEntity;
import com.mygdx.game.entity.obj.grounds.Grass;
import com.mygdx.game.entity.obj.grounds.Ground;
import com.mygdx.game.entity.obj.resourceblock.Tree;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.maps.Plains;
import com.mygdx.game.ui.UI;
import com.mygdx.game.util.MyInputProcessor;

import java.util.Random;

import static com.mygdx.game.util.MyInputProcessor.*;
import static com.mygdx.game.util.shaders.BrightnessShader.brightnessShader;
import static com.mygdx.game.util.shaders.PixelShader.pixelShader;

import com.mygdx.game.util.shaders.*;
import org.lwjgl.Sys;

public class Main extends ApplicationAdapter {
	public static Random random = new Random();
	private static OrthographicCamera camera, uiCamera;
	private static ShapeRenderer renderer;
	private static Vector2 mouseRelative = new Vector2();
	public static SpriteBatch spriteBatch;
	public static BitmapFont font;
	public static World world;
	public static Array<Body> bodiesToDestroy = new Array<>();
	private static Vector2 playerPos = new Vector2(0, 0);
	private Viewport viewport;
	private Box2DDebugRenderer debugRenderer;
	public static TextureAtlas textureAtlas;
	public static Map map;
    public static FrameBuffer frameBuffer;
//	ChunkHandler g;


	@Override
	public void create () {
		// Set window size and make it not resizable
		Gdx.graphics.setWindowedMode(WIN_WIDTH, WIN_HEIGHT);
//		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.input.setInputProcessor(new MyInputProcessor());

		//
		camera = new OrthographicCamera();
		uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		uiCamera.zoom = Gdx.graphics.getHeight()/Gdx.graphics.getWidth() * 100f;
		viewport = new FitViewport(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH), camera);
		viewport.apply();
//		viewport = new FitViewport(20, 20 * ((float) WIN_HEIGHT /WIN_WIDTH), uiCamera);
//		viewport.apply();

		spriteBatch = new SpriteBatch();
		spriteBatch.enableBlending();
		font = new BitmapFont();

		font.setColor(Color.WHITE);
		textureAtlas = new TextureAtlas("./pack/textures_single.atlas");


		renderer = new ShapeRenderer();
		world = new World(new Vector2(0, 0f), true); // Gravity downwards
		debugRenderer = new Box2DDebugRenderer();
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, 33, 33, false);
		UI.init();
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
		map = new Plains(201, 201);
		map.genMap();

//		Ground.instantiate(new Grass(new Vector2(-10, 0), 2));
		ItemEntity.create(new Stick(new Vector2(2, 0)));
		ItemEntity.create(new Stick(new Vector2(2.2f, 0)));

//		g = new ChunkHandler();
	}
	int i = 0;
	@Override
	public void render () {

		// Mouse
		mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		float prevAngle = mouseAngle;
		mouseAngle = (float) Math.toDegrees(Math.atan2(mousePosition.x - Gdx.graphics.getWidth()/2f, mousePosition.y - Gdx.graphics.getHeight()/2f)) + 180;
		deltaMouseAngle = mouseAngle - prevAngle;

		float deltaTime = Gdx.graphics.getDeltaTime();
		world.step(deltaTime, 6, 2);

		if (clickEvent.contains(LEFTCLICK)) {
			Array<BlockEntity> toDestroy = new Array<>();
			for (BlockEntity be : BlockEntity.entities) {
				if (be instanceof Tree) {
					be.destroy();
					toDestroy.add(be);
				}
			}
			BlockEntity.entities.removeAll(toDestroy, true);
			for (BlockEntity be : BlockEntity.grounds) {
				be.destroy();
			}

			BlockEntity.grounds.clear();
			BlockEntity.entities.clear();
			map.genMap();

		}
		for (Body b : bodiesToDestroy)
			world.destroyBody(b);
		bodiesToDestroy.clear();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(getMobInstance(Human.class, 1).getHead().getPosition().x, getMobInstance(Human.class, 1).getHead().getPosition().y, 0f);
		camera.update();

//		// Lock the mouse cursor to the center of the window
//		Gdx.input.setCursorPosition(WIN_WIDTH / 2, WIN_HEIGHT / 2);
		playerPos.set(getMobInstance(Human.class, 1).getPosition());

		spriteBatch.setProjectionMatrix(camera.combined);

		BrightnessShader.setBrightness(1f);
		spriteBatch.setShader(brightnessShader);
		spriteBatch.begin();

		entityRender();
		entityUpdate();

		font.getData().setScale(1f);
		spriteBatch.end();
		spriteBatch.setShader(null);

		// Render UI
		spriteBatch.setProjectionMatrix(uiCamera.combined);
		spriteBatch.begin();
		font.getData().setScale(uiCamera.zoom/camera.zoom * 3.5f);
//		font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), camera.project(new Vector3(5, 0 , 0)).x - Gdx.graphics.getWidth()/2f, camera.project(new Vector3(5, 0 , 0)).y - Gdx.graphics.getHeight()/2f);
		font.draw(spriteBatch, "Grounds: " + BlockEntity.grounds.size, camera.project(new Vector3(5, 0, 0)).x - Gdx.graphics.getWidth()/2f, camera.project(new Vector3(5, 0, 0)).y - Gdx.graphics.getHeight()/2f);
		font.draw(spriteBatch, "Items: " + ItemEntity.items.size, camera.project(new Vector3(5, -.8f , 0)).x - Gdx.graphics.getWidth()/2f, camera.project(new Vector3(5, -.8f , 0)).y - Gdx.graphics.getHeight()/2f);
		font.draw(spriteBatch, "Blocks: " + BlockEntity.entities.size, camera.project(new Vector3(5, -1.6f , 0)).x - Gdx.graphics.getWidth()/2f, camera.project(new Vector3(5, -1.6f , 0)).y - Gdx.graphics.getHeight()/2f);
		font.draw(spriteBatch, "Mobs: " + MobileEntity.mobs.size, camera.project(new Vector3(5, -2.4f , 0)).x - Gdx.graphics.getWidth()/2f, camera.project(new Vector3(5, -2.4f , 0)).y - Gdx.graphics.getHeight()/2f);
		font.draw(spriteBatch, "Bodies: " + world.getBodyCount(), camera.project(new Vector3(15, 0 , 0)).x - Gdx.graphics.getWidth()/2f, camera.project(new Vector3(15, 0 , 0)).y - Gdx.graphics.getHeight()/2f);


		font.getData().setScale(1f);

		font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2 - 25);
		font.draw(spriteBatch, "Zoom: " + Math.round(camera.zoom * 100f) / 100f, Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2 - 50);
		font.draw(spriteBatch, "X: " + Math.round(getMobInstance(Human.class, 1).getHead().getBody().getPosition().x) + "  Y: " + Math.round(getMobInstance(Human.class, 1).getHead().getBody().getPosition().y), Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight()/2 - 75);


		spriteBatch.end();

		UI.uiRender();
		UI.uiUpdate();


//		getZombie(1).faceToPoint(getZombie(1).getHead().getPosition(), getMobInstance(Human.class, 1).getHead().getPosition(), 10);
		mouseRelative.set(Gdx.input.getDeltaX(), Gdx.input.getDeltaY());


//		if (events.contains(' '))


//		camera.position.set((float) ((Math.cos(getMobInstance(Human.class, 1).getAngle(false) + (Math.PI/2)) * (8 + (5 * (zoom - maxZoom)))) + getMobInstance(Human.class, 1).getHead().getPosition().x), (float) ((Math.sin(getMobInstance(Human.class, 1).getAngle(false) + (Math.PI/2)) * (8 + (5 * (zoom - maxZoom)))) + getMobInstance(Human.class, 1).getHead().getPosition().y), 0f);

//		camera.rotate((float) (1 * Math.toDegrees(getMobInstance(Human.class, 1).getDeltaAngle())));
//		camera.rotateAround(new Vector3(getMobInstance(Human.class, 1).getHead().getPosition().x, getMobInstance(Human.class, 1).getHead().getPosition().y, 0f), camera.position, (float) (1 * Math.toDegrees(getMobInstance(Human.class, 1).getDAngle())));
//		getMobInstance(Human.class, 1).rotate((mouseRelative.x * .5f));

		camera.zoom = zoom;




		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);
//		renderer.setColor(new Color(1, 1, 1, 0.1f));
//		for (float x = -30.5f; x < 30; x++)
//			for (float y = -30.5f; y < 30; y++) {
//				renderer.rect(x, y, 1, 1);
//			}
		renderer.end();
		// Debug renderer
		debugRenderer.render(world, camera.combined);
		debugRenderer.setDrawJoints(false);
//		debugRenderer.setDrawBodies(false);
		debugRenderer.setDrawContacts(false);
		clickEvent.clear();
//		System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());

	}

	@Override
	public void dispose () {
		world.dispose();
		debugRenderer.dispose();
		renderer.dispose();
		spriteBatch.dispose();
        font.dispose();
        frameBuffer.dispose();
		pixelShader.dispose();
		brightnessShader.dispose();
		textureAtlas.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	public static Camera getCamera() {return camera;}
	public static Camera getUICamera() {return uiCamera;}
	public static boolean inCameraFrustum(Sprite s) {
		Rectangle boundingBox = s.getBoundingRectangle();
		float centerX = boundingBox.x + boundingBox.width / 2;
		float centerY = boundingBox.y + boundingBox.height / 2;
		float halfWidth = boundingBox.width / 2;
		float halfHeight = boundingBox.height / 2;

		if (camera.frustum.boundsInFrustum(centerX, centerY, 0, halfWidth, halfHeight, 0))
			return true;
		return false;
	}
	public static ShapeRenderer getRenderer() {return renderer;}
	public static TextureAtlas getTextureAtlas() {return textureAtlas;}
	public static Vector2 getPlayerPos() {return playerPos;}
}
