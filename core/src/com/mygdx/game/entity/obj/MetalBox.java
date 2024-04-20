package com.mygdx.game.entity.obj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.obj.shape.Rect;

import static com.mygdx.game.Main.spriteBatch;

public class MetalBox extends Rect {
    public static int num_of_metbox = 0;
    public static void instantiate(float x, float y, float width, BodyDef.BodyType BT, float density) {
        num_of_metbox++;
        entities.add(new MetalBox(x, y, width, BT, density, Integer.toString(num_of_metbox).concat("_METBOX")));
    }
    public MetalBox(float x, float y, float width, BodyDef.BodyType BT, float density, String name) {
        super(x, y, width, width, BT, density);
        setSSR(1);
        setName(name);
        setTexture(new Texture("metalbox.png"));
        setTextureRegion(new TextureRegion(getTexture(), getSSR() * (1f/4), 0f, ((getSSR() + 1) / 4f), 1f));
        setSprite(new Sprite(getTextureRegion()));
        getSprite().setScale(width/33f);

    }

    @Override
    public void render() {
        getSprite().setCenter(getBody().getPosition().x, getBody().getPosition().y);
        getSprite().rotate((float) (-1 * Math.toDegrees(getDeltaAngle())));
        getSprite().draw(spriteBatch);
    }

    @Override
    public void update() {
        setAngle(getBody().getAngle());
        calcDeltaAngle();
    }
}
