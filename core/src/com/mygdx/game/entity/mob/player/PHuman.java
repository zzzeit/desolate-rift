package com.mygdx.game.entity.mob.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.mob.IPlayer;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.obj.BlockEntity;
import com.mygdx.game.entity.obj.MetalBox;

import static com.mygdx.game.Main.getRenderer;
import static com.mygdx.game.Main.spriteBatch;
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
    public void placeBlock(BlockEntity block) {

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
            placeBlock(MetalBox.instantiate(blockSilPos.x, blockSilPos.y, 1, BodyDef.BodyType.StaticBody, 1f));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void shapeRender() {

        getRenderer().begin(ShapeRenderer.ShapeType.Line);
        getRenderer().setColor(Color.BLUE);
        getRenderer().rect(blockSilPos.x - .7f/2, blockSilPos.y - .7f/2, .7f, .7f);
        getRenderer().end();
    }


}
