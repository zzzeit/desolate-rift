package com.mygdx.game.util;

import com.badlogic.gdx.math.Vector2;

public class Settings {
    public static int WIN_WIDTH = 1000, WIN_HEIGHT = 800;

    public static float angleBetweenPoints(Vector2 center, Vector2 p2, Boolean oneDirection){
        float angle = (float) Math.atan2(p2.y - center.y, p2.x - center.x);

        if (oneDirection) {
            // Ensure the angle is within the range of 0 to 2π
            if (angle < 0) {
                angle += 2 * Math.PI;
            }
            return angle;
        }
        else
            return angle;
    }
}
