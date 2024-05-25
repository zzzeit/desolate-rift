package com.mygdx.game.util;

import static com.mygdx.game.util.IKeycodes.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.ArrayList;
import java.util.List;
import static com.mygdx.game.util.Settings.*;

public class MyInputProcessor implements InputProcessor{
    public static List<Integer> events = new ArrayList<>();
    public static List<Integer> clickEvent = new ArrayList<>();
    private void addEvent(Integer i) {events.add(i);}
    private void remEvent(Integer i) {events.remove((Integer) i);}
    public static float mouseAngle = 0, deltaMouseAngle = 0;
    public static Vector2 mousePosition;
    @Override
    public boolean keyDown(int keycode) {
        // Handle key down event
        if (keycode == Input.Keys.W)
            addEvent(W);
        if (keycode == Input.Keys.A)
            addEvent(A);
        if (keycode == Input.Keys.S)
            addEvent(S);
        if (keycode == Input.Keys.D)
            addEvent(D);
        if (keycode == Input.Keys.SPACE)
            addEvent(SPACE);

        if (keycode == Input.Keys.ESCAPE) { // Check for the ESC key
            Gdx.app.exit(); // Exit the application
            return true;
        }

        return true; // Return true to indicate that the event has been handled
    }

    @Override
    public boolean keyUp(int i) {
        if (i == Input.Keys.W)
            remEvent(W);
        if (i == Input.Keys.A)
            remEvent(A);
        if (i == Input.Keys.S)
            remEvent(S);
        if (i == Input.Keys.D)
            remEvent(D);
        if (i == Input.Keys.SPACE)
            remEvent(SPACE);

        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT)
            clickEvent.add(LEFTCLICK);
        return true;
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

    public static float zoom = 13f, maxZoom = 3f;
    @Override
    public boolean scrolled(float v, float v1) {
        zoom += v1 * .1f;
        if (zoom < .5f)
            zoom = .5f;
        else if (zoom > maxZoom)
            zoom = maxZoom;
        return false;
    }
}
