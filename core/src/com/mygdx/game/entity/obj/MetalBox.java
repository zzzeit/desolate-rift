package com.mygdx.game.entity.obj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.obj.shape.Rect;

import static com.mygdx.game.Main.spriteBatch;

public class MetalBox extends Rect {
    public static int num_of_metbox = 0;
    public static MetalBox instantiate(float x, float y, float width, BodyDef.BodyType BT, float density) {
        num_of_metbox++;
        entities.add(new MetalBox(x, y, width, BT, density, Integer.toString(num_of_metbox).concat("_METBOX")));
        return getBlockInstance(MetalBox.class, num_of_metbox);
    }
    public MetalBox(float x, float y, float width, BodyDef.BodyType BT, float density, String name) {
        super(x, y, width, width, BT, density);
        setName(name);

        setSSR(0);
        setTexture(new Texture("metalbox165.png"));
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/5), 0f, ((getSSR() + 1) / 5f), 1/5f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(0)));
        getSpriteList().get(0).setScale(width/33f);

        setSSR(1);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/5), 0f, ((getSSR() + 1) / 5f), 1/5f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(1)));
        getSpriteList().get(1).setScale(width/33f);

        setSSR(2);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/5), 0f, ((getSSR() + 1) / 5f), 1/5f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(2)));
        getSpriteList().get(2).setScale(width/33f);

        setSSR(3);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/5), 0f, ((getSSR() + 1) / 5f), 1/5f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(3)));
        getSpriteList().get(3).setScale(width/33f);

        setSSR(4);
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/5), 0f, ((getSSR() + 1) / 5f), 1/5f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(4)));
        getSpriteList().get(4).setScale(width/33f);

        setSSR(0); //5
        getTextureRegionList().add(new TextureRegion(getTexture(), getSSR() * (1f/5), 1/5f, ((getSSR() + 1) / 5f), 2/5f));
        getSpriteList().add(new Sprite(getTextureRegionList().get(5)));
        getSpriteList().get(5).setScale(width/33f);
    }

    @Override
    public void render() {
        if (getAdj(0) && getAdj(2) && getAdj(4) && getAdj(6)) { // right up left down
            getSpriteList().get(5).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(5).draw(spriteBatch);
        } else if (getAdj(0) && getAdj(2) && getAdj(4)) { // right up left
            getSpriteList().get(2).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(2).flip(false, true);
            getSpriteList().get(2).draw(spriteBatch);
            getSpriteList().get(2).flip(false, true);
        } else if (getAdj(0) && getAdj(4) && getAdj(6)) { // right left down
            getSpriteList().get(2).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(2).draw(spriteBatch);
        } else if (getAdj(0) && getAdj(2) && getAdj(6)) { // right up down
            getSpriteList().get(2).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(2).rotate(90);
            getSpriteList().get(2).draw(spriteBatch);
            getSpriteList().get(2).rotate(-90);
        } else if (getAdj(2) && getAdj(4) && getAdj(6)) { // up left down
            getSpriteList().get(2).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(2).rotate(270);
            getSpriteList().get(2).draw(spriteBatch);
            getSpriteList().get(2).rotate(-270);
        } else if (getAdj(0) && getAdj(4)) { // right left
            getSpriteList().get(3).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(3).draw(spriteBatch);
        } else if (getAdj(2) && getAdj(6)) { // up down
            getSpriteList().get(3).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(3).rotate(90);
            getSpriteList().get(3).draw(spriteBatch);
            getSpriteList().get(3).rotate(-90);
         } else if (getAdj(0) && getAdj(2)) { // right up
            getSpriteList().get(4).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(4).rotate(180);
            getSpriteList().get(4).draw(spriteBatch);
            getSpriteList().get(4).rotate(-180);
        } else if (getAdj(2) && getAdj(4)) { // up left
            getSpriteList().get(4).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(4).rotate(270);
            getSpriteList().get(4).draw(spriteBatch);
            getSpriteList().get(4).rotate(-270);
        } else if (getAdj(0) && getAdj(6)) { // right down
            getSpriteList().get(4).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(4).rotate(90);
            getSpriteList().get(4).draw(spriteBatch);
            getSpriteList().get(4).rotate(-90);
        } else if (getAdj(4) && getAdj(6)) { // left down
            getSpriteList().get(4).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(4).draw(spriteBatch);
        } else if (getAdj(0)) { // right
            getSpriteList().get(1).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(1).draw(spriteBatch);
        } else if (getAdj(2)) { // up
            getSpriteList().get(1).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(1).rotate(90);
            getSpriteList().get(1).draw(spriteBatch);
            getSpriteList().get(1).rotate(-90);
        } else if (getAdj(4)) { // left
            getSpriteList().get(1).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(1).flip(true, false);
            getSpriteList().get(1).draw(spriteBatch);
            getSpriteList().get(1).flip(true, false);
        } else if (getAdj(6)) { // down
            getSpriteList().get(1).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(1).rotate(-90);
            getSpriteList().get(1).draw(spriteBatch);
            getSpriteList().get(1).rotate(90);
        } else {
            getSpriteList().get(0).setCenter(getBody().getPosition().x, getBody().getPosition().y);
            getSpriteList().get(0).draw(spriteBatch);
        }


    }

    @Override
    public void shapeRender() {

    }

    @Override
    public void update() {
        setAngle(getBody().getAngle());
        calcDeltaAngle();
        updCheckAdjBlocks();
    }

    @Override
    public Vector2 getPosition() {
        return getBody().getPosition();
    }

}
