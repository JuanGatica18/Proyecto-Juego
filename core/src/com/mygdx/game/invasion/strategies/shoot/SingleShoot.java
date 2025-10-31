package com.mygdx.game.invasion.strategies.shoot;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.invasion.entities.Bullet; // Importa la bala

// GM1.5: Implementación 1
public class SingleShoot implements ShootStrategy {
    @Override
    public void shoot(float x, float y, Array<Bullet> bullets) {
        // ¡Añade una nueva bala a la lista del juego!
        bullets.add(new Bullet(x, y));
        // Ya no imprimimos a consola, ahora disparamos de verdad
    }
}