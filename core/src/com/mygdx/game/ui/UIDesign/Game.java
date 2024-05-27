package com.mygdx.game.ui.UIDesign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ui.Design;
import com.mygdx.game.ui.type.Image;

import static com.badlogic.gdx.Gdx.graphics;

public class Game extends Design {
    public Game() {
//        addUI(new Image("clock", new Vector2(-graphics.getWidth()/2 + 70, graphics.getHeight()/2 - 70), 3));
        addUI(new Image("clock", new Vector2(0, 0), 3));
        addUI(new Image("clock_border", new Vector2(0, 0), 2.5f));
        addUI(new Image("clock_pointer", new Vector2(0, 0), 2.5f));
    }
}
