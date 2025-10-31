package com.mygdx.game.invasion.strategies.shoot;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.invasion.entities.Bullet;

public class DoubleShoot implements ShootStrategy {
    @Override
    public void shoot(float x, float y, Array<Bullet> bullets) {
        // Dispara dos balas separadas
        float separation = 20f; // Separación entre balas
        bullets.add(new Bullet(x - separation, y));
        bullets.add(new Bullet(x + separation, y));
    }
}