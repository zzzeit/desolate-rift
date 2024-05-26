package com.mygdx.game.entity.mob;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entity.Entity;
import com.mygdx.game.entity.IEntity;
import com.mygdx.game.entity.mob.hostile.Zombie;
import com.mygdx.game.entity.mob.player.PHuman;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.Main.*;
import static com.mygdx.game.Main.spriteBatch;

public abstract class MobileEntity extends Entity implements IEntity {
    public static List<MobileEntity> mobs = new ArrayList<>();
    public static void upd() {
        for (MobileEntity m : mobs){
            m.update();
        }
    }

    public static void ren(int layer) {
        for (MobileEntity m : mobs) {
            m.render(layer);
            if (m instanceof Zombie) {
                ((Zombie) m).faceToPoint(((Zombie) m).getHead().getPosition(), getMobInstance(PHuman.class, 1).getHead().getPosition(), 30);
            }
        }
    }
    public static <T extends MobileEntity> T getMobInstance(Class<T> c, int n) {
        for (MobileEntity m : mobs) {
            if (c.isInstance(m))
                if (m.getName(0) == Integer.toString(n).charAt(0))
                    return (c.cast(m));
        }
        return null;
    }

    private float health = 0;


    // Setter
    public void setHealth(float health) {
        this.health = health;
    }

    // Getter
    public float getHealth() {
        return health;
    }




}
