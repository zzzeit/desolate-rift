package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public interface TextureHandler {
    List<TextureRegion> textureRegionList = new ArrayList<>();
    List<Sprite> spriteList = new ArrayList<>();
    int SSR = 0; // Sprite Sheet Index

    void addSprite();

}
