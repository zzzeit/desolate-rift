package com.mygdx.game.entity.mob.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.entity.item.ItemEntity;
import com.mygdx.game.entity.mob.IPlayer;
import com.mygdx.game.entity.mob.hostile.Human;
import com.mygdx.game.entity.obj.blocks.MetalBox;
import com.mygdx.game.map.Map;

import static com.mygdx.game.entity.item.ItemEntity.items;
import static com.mygdx.game.entity.mob.MobileEntity.mobs;
import static com.mygdx.game.util.IKeycodes.*;
import static com.mygdx.game.util.MyInputProcessor.*;
import static com.mygdx.game.entity.mob.player.Player.*;

public class PHuman extends Player implements IPlayer {
    public static void instantiate(float x, float y) {
//        mobs.add(new Human(x, y, Integer.toString(mobs.size()).concat("_PLAYER")));
        PHuman ph = new PHuman(x, y, Integer.toString(players.size + 1).concat("_PLAYER"));
        players.add(ph);
        mobs.add(ph.human);
    }

    private Human human;
    public PHuman(float x, float y, String name) {
        human = new Human(x, y, name) {
            @Override
            protected void subClass() {
                getTorso().getBody().setActive(false);
            }

            @Override
            public void update() {
                super.update();
//                blockSilPos = getSilPos(getPosition(), getAngle(false));
                if (events.contains(W))
                    moveNorth();
                if (events.contains(A))
                    moveWest();
                if (events.contains(S))
                    moveSouth();
                if (events.contains(D))
                    moveEast();
//        if (clickEvent.contains(LEFTCLICK))
//            placeBlock();
//        if (events.contains(SPACE))
//            placeBlock();
                getHead().getBody().setTransform(getPosition(), (float) Math.toRadians(mouseAngle));
                getTorso().getBody().setTransform(getPosition(), (float) Math.toRadians(mouseAngle));
//        System.out.printf("X[%f]  Y[%f]\n", Math.round(getPosition().x / 15) * 15f, Math.round(getPosition().y / 15) * 15f);

                // Collect Items
                for (ItemEntity ie : items) {
                    if (ie.getBody().getFixtureList().get(1).testPoint(getHead().getPosition())) {
                        ie.beCollected(getHead().getPosition());
                        if (ie.getBody().getFixtureList().get(0).testPoint(getHead().getPosition()))
                            ie.destroy();
                    }
                    else
                        ie.setSpeed(0);
                }
                outOfBounds();
            }

            private void outOfBounds() {
                int hw = Map.mapSize[0]/2, hh = Map.mapSize[1]/2;
                if (getHead().getPosition().x > hw)
                    getHead().getBody().applyForceToCenter(-50f, 0, true);
                if (getHead().getPosition().x < -hw)
                    getHead().getBody().applyForceToCenter(50f, 0, true);
                if (getHead().getPosition().y > hh)
                    getHead().getBody().applyForceToCenter(0, -50f, true);
                if (getHead().getPosition().y < -hh)
                    getHead().getBody().applyForceToCenter(0, 50f, true);
            }
        };
        human.setName(name);
//        getTorso().getBody().setActive(false);
        human.getHead().getBody().getFixtureList().first().setFriction(5f);
    }


    // SILHOUETTE
    private Vector2 blockSilPos;
    public void placeBlock() {
        // Check if any body was found
        if (human.bodyExists(blockSilPos)) {
            System.out.println("Cant place a bock here [" + human.err + "]");
            human.err++;
        }
        else
            MetalBox.instantiate(blockSilPos.x, blockSilPos.y, 1, BodyDef.BodyType.StaticBody, 1f);
    }

    private boolean testPointInFix(Fixture f) {
        return f.testPoint(human.getHead().getPosition());
    }

    public Human getHuman() {return human;}

}
