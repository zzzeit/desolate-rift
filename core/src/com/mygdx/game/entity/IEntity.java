package com.mygdx.game.entity;

import com.badlogic.gdx.math.Vector2;

public interface IEntity {
    void render();
    void update();
    Vector2 getPosition();
}
