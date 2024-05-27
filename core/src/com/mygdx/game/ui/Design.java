package com.mygdx.game.ui;

import com.badlogic.gdx.utils.Array;

public abstract class Design {
    private Array<UI> uiList = new Array<>();



    protected void addUI(UI ui) {
        uiList.add(ui);
    }

    // Setter
    public void setUIList(Array<UI> uiList) {
        this.uiList = uiList;
    }
    // Getter
    public Array<UI> getUIList() {
        return uiList;
    }
}
