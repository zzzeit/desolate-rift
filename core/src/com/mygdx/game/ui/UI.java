package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.ui.UIDesign.UIGame;

public abstract class UI {
    public static Array<Design> designList = new Array<>();
    public static TextureAtlas textureAtlas = new TextureAtlas("./pack/ui.atlas");
    public static void init() {
        designList.add(new UIGame());
    }
    public static void uiRender() {
        for (Design design : designList)
            for(UI ui : design.getUIList())
                ui.render(0);
    }
    public static void uiUpdate() {
        for (Design design : designList)
            design.update();
    }

    public abstract void render(int layer);
}
