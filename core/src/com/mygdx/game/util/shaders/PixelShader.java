package com.mygdx.game.util.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class PixelShader extends ShaderProgram {
    public static final PixelShader pixelShader = new PixelShader();
    private static final String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
            "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
            "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "void main() {\n" +
            "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
            "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
            "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
            "}\n";;

    private static final String fragmentShader = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform vec2 u_pixelSize;\n" +
            "void main() {\n" +
            "   vec2 coord = v_texCoords;\n" +
            "   coord = floor(coord / u_pixelSize) * u_pixelSize;\n" +
            "   gl_FragColor = texture2D(u_texture, coord);\n" +
            "}\n";

    PixelShader() {
        super(vertexShader, fragmentShader);
        this.begin();
        this.setUniformf("u_pixelSize", 2.4f / Gdx.graphics.getWidth(), 2.4f / Gdx.graphics.getHeight());
        this.end();
    }

    public static void updPixSize(float x) {
        pixelShader.begin();
        pixelShader.setUniformf("u_pixelSize", x / Gdx.graphics.getWidth(), x / Gdx.graphics.getHeight());
        pixelShader.end();
    }
}
