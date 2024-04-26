package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Main;
import com.mygdx.game.entity.obj.BlockEntity;
import com.mygdx.game.entity.obj.MetalBox;
import org.lwjgl.Sys;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.entity.obj.BlockEntity.entities;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


/**
 * <p>This class is used for creating and loading {@link Structure},
 * also with a secondary function for reading and writing serialized {@link Structure}. {@link MapGeneration} will utilize this class
 * for the games map generation.</p>
 * <ul>
 *      <li>{@link #create(String)} creates an instance of {@link Structure} and serialize it to 'assets/structures'.</li>
 *      <li>{@link #load(String)} will read a serialized {@link Structure} and iterates its block's positions and instantiates an object to the list {@link BlockEntity#entities}.
 *             The type of object that will be instantiated will be determined by a predefined static integer in {@link BlockList}.</li>
 * </ul>
 *<p>
 *     {@link #create(String)} should be called only once every creation of a serialized {@link Structure}. <br>
 *     {@link #load(String)} should be called every map generation.
 *</p>
 */
class StructureManager implements BlockList {
    private static int[][] map;
    private static Structure s;
    private static Vector3[][] bp; // Block Positions
    private static FileOutputStream fos;
    private static FileInputStream fis;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    /**
     * Variables with required changes every instance:
     * <ul>
     *     <li>map, this variable will define the canvas and each side must be the same.</li>
     * </ul>
     */
    public static void create(String filename) throws IOException {
        map = new int[][] {
                {MetalBox, MetalBox, MetalBox, MetalBox, Air, Air, Air, MetalBox, MetalBox, MetalBox, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, Air, Air, Air, Air, Air, Air, Air, Air, Air, MetalBox},
                {MetalBox, MetalBox, MetalBox, MetalBox, Air, Air, Air, MetalBox, MetalBox, MetalBox, MetalBox}
        };
        s = new Structure(0, 0, 0, map);

        // Serialize the structure
        fos = new FileOutputStream("./structures/".concat(filename).concat(".ser"));
        oos = new ObjectOutputStream(fos);
        oos.writeObject(s);
        fos.close();
        oos.close();

        System.out.println("Serialization successful");
    }

    public static void load(String filename) throws IOException, ClassNotFoundException {
        // Read the serialized structure
        fis = new FileInputStream("./structures/".concat(filename).concat(".ser"));
        ois = new ObjectInputStream(fis);
        s = (Structure) ois.readObject();
        fis.close();
        ois.close();

        s.flip(true, false);
        bp = s.getBlockpositions();
        for (int y = 0; y < bp.length; y++) {
            for (int x = 0; x < bp.length; x++) {
                if (bp[y][x].z == 1)
                    entities.add(new MetalBox(bp[y][x].x, bp[y][x].y, 1, BodyDef.BodyType.StaticBody, 1, "Box"));
            }
        }

        System.out.println("Load serialized object successful");
    }



}

