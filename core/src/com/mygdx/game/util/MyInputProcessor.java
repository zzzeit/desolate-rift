package com.mygdx.game.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class MyInputProcessor implements InputProcessor {
    public static List<Character> events = new ArrayList<>();
    private void addEvent(Character c) {events.add(c);}
    private void remEvent(Character c) {events.remove((Character) c);}
    @Override
    public boolean keyDown(int keycode) {
        // Handle key down event
        if (keycode == Input.Keys.W)
            addEvent('W');
        if (keycode == Input.Keys.A)
            addEvent('A');
        if (keycode == Input.Keys.S)
            addEvent('S');
        if (keycode == Input.Keys.D)
            addEvent('D');

        return true; // Return true to indicate that the event has been handled
    }

    @Override
    public boolean keyUp(int i) {
        if (i == Input.Keys.W)
            remEvent('W');
        if (i == Input.Keys.A)
            remEvent('A');
        if (i == Input.Keys.S)
            remEvent('S');
        if (i == Input.Keys.D)
            remEvent('D');

        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {

        return false;
    }



    @Override
    public boolean mouseMoved(int x, int y) {


        return true;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
