package com.mygdx.game.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

public class MyInputProcessor implements InputProcessor {
    public static List<Character> events = new ArrayList<>();
    private void addEvent(Character c) {events.add(c);}
    private void remEvent(Character c) {events.remove((Character) c);}
    @Override
    public boolean keyDown(int keycode) {
        // Handle key down event
        if (keycode == Input.Keys.LEFT)
            addEvent('0');
        if (keycode == Input.Keys.RIGHT)
            addEvent('1');
        if (keycode == Input.Keys.UP)
            addEvent('2');
        if (keycode == Input.Keys.DOWN)
            addEvent('3');

        return true; // Return true to indicate that the event has been handled
    }

    @Override
    public boolean keyUp(int i) {
        if (i == Input.Keys.LEFT)
            remEvent('0');
        if (i == Input.Keys.RIGHT)
            remEvent('1');
        if (i == Input.Keys.UP)
            remEvent('2');
        if (i == Input.Keys.DOWN)
            remEvent('3');

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
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
