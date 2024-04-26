package com.mygdx.game.entity;

import com.badlogic.gdx.math.Vector2;

/**
 * Intef
 */
public interface IEntity {
    void render();
    void shapeRender();
    void update();
    Vector2 getPosition();
}
