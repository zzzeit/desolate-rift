package com.mygdx.game.entity.mob.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.mygdx.game.entity.mob.IPlayer;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.obj.BlockEntity;
import com.mygdx.game.entity.obj.MetalBox;
import com.mygdx.game.util.MyQueryCallback;

import static com.mygdx.game.Main.*;
import static com.mygdx.game.entity.obj.BlockEntity.getBlockInstance;
import static com.mygdx.game.util.IKeycodes.*;
import static com.mygdx.game.util.MyInputProcessor.clickEvent;
import static com.mygdx.game.util.MyInputProcessor.events;

public class PHuman extends Human implements IPlayer {
    public static void instantiate(float x, float y) {
        nPlayers++;
        mobs.add(new PHuman(x, y, Integer.toString(nPlayers).concat("_PLAYER")));
    }


    public PHuman(float x, float y, String name) {
        super(x, y, name);
        setName(name);

    }



    // SILHOUETTE
    private Vector2 blockSilPos;
    @Override
    public void placeBlock() {
        // Check if any body was found
        if (canPlaceBlock()) {
            System.out.println("Cant place a bock here [" + err + "]");
            err++;
        }
        else
            MetalBox.instantiate(blockSilPos.x, blockSilPos.y, 1, BodyDef.BodyType.StaticBody, 1f);
    }

    @Override
    public boolean canPlaceBlock() {
        // Define the AABB around the position
        float halfWidth = 0.1f; // Adjust this value based on the size of your bodies
        float halfHeight = 0.1f; // Adjust this value based on the size of your bodies
        Vector2 lowerBound = new Vector2(blockSilPos.x - halfWidth, blockSilPos.y - halfHeight);
        Vector2 upperBound = new Vector2(blockSilPos.x + halfWidth, blockSilPos.y + halfHeight);

        // Perform the query
        MyQueryCallback callback = new MyQueryCallback();
        world.QueryAABB(callback, lowerBound.x, lowerBound.y, upperBound.x, upperBound.y);
        return callback.isBodyFound();
    }

    @Override
    public void update() {
        super.update();
        blockSilPos = getSilPos(getPosition(), getAngle(false));
        if (events.contains(W))
            moveForward();
        if (events.contains(A))
            moveLeft();
        if (events.contains(S))
            moveBackward();
        if (events.contains(D))
            moveRight();
        if (clickEvent.contains(LEFTCLICK))
            placeBlock();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void shapeRender() {

        getRenderer().begin(ShapeRenderer.ShapeType.Line);
        if (canPlaceBlock())
            getRenderer().setColor(Color.RED);
        else
            getRenderer().setColor(Color.BLUE);
        getRenderer().rect(blockSilPos.x - .7f/2, blockSilPos.y - .7f/2, .7f, .7f);
        getRenderer().end();
    }


}
