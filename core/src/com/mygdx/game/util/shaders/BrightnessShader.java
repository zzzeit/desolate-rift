package com.mygdx.game.util.shaders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BrightnessShader extends ShaderProgram {
    public static final BrightnessShader brightnessShader = new BrightnessShader(.5f);
    private static final String vertexShader = "attribute vec4 a_position;\n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "void main() {\n" +
            "   v_color = a_color;\n" +
            "   v_texCoords = a_texCoord0;\n" +
            "   gl_Position = u_projTrans * a_position;\n" +
            "}\n";

    private static final String fragmentShader = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform float u_brightness;\n" +
            "void main() {\n" +
            "   vec4 color = texture2D(u_texture, v_texCoords);\n" +
            "   color.rgb *= u_brightness;\n" +
            "   gl_FragColor = color;\n" +
            "}\n";

    public BrightnessShader(float brightness) {
        super(vertexShader, fragmentShader);
        // Set the brightness uniform
        this.begin();
        this.setUniformf("u_brightness", brightness); // Change this value to adjust the brightness
        this.end();
    }

    public static void setBrightness(float brightness) {
        brightnessShader.begin();
        brightnessShader.setUniformf("u_brightness", brightness);
        brightnessShader.end();
    }
}
